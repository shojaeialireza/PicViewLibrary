package ir.shojaei.picview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var isActive = false

    private val path = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 8f
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.MITER
    }

    private var lastX = 0f
    private var lastY = 0f

    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    fun setActive() {
        isActive = true
    }

    fun setInactive() {
        isActive = false
    }

    fun activeEraser() {
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    fun inactiveEraser() {
        paint.xfermode = null
    }

    fun setPaintColor(color: Int) {
        paint.color = color
    }

    fun getPaintColor() = paint.color

    fun setPaintWidth(w: Float) {
        paint.strokeWidth = w
    }

    fun getPaintWidth() = paint.strokeWidth

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (isActive) {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(event.x, event.y)
                    lastX = event.x
                    lastY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    path.quadTo(lastX, lastY, event.x, event.y)
                    canvas.drawPath(path, paint)
                    lastX = event.x
                    lastY = event.y
                    invalidate()
                }
                MotionEvent.ACTION_UP -> path.reset()
            }
            true
        } else false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, 0f, 0f, null)
    }
}