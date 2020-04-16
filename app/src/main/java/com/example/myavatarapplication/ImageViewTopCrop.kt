package com.oath.doubleplay.stream.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Matrix
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView

// twiiter has image like "w": 554 x "h": 1200 and most of the to and bottom are black,
// but need to be displayed in w : h == 15:9
// CenterCrop shows most of the time head cut, so need adding offset to show more from the top

class ImageViewTopCrop : AppCompatImageView {
    constructor(context: Context?) : super(context) {
        scaleType = ScaleType.MATRIX
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        scaleType = ScaleType.MATRIX
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        scaleType = ScaleType.MATRIX
    }

    ///
//    override fun onLayout(changed: Boolean, frameLeft: Int, frameTop: Int, frameRight: Int, frameBottom: Int) {
//        super.onLayout(changed, frameLeft, frameTop, frameRight, frameBottom)
//        Log.i("+++", "+++ 111 +++ +++ onLayout(), drawable==null:(${drawable == null}), (frameLeft($frameLeft) == frameRight($frameRight) && frameTop($frameTop) == frameBottom($frameBottom)))\n" +
//            "$idStr\n---")
//        if (drawable == null || (frameLeft == frameRight && frameTop == frameBottom)) {
//            return
//                .also {
//                    Log.i("+++", "+++ 111 --- exit onLayout(), drawable==null:(${drawable == null}), (frameLeft($frameLeft) == frameRight($frameRight) && frameTop($frameTop) == frameBottom($frameBottom)))\n" +
//                        "$idStr\n---")
//                }
//        }
//        recomputeImageMatrix(frameLeft, frameTop, frameRight, frameBottom)
//    }

    private fun recomputeImageMatrix(frameLeft: Int, frameTop: Int, frameRight: Int, frameBottom: Int) {
        scaleType = ScaleType.MATRIX
        val frameWidth = frameRight - frameLeft.toFloat() - this.paddingLeft - this.paddingRight
        val frameHeight = frameBottom - frameTop.toFloat() - this.paddingTop - this.paddingBottom
        val originalImageWidth = drawable.intrinsicWidth.toFloat()
        val originalImageHeight = drawable.intrinsicHeight.toFloat()

        val scaleX = frameWidth / originalImageWidth
        val scaleY = frameHeight / originalImageHeight
        val scale = if (scaleX > scaleY) scaleX else scaleY

//        val scale = if (frameWidth > originalImageWidth || frameHeight > originalImageHeight) {
//            val fitHorizontallyScaleFactor = frameWidth / originalImageWidth
//            val fitVerticallyScaleFactor = frameHeight / originalImageHeight
//            Math.max(fitHorizontallyScaleFactor, fitVerticallyScaleFactor)
//        } else {
//            1.0f
//        }

        val newImageWidth = originalImageWidth * scale
        val newImageHeight = originalImageHeight * scale

        /**
         * twitter has wired image dimensions someone are very wide and has the meaningful content left aligned
         * so for width is larger than view 2.5x then choose top/left translate
         * otherwise do centerCrop
         */
        val widthRatioFactor = newImageWidth / frameWidth
        var horizontalTranslateDx = if (widthRatioFactor > 2.5) {
            0f
        } else {
            (frameWidth - newImageWidth) * 0.5f
        }

        /**
         * vertical do centerCrop with a bit shift higher
         */
        val newHalf = newImageHeight/2
        var verticalTranslateDy = -newHalf + (frameHeight / 2 + 25.DpToPx()).coerceAtMost((newHalf))

        if (frameWidth > originalImageWidth || frameHeight > originalImageHeight) {
            Log.e("+++", "+++ [frameWidth x H: $frameWidth x $frameHeight], [originalImageWidth x H: $originalImageWidth x $originalImageHeight]\n" +
                    "scaleX: $scaleX, scaleY: $scaleY, ==> scale: $scale\n" +
                    "newImageWidth x H: $newImageWidth x $newImageHeight\n" +
                    "(frameWidth($frameWidth) > originalImageWidth($originalImageWidth) || frameHeight($frameHeight) > originalImageHeight($originalImageHeight)) ==> ${(frameWidth > originalImageWidth || frameHeight > originalImageHeight)}, FALSE ==> scale == 1f\n" +
                    "dx x dy: $horizontalTranslateDx x $verticalTranslateDy, widthRatioFactor: $widthRatioFactor\n" +
                    "--- $idStr")

        } else if (scale != 1f) {
            Log.w("+++", "+++ [frameWidth x H: $frameWidth x $frameHeight], [originalImageWidth x H: $originalImageWidth x $originalImageHeight]\n" +
                    "scaleX: $scaleX, scaleY: $scaleY, ==> scale: $scale\n" +
                    "newImageWidth x H: $newImageWidth x $newImageHeight\n" +
                    "(frameWidth($frameWidth) > originalImageWidth($originalImageWidth) || frameHeight($frameHeight) > originalImageHeight($originalImageHeight)) ==> ${(frameWidth > originalImageWidth || frameHeight > originalImageHeight)}, FALSE ==> scale == 1f\n" +
                    "dx x dy: $horizontalTranslateDx x $verticalTranslateDy, widthRatioFactor: $widthRatioFactor\n" +
                    "--- $idStr")

        } else {
            Log.i("+++", "+++ [frameWidth x H: $frameWidth x $frameHeight], [originalImageWidth x H: $originalImageWidth x $originalImageHeight]\n" +
                    "scaleX: $scaleX, scaleY: $scaleY, ==> scale: $scale\n" +
                    "newImageWidth x H: $newImageWidth x $newImageHeight\n" +
                    "(frameWidth($frameWidth) > originalImageWidth($originalImageWidth) || frameHeight($frameHeight) > originalImageHeight($originalImageHeight)) ==> ${(frameWidth > originalImageWidth || frameHeight > originalImageHeight)}, FALSE ==> scale == 1f\n" +
                    "dx x dy: $horizontalTranslateDx x $verticalTranslateDy, widthRatioFactor: $widthRatioFactor\n" +
                    "--- $idStr")
        }

        ///
//        val newMatrix = Matrix() //imageMatrix // todo: better to use new matrix?
//        newMatrix.setScale(usedScaleFactor, usedScaleFactor, 0f, 0f)
//        newMatrix.postTranslate(
//            (frameWidth - newImageWidth) * 0.5f,
//            -newImageHeight/2 + Math.min((frameHeight/2 + 25.DpToPx()), (newImageHeight/2))
//        )

        ///

        val newMatrix = imageMatrix
        newMatrix.setScale(scale, scale, 0f, 0f)
        newMatrix.postTranslate(horizontalTranslateDx,verticalTranslateDy)
        imageMatrix = newMatrix
    }
    ///

    var idStr = ""
    override fun setFrame(frameLeft: Int, frameTop: Int, frameRight: Int, frameBottom: Int): Boolean {

        if (drawable == null || (frameLeft == frameRight && frameTop == frameBottom)) {
            return super.setFrame(frameLeft, frameTop, frameRight, frameBottom)
                .also {
                    Log.v("+++", "+++ --- setFrame(), return $it, drawable==null:(${drawable == null}) || (frameLeft($frameLeft) == frameRight($frameRight) && frameTop($frameTop) == frameBottom($frameBottom)))\n" +
                            "$idStr\n---")
                }
        }
        if (drawable.intrinsicWidth <= 0f || drawable.intrinsicHeight <= 0f) {
            return super.setFrame(frameLeft, frameTop, frameRight, frameBottom)
                .also {
                    Log.e("+++", "+++ --- setFrame(), return $it, originalImageWidth(${drawable.intrinsicWidth}) <= 0 || originalImageHeight(${drawable.intrinsicHeight}) <= 0\n" +
                            "$idStr\n---")
                }
        }

        Log.i("+++", "+++ 222 +++ +++ setFrame(), drawable==null:(${drawable == null}), (frameLeft($frameLeft) == frameRight($frameRight) && frameTop($frameTop) == frameBottom($frameBottom)))\n" +
                "$idStr\n---")

        recomputeImageMatrix(frameLeft, frameTop, frameRight, frameBottom)
        return super.setFrame(frameLeft, frameTop, frameRight, frameBottom)
    }
}

/**
 * Dp to Int
 */
fun Int.DpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Int to Dp
 */
fun Int.IntToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
