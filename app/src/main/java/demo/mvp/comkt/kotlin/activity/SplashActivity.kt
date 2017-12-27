package demo.mvp.comkt.kotlin

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import demo.mvp.comkt.kotlin.base.BaseActivity
import demo.mvp.comkt.kotlin.extension.startIntent
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by HP on 2017/12/6.
 */
class SplashActivity : BaseActivity(){


    override fun attachRes(): Int {

        val strA:String = "非空"
        val strB:String? = null
        val strC:String? = "可空串"

        Log.d("TTTTT","strA = "  + strA.length);
        Log.d("TTTTT","strB = "  + strB?.length);
        Log.d("TTTTT","strC = "  + strC?.length);

        return R.layout.activity_splash;
    }


    override fun initView() {
        val font : Typeface = Typeface.createFromAsset(this.assets,"fonts/Lobster-1.4.otf")
        textView.typeface = font
        textView2.typeface = font

        setAnimation();
    }

    private fun setAnimation() {
        val alphaAnimation = AlphaAnimation(0.1f,1.0f)
        alphaAnimation.duration = 1000
        val scaleAnimation = ScaleAnimation(0.1f,1.0f,0.1f,1.0f, ScaleAnimation.RELATIVE_TO_SELF,0.5f, ScaleAnimation.RELATIVE_TO_SELF,0.5f)
        scaleAnimation.duration =1000
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(scaleAnimation)
        animationSet.duration = 1000
        button4.startAnimation(animationSet)
        animationSet.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                startIntent<MainActivity>();
                finish()
            }
        })
    }

}