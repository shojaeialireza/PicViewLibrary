package ir.shojaei.picview

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout

class PicTextView(context: Context) : PicView(context, null) {

    private var mainTv = GradientTextView(getContext(), null)

    private var fontSize = 25f

    init {
        tag = "pic_view"
        mainTv.setTextColor(Color.WHITE)
        val params = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        mainTv.layoutParams = params
        addV(mainTv)
    }

    fun setColor(color: Int) {
        mainTv.setGradient(0, 0, 0, listOf(color, color))
        invalidate()
    }

    fun setRotationXY(x: Float, y: Float) {
        mainTv.rotationX = x
        mainTv.rotationY = y
    }

    fun getViewRotationX() = mainTv.rotationX
    fun getViewRotationY() = mainTv.rotationY
    fun getViewRotation() = rotation

    fun setFont(font: Typeface) {
        mainTv.typeface = font
    }

    fun getFont(): Typeface = mainTv.typeface

    fun setGravityVal(g: Int) {
        mainTv.gravity = g
    }

    fun getGravity() = mainTv.gravity

    fun setText(t: String) {
        mainTv.text = t
        mainTv.setLines(t.split("\n").size)
    }

    fun getText() = mainTv.text.toString()

    fun setTexSize(s: Float) {
        mainTv.textSize = s
        fontSize = s
    }

    fun getTextSize() = fontSize.toInt()

    fun setGradient(values: List<Int>, colors: List<Int>) =
        mainTv.setGradient(values[0], values[1], values[2], colors)

    fun getGradientColors() = mainTv.getGradientColors()
    fun getGradientVals() = mainTv.getGradientVals()

    override fun setOpacity(radius: Float) {
        mainTv.alpha = radius
    }

    override fun getOpacity() = mainTv.alpha

    fun setStroke(s: Int) {
        mainTv.strokeSize = s
    }

    fun getStrokeSize() = mainTv.strokeSize

    fun setStrokeColor(color: Int) {
        mainTv.strokeColor = color
    }

    fun getStrokeColor() = mainTv.strokeColor

    fun setShadow(s: Float) {
        mainTv.setShadow(s)
    }

    fun getShadow() = mainTv.getShadowSize()

    override fun doScale(isIncrease: Boolean) {
        if (isIncrease) {
            if (fontSize < 100) fontSize++
        } else {
            if (fontSize > 0) fontSize--
        }
        mainTv.textSize = fontSize
        Log.i("___", "mw:$maxWidth , mh:$maxHeight , w:$width , h:$height")
    }

    override fun getMainView() = mainTv
}