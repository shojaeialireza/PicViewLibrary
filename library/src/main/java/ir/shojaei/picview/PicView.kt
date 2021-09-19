package ir.shojaei.picview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.setPadding
import kotlin.math.*

@SuppressLint("ResourceType")
abstract class PicView(context: Context, attributeSet: AttributeSet?) :
    ConstraintLayout(context, attributeSet) {

    //region variables

    private val TOUCH_SCALE_FACTOR = 0.03f
    private var previousX = 0f
    private var previousY = 0f

    private val Int.px: Int
        get() {
            return (resources.displayMetrics.density * this).toInt()
        }


    private var diffPointersX = -1
    private var diffPointersY = -1

    private var isLock = false

    private var moveOrgX = -1f
    private var moveOrgY = -1f

    private lateinit var onViewClickListener: (id: Int) -> Unit


    private val rotateIv: ImageView = ImageView(context).apply {
        setImageResource(R.drawable.rotate)
        setBackgroundResource(R.drawable.icons_background)
        id = 1001
        val lp = FrameLayout.LayoutParams(30.px, 30.px)
        layoutParams = lp
        scaleType = ImageView.ScaleType.CENTER
        tag = "iv_rotate"
    }
    private val deleteIv: ImageView = ImageView(context).apply {
        setImageResource(R.drawable.remove)
        setBackgroundResource(R.drawable.icons_background)
        id = 1002
        val lp = FrameLayout.LayoutParams(30.px, 30.px)
        layoutParams = lp
        scaleType = ImageView.ScaleType.CENTER
    }
    private val flipIv: ImageView = ImageView(context).apply {
        setImageResource(R.drawable.flip)
        setBackgroundResource(R.drawable.icons_background)
        id = 1003
        val lp = FrameLayout.LayoutParams(30.px, 30.px)
        layoutParams = lp
        scaleType = ImageView.ScaleType.CENTER
    }
    private val lockedIv: ImageView = ImageView(context).apply {
        setImageResource(R.drawable.unlock)
        setBackgroundResource(R.drawable.icons_background)
        id = 1004
        val lp = FrameLayout.LayoutParams(30.px, 30.px)
        layoutParams = lp
        scaleType = ImageView.ScaleType.CENTER
    }
    private val mainLayout: FrameLayout = FrameLayout(context).apply {
        id = 1005
        setBackgroundResource(R.drawable.border_view)
        setPadding(8)
        clipChildren = false
        clipToPadding = false
    }

    //endregion
    init {
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            this.gravity = Gravity.CENTER
        }

        val constraintSet = ConstraintSet()

        addView(mainLayout)
        addView(lockedIv)
        addView(deleteIv)
        addView(rotateIv)
        addView(flipIv)

        clipChildren = false
        clipToPadding = false
        constraintSet.clone(this)

        minWidth=70.px
        minHeight=70.px

        constraintSet.connect(flipIv.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(flipIv.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)

        constraintSet.connect(deleteIv.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.connect(deleteIv.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)

        constraintSet.connect(rotateIv.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.connect(rotateIv.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)


        constraintSet.connect(lockedIv.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(lockedIv.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

        constraintSet.connect(mainLayout.id, ConstraintSet.TOP, flipIv.id, ConstraintSet.BOTTOM)
        constraintSet.connect(mainLayout.id, ConstraintSet.START, flipIv.id, ConstraintSet.END)
        constraintSet.connect(mainLayout.id, ConstraintSet.BOTTOM, rotateIv.id, ConstraintSet.TOP)
        constraintSet.connect(mainLayout.id, ConstraintSet.END, rotateIv.id, ConstraintSet.START)

        setConstraintSet(constraintSet)

        deleteIv.setOnClickListener {
            this.parent?.let { p ->
                (p as ViewGroup).removeView(this)
            }
        }
        flipIv.setOnClickListener {
            getMainView().apply {
                rotationY = if (rotationY == -180f) 0f else -180f
            }
        }
        lockedIv.setOnClickListener {
            getMainView().apply {
                isLock = !isLock
                lockedIv.setImageResource(
                    if (isLock) R.drawable.lock else R.drawable.unlock
                )
            }
        }
        rotateIv.setOnTouchListener { view, motionEvent -> handleTouch(view, motionEvent) }
        setOnTouchListener { view, motionEvent -> handleTouch(view, motionEvent) }
    }

    protected fun addV(v: View) {
        mainLayout.addView(v)
    }

    private fun handleTouch(v: View, event: MotionEvent): Boolean {
        when (v.tag) {
            "iv_rotate" -> {
                if (!isLock) {
                    when (event.action) {
                        MotionEvent.ACTION_MOVE -> {
                            var dx = event.x - previousX
                            var dy = event.y - previousX
                            if (dy > height / 2)
                                dx *= -1
                            if (dx > width / 2)
                                dy *= -1
                            rotation += (dx + dy) * TOUCH_SCALE_FACTOR
                            if (rotation > 360) rotation -= 360
                            else if (rotation < 0) rotation = 360 - rotation
                            previousX = event.x
                            previousY = event.y
                        }
                    }
                }
            }
            else -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        moveOrgX = event.rawX
                        moveOrgY = event.rawY
                        onViewClickListener.invoke(v.id)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (!isLock) {
                            if (event.pointerCount == 1) {
                                val offsetX = event.rawX - moveOrgX
                                val offsetY = event.rawY - moveOrgY
                                this@PicView.x += offsetX
                                if (this.y + offsetY in -height + 50f..(parent as View).height - 50f)
                                    this@PicView.y += offsetY
                                moveOrgX = event.rawX
                                moveOrgY = event.rawY
                            } else if (event.pointerCount == 2) {
                                val fIndex = event.findPointerIndex(0)
                                val sIndex = event.findPointerIndex(1)
                                if (diffPointersX == -1) {
                                    diffPointersX =
                                        abs(event.getX(fIndex) - event.getX(sIndex)).toInt()
                                }
                                if (diffPointersY == -1) {
                                    diffPointersY =
                                        abs(event.getY(fIndex) - event.getY(sIndex)).toInt()
                                }
                                if (abs(event.getX(fIndex) - event.getX(sIndex)).toInt() >= diffPointersX + 20) {
                                    diffPointersX =
                                        abs(event.getX(fIndex) - event.getX(sIndex)).toInt()
                                    doScale(true)
                                }
                                if (abs(event.getX(fIndex) - event.getX(sIndex)).toInt() <= diffPointersX - 20) {
                                    diffPointersX =
                                        abs(event.getX(fIndex) - event.getX(sIndex)).toInt()
                                    doScale(false)
                                }
                                if (abs(event.getY(fIndex) - event.getY(sIndex)).toInt() >= diffPointersY + 20) {
                                    diffPointersY =
                                        abs(event.getY(fIndex) - event.getY(sIndex)).toInt()
                                    doScale(true)
                                }
                                if (abs(event.getY(fIndex) - event.getY(sIndex)).toInt() <= diffPointersY - 20) {
                                    diffPointersY =
                                        abs(event.getY(fIndex) - event.getY(sIndex)).toInt()
                                    doScale(false)
                                }
                            }
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        diffPointersX = -1
                        diffPointersY = -1
                    }
                }
            }
        }
        return true
    }

    abstract fun doScale(isIncrease: Boolean)

    fun setOnClickListener(listener: (id: Int) -> Unit) {
        onViewClickListener = listener
    }

    fun hideBorder() {
        mainLayout.setBackgroundColor(Color.TRANSPARENT)
        rotateIv.visibility = View.INVISIBLE
        deleteIv.visibility = View.INVISIBLE
        lockedIv.visibility = View.INVISIBLE
        flipIv.visibility = View.INVISIBLE

    }

    fun showBorder() {
        mainLayout.setBackgroundResource(R.drawable.border_view)
        rotateIv.visibility = View.VISIBLE
        deleteIv.visibility = View.VISIBLE
        lockedIv.visibility = View.VISIBLE
        flipIv.visibility = View.VISIBLE
    }

    abstract fun setOpacity(radius: Float)
    abstract fun getOpacity(): Float

    abstract fun getMainView(): View
}