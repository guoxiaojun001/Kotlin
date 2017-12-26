package demo.mvp.comkt.kotlin.utils

import android.content.Context
import android.graphics.BitmapFactory
import com.bumptech.glide.BitmapTypeRequest
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import demo.mvp.comkt.kotlin.base.BaseApplication

/**
 * Created by HP on 2017/12/25.
 */
class GlideImageLoader private constructor(context: Context) {

    private var mRequestManager: RequestManager = Glide.with(BaseApplication.getInstance());


//  private  constructor(context: Context){}

    companion object {
        private var sImageLoader: GlideImageLoader? = null;

        fun getInstance(context: Context): GlideImageLoader? {
            if (sImageLoader == null) {
                synchronized(GlideImageLoader::class.java) {
                    if (sImageLoader == null) {

                        sImageLoader = GlideImageLoader(context);
                    }

                }
            }

            return sImageLoader;
        }

        fun loadImage(model : String ): BitmapTypeRequest<*>? {

            if (sImageLoader!!.mRequestManager == null) {
                sImageLoader!!.mRequestManager =  Glide.with(BaseApplication.getInstance())
            }
            return sImageLoader!!.mRequestManager.load(model)
                    .asBitmap()

        }

        fun <T> loadImage(model: T, resourceId: Int): DrawableRequestBuilder<*> {
            if (sImageLoader!!.mRequestManager == null) {
                sImageLoader!!.mRequestManager =  Glide.with(BaseApplication.getInstance())
            }
            return sImageLoader!!.mRequestManager.load(model)
                    .placeholder(resourceId)
                    .error(resourceId)
                    .dontAnimate()
        }


        fun <T> loadImagePic(model: T): DrawableRequestBuilder<*> {
            if (sImageLoader!!.mRequestManager == null) {
                sImageLoader!!.mRequestManager =  Glide.with(BaseApplication.getInstance())
            }
            return sImageLoader!!.mRequestManager.load(model)
                    .dontAnimate()
        }

    }

}