package demo.mvp.comkt.kotlin.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.annotation.LayoutRes
import android.support.annotation.XmlRes
import android.support.v7.widget.Toolbar
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import demo.mvp.comkt.kotlin.R
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by HP on 2017/12/26.
 */
abstract class BasePreferenceActivity : PreferenceActivity(), LifecycleProvider<ActivityEvent> {

    private var preference: SharedPreferences? = null;


    @LayoutRes
    abstract fun getLayout() : Int

    @XmlRes
    abstract fun getXml() : Int

    abstract fun initView()

    abstract fun initData()

    fun getPreference() : SharedPreferences {
        if (preference == null) {
            preference = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        return preference!!
    }

    fun setToolBar(toolbar: Toolbar, title: String) {
        toolbar.title = title + ""
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun setToolBarNoBack(toolbar: Toolbar, title: String) {
        toolbar.title = title + ""
    }



    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    @CheckResult
    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    @CheckResult
    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        addPreferencesFromResource(getXml())
        setContentView(getLayout())
        initView()
        initData()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }
}