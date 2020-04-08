package com.example.myavatarapplication

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
