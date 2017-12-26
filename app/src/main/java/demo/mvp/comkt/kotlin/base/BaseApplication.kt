package demo.mvp.comkt.kotlin.base

import android.app.Application
import demo.mvp.comkt.kotlin.utils.GlideImageLoader

/**
 * Created by HP on 2017/12/25.
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Inner.APP = this;


        GlideImageLoader.getInstance(getInstance())
    }


    companion object {
        fun getInstance(): BaseApplication{
            return  Inner.APP!!;
        }
    }

    private object Inner {
        var APP: BaseApplication? = null
    }

}