package com.example.myavatarapplication

import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.RequestOptions

class MainActivity : AppCompatActivity() {

    var twitterMediaImage1: ImageView? = null
    var twitterMediaImage2: ImageView? = null
    var twitterMediaImage3: ImageView? = null
    var twitterMediaImage4: ImageView? = null
    var twitterMediaImgContainer: View? = null
    var twitterMediaImgSubContainerLeft: View? = null
    var twitterMediaImgSubContainerRight: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        twitterMediaImage1 = findViewById<ImageView>(R.id.dp_twitter_media_image_1)
        twitterMediaImage2 = findViewById<ImageView>(R.id.dp_twitter_media_image_2)
        twitterMediaImage3 = findViewById<ImageView>(R.id.dp_twitter_media_image_3)
        twitterMediaImage4 = findViewById<ImageView>(R.id.dp_twitter_media_image_4)
        twitterMediaImgContainer = findViewById<View>(R.id.dp_twitter_media_img_container)
        twitterMediaImgSubContainerLeft = findViewById<View>(R.id.dp_twitter_media_img_sub_container_left)
        twitterMediaImgSubContainerRight = findViewById<View>(R.id.dp_twitter_media_img_sub_container_right)


        loadAvatar()
    }

    /**
     * if in manifest set android:usesCleartextTraffic="false",
     * it will get exception when load image from http from glide
     *
     * java.io.IOException(Cleartext HTTP traffic to pbs.twimg.com not permitted)
    call GlideException#logRootCauses(String) for more detail
    class com.bumptech.glide.load.engine.GlideException: Failed to load resource
     *
     * <uses-permission android:name="android.permission.INTERNET" />

    either do
    <application

    android:usesCleartextTraffic="true"
    tools:replace="android:usesCleartextTraffic"

     *
     * or change http:// to https://
     *      */

    fun loadAvatar() {
        val avtarUrl =
            "http://pbs.twimg.com/profile_images/1193175282267643904/_Q8YlPIc_normal.jpg"
//            "http://pbs.twimg.com/profile_images/1010862750401253377/Rof4XuYC_normal.jpg"
//            "http://pbs.twimg.com/profile_images/904818366828998657/RFVV0nKQ_normal.jpg"
//        "http://pbs.twimg.com/profile_images/1082358814819536896/19QbYCgF_bigger.jpg"
//        "http://pbs.twimg.com/profile_images/1010862750401253377/Rof4XuYC_bigger.jpg"

        findViewById<ImageView>(R.id.avatar).apply {
            loadImg2(
                avtarUrl
                ,requestOptions = RequestOptions.circleCropTransform()
            )
        }

        loadImage()
    }

    fun getMediaImages(): List<String> {

        var list = mutableListOf<String>()

        var imgUrls = listOf(
//            "https://pbs.twimg.com/tweet_video_thumb/EO-WhfIW4AEeUgT.jpg"
            "https://pbs.twimg.com/media/EPBewQJXkAAXigE.jpg?name=large"
//            ,"https://pbs.twimg.com/media/EPBewQJXkAAXigE.jpg?name=small"
//            ,"https://pbs.twimg.com/media/EPBewQJXkAAXigE.jpg?name=thumb"
        )
        var sizeFormat = ""//"?name=small"
        imgUrls.forEach { mediaUrl ->
            val url = list.add(mediaUrl + sizeFormat)
        }
        return list
    }

    fun loadImage() {

        //(twitterMediaImgContainer?.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "H,1.79:1"

        val imgContainerSubRight = twitterMediaImgSubContainerRight

        var twitterMediaImage1: ImageView? = findViewById<ImageView>(R.id.dp_twitter_media_image_1)
        var twitterMediaImage2: ImageView? = findViewById<ImageView>(R.id.dp_twitter_media_image_2)
        var twitterMediaImage3: ImageView? = findViewById<ImageView>(R.id.dp_twitter_media_image_3)
        var twitterMediaImage4: ImageView? = findViewById<ImageView>(R.id.dp_twitter_media_image_4)


        val img1 = twitterMediaImage1
        val img2 = twitterMediaImage2
        val img3 = twitterMediaImage3
        val img4 = twitterMediaImage4

        val imgs = getMediaImages()

        if (imgs.isNotEmpty()) {
            setMultipleImgGroupVisibility(true)

            when (imgs.size) {
                1 -> {
                    imgContainerSubRight?.visibility = View.GONE
                    img2?.visibility = View.GONE
                    img3?.visibility = View.GONE
                    img4?.visibility = View.GONE
                }
                2 -> {
                    img3?.visibility = View.GONE // keep 1, 2
                    img4?.visibility = View.GONE
                }
                3 -> {
                    img3?.visibility = View.GONE // keep 1, 2, 4
                }
            }

            if (imgs.size >= 1) {
                img1?.apply {
                    if (imgs.size == 1) {
                        if (false) {
                            setViewSelectedRoundCorner(this, topLeft = true, topRight = true, bottomLeft = true, bottomRight = true)
                        } else {
                            setViewSelectedRoundCorner(this, topLeft = false, topRight = false, bottomLeft = true, bottomRight = true)
                        }
                    } else if (imgs.size == 2 || imgs.size == 3) {
                        if (false) {
                            setViewSelectedRoundCorner(this, topLeft = true, topRight = false, bottomLeft = true, bottomRight = false)
                        } else {
                            setViewSelectedRoundCorner(this, topLeft = false, topRight = false, bottomLeft = true, bottomRight = false)
                        }
                    } else if (imgs.size == 4) {
                        if (true) {
                            setViewSelectedRoundCorner(this, topLeft = true, topRight = false, bottomLeft = false, bottomRight = false)
                        } else {
                            setViewSelectedRoundCorner(this, topLeft = false, topRight = false, bottomLeft = false, bottomRight = false)
                        }
                    }
                    Glide.with(context).load(imgs[0]).apply(RequestOptions().dontTransform()).into(this) // glide issue with recycled imageview which has had different dimensions, https://github.com/bumptech/glide/issues/1456
                }
            }
            if (imgs.size >= 2) {
                img2?.apply {
                    if (imgs.size == 2) {
                        if (true) {
                            setViewSelectedRoundCorner(this, topLeft = false, topRight = true, bottomLeft = false, bottomRight = true)
                        } else {
                            setViewSelectedRoundCorner(this, topLeft = false, topRight = false, bottomLeft = false, bottomRight = true)
                        }
                    } else if (imgs.size == 3 || imgs.size == 4) {
                        if (true) {
                            setViewSelectedRoundCorner(this, topLeft = false, topRight = true, bottomLeft = false, bottomRight = false)
                        } else {
                            setViewSelectedRoundCorner(this, topLeft = false, topRight = false, bottomLeft = false, bottomRight = false)
                        }
                    }
                    Glide.with(context).load(imgs[1]).apply(RequestOptions().dontTransform()).into(this)
                }
            }
            if (imgs.size == 3) { // 3 image shows right with one image and left with two images
                img4?.apply {
                    setViewSelectedRoundCorner(this, topLeft = false, topRight = false, bottomLeft = false, bottomRight = true)
                    Glide.with(context).load(imgs[2]).apply(RequestOptions().dontTransform()).into(this)
                }
            } else if (imgs.size == 4) {

                img3?.apply {
                    setViewSelectedRoundCorner(this, topLeft = false, topRight = false, bottomLeft = true, bottomRight = false)
                    Glide.with(context).load(imgs[2]).apply(RequestOptions().dontTransform()).into(this)
                }
                img4?.apply {
                    setViewSelectedRoundCorner(this, topLeft = false, topRight = false, bottomLeft = false, bottomRight = true)
                    Glide.with(context).load(imgs[3]).apply(RequestOptions().dontTransform()).into(this)
                }
            }
        } else {
            setMultipleImgGroupVisibility(false)
        }
    }

    fun setMultipleImgGroupVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        twitterMediaImage1?.visibility = visibility
        twitterMediaImage2?.visibility = visibility
        twitterMediaImage3?.visibility = visibility
        twitterMediaImage4?.visibility = visibility
        twitterMediaImgSubContainerLeft?.visibility = visibility
        twitterMediaImgSubContainerRight?.visibility = visibility
        twitterMediaImgContainer?.visibility = visibility
    }

    private fun setViewSelectedRoundCorner(view: View, topLeft: Boolean = false, topRight: Boolean = false, bottomLeft: Boolean = false, bottomRight: Boolean = false) {
        val curveRadius = view.context.resources.getDimension(R.dimen.twitter_corner_radius)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.outlineProvider = object : ViewOutlineProvider() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun getOutline(view: View?, outline: Outline?) {
                    view?.let {

                        if (topLeft && topRight && bottomLeft && bottomRight) { // all
                            outline?.setRoundRect(0, 0, view.width, view.height, curveRadius)
                        } else if (topLeft && topRight && !bottomLeft && !bottomRight) { // TOP
                            outline?.setRoundRect(0, 0, view.width, view.height + (curveRadius.toInt()), curveRadius)
                        } else if (!topLeft && !topRight && bottomLeft && bottomRight) { // bottom
                            outline?.setRoundRect(0, -(curveRadius.toInt()), view.width, view.height, curveRadius)
                        } else if (topLeft && !topRight && bottomLeft && !bottomRight) { // left
                            outline?.setRoundRect(0, 0, view.width + (curveRadius.toInt()), view.height, curveRadius)
                        } else if (!topLeft && topRight && !bottomLeft && bottomRight) { // right
                            outline?.setRoundRect(-(curveRadius.toInt()), 0, view.width, view.height, curveRadius)
                        } else if (topLeft && !topRight && !bottomLeft && !bottomRight) { // top left
                            outline?.setRoundRect(0, 0, view.width + (curveRadius.toInt()), view.height + (curveRadius.toInt()), curveRadius)
                        } else if (!topLeft && !topRight && bottomLeft && !bottomRight) { // bottom left
                            outline?.setRoundRect(0, -(curveRadius.toInt()), view.width + (curveRadius.toInt()), view.height, curveRadius)
                        } else if (!topLeft && topRight && !bottomLeft && !bottomRight) { // top right
                            outline?.setRoundRect(-(curveRadius.toInt()), 0, view.width, view.height + (curveRadius.toInt()), curveRadius)
                        } else if (!topLeft && !topRight && !bottomLeft && bottomRight) { // bottom right
                            outline?.setRoundRect(-(curveRadius.toInt()), -(curveRadius.toInt()), view.width, view.height, curveRadius)
                        } else if (!topLeft && !topRight && !bottomLeft && !bottomRight) { // no round corner
                            outline?.setRoundRect(0, 0, view.width, view.height, 0F)
                        } else {
                            outline?.setRoundRect(0, 0, view.width, view.height, 0F)
                        }
                    }
                }
            }
            view.clipToOutline = true
        }
    }
}

//fun ImageView.loadImg(
//    imageUrl: String,
//    requestOptions: RequestOptions = RequestOptions()
//) {
//    if (!TextUtils.isEmpty(imageUrl)) {
//        Glide.with(this.context)
//            .load(imageUrl)
//            .apply(requestOptions)
//            .error(R.drawable.unknown_avatar)
//            .into(this)
//    }
//}

fun ImageView.loadImg2(
    imageUrl: String,
    diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC,
    skipMemoryCache: Boolean = true,
    roundingRadius: Int = 0,
    target: Target<Drawable>? = null,
    requestOptions: RequestOptions = RequestOptions()
) {
    requestOptions
        .diskCacheStrategy(diskCacheStrategy)
        .apply {
            if (roundingRadius > 0) {
                transform(CenterCrop(), RoundedCorners(roundingRadius))
            }
        }

    if (!TextUtils.isEmpty(imageUrl)) {
        Glide.with(this.context)
            .load(imageUrl)
            .apply(requestOptions)
            .listener(object : RequestListener<Drawable> { //9
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                          isFirstResource: Boolean): Boolean {
//                    DispatchingProgressManager.forget(url)
//                    onFinished()
                    Log.e("+++", "+++ !!! onLoadFailed() $e", e)
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                             dataSource: DataSource?, isFirstResource: Boolean): Boolean {

//                    DispatchingProgressManager.forget(url)
//                    onFinished()
                    Log.i("+++", "+++ onResourceReady()")
                    return false
                }
            })
            .skipMemoryCache(skipMemoryCache)
            .apply {
                if (target == null) {
                    into(this@loadImg2)
                } else {
                    into(target)
                }
            }
    }
}
