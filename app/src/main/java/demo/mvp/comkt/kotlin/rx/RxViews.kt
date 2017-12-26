package demo.mvp.comkt.kotlin.rx

import android.annotation.SuppressLint
import android.support.annotation.CheckResult
import com.fire.zhihudaily.rx.EasyRecyclerViewLoadMore
import com.fire.zhihudaily.rx.EasyRecyclerViewLoadMoreError
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

/**
 * Created by fire on 2017/12/10.
 * Date：2017/12/10
 * Author: fire
 * Description:
 */
object RxViews {

    @SuppressLint("RestrictedApi")
    @CheckResult
    fun <T>onItemClick(adapter: RecyclerArrayAdapter<T>): Observable<Int> {

        return EasyRecyclerViewItemClickListener(adapter)
    }

    @SuppressLint("RestrictedApi")
    @CheckResult
    fun <T>setLoadMore(adapter: RecyclerArrayAdapter<T>): Observable<Any> {
        return EasyRecyclerViewLoadMore(adapter)
    }

    @SuppressLint("RestrictedApi")
    @CheckResult
    fun <T>adapterLoadMoreError(adapter: RecyclerArrayAdapter<T>): Observable<Any> {
        return EasyRecyclerViewLoadMoreError(adapter)
    }

    @SuppressLint("RestrictedApi")
    @CheckResult
    fun EasyRecyclererrorViewClick(recycler : EasyRecyclerView): Observable<Any>  {
        return EasyRecyclerViewErrorClick(recycler)
    }

    fun <T> transBOOL(`val`: Boolean?): ObservableTransformer<T, Boolean> {
        return object : ObservableTransformer<T, Boolean> {
            override fun apply(upstream: Observable<T>): Observable<Boolean> {
                return upstream.map {
                    return@map `val`;
                }
            }
        }
    }
}