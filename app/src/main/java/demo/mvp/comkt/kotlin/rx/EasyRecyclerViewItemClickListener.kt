package demo.mvp.comkt.kotlin.rx

import android.annotation.SuppressLint
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable


class EasyRecyclerViewItemClickListener<T> : Observable<kotlin.Int> {

    @SuppressLint("RestrictedApi")
    override fun subscribeActual(observer: Observer<in Int>) {
        if (null == (observer)) {
            return
        }
        val listener = ItemClickListener(adapter, observer)
        observer.onSubscribe(listener)
        adapter.setOnItemClickListener(listener)
    }

    private val adapter: RecyclerArrayAdapter<T>

    constructor(adapter: RecyclerArrayAdapter<T>){
        this.adapter = adapter
    }


    internal class ItemClickListener<T>(private val adapter: RecyclerArrayAdapter<T>, private val observer: Observer<in Int>) : MainThreadDisposable(), RecyclerArrayAdapter.OnItemClickListener {

        override fun onItemClick(position: Int) {
            if (!isDisposed) {
                observer.onNext(position)
            }
        }
        override fun onDispose() {
            adapter.setOnItemClickListener(null)
        }
    }
}