package ir.shojaei.picview

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView

class PicImageView(context: Context) : PicView(context, null) {

    var w = 150
    var h = 150
    private lateinit var originalImage: Bitmap
    private val mainIv = ImageView(context).apply {
        scaleType = ImageView.ScaleType.FIT_XY
    }

    init {
        tag = "pic_view"
        addV(mainIv)
    }

    fun setBitmap(bitmap: Bitmap) {
        originalImage = bitmap
        mainIv.setImageBitmap(
            Bitmap.createScaledBitmap(bitmap, w, h, false)
        )
    }

    override fun setOpacity(radius: Float) {
        mainIv.alpha = radius
    }

    override fun getOpacity() = mainIv.alpha

    override fun doScale(isIncrease: Boolean) {
        if (isIncrease) {
            w += 15
            h += 15
        } else {
            w = if (w - 15 > 1) w - 15 else 1
            h = if (h - 15 > 1) h - 15 else 1
        }
        mainIv.setImageBitmap(Bitmap.createScaledBitmap(originalImage, w, h, false))
    }

    override fun getMainView() = mainIv
}
