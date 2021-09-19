package ir.shojaei.picview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class GradientTextView(context: Context, attributeSet: AttributeSet?) :
    AppCompatTextView(context, attributeSet) {

    var strokeSize = 0
        set(value) {
            field = value
            invalidate()
        }
    private var shadow = 0f
    private var shadowOfColor = Color.parseColor("#4E4E4E")

    private var gradientX0 = 90
    private var gradientY0 = 0
    private var gradientRotation = 0
    private val gradientColors = mutableListOf<Int>(Color.WHITE, Color.WHITE)
    var strokeColor = Color.WHITE
        set(value) {
            field = value
            invalidate()
        }

    fun setShadow(s: Float) {
        setShadowLayer(s / 3, s, s, shadowOfColor)
        shadow=s
    }

    fun getShadowSize() = shadow

    fun setGradient(x0: Int, y0: Int, rotation: Int, color: List<Int>) {
        gradientX0 = x0
        gradientY0 = y0
        gradientRotation = rotation
        gradientColors.apply {
            clear()
            addAll(color)
        }
        invalidate()
    }

    fun getGradientColors() = gradientColors
    fun getGradientVals() = listOf(gradientX0, gradientY0, gradientRotation)

    override fun onDraw(canvas: Canvas?) {
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeMiter = 10f
        setTextColor(strokeColor)
        paint.strokeWidth = strokeSize.toFloat()
        paint.shader = null
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        setTextColor(textColors)
        val shader: Shader = LinearGradient(
            gradientX0.toFloat(), gradientY0.toFloat(), 0f,
            width.toFloat(), gradientColors[0], gradientColors[1], Shader.TileMode.MIRROR
        )
        val matrix=Matrix()
        matrix.setRotate(gradientRotation.toFloat())
        shader.setLocalMatrix(matrix)
        paint.shader = shader
        super.onDraw(canvas)
    }
}