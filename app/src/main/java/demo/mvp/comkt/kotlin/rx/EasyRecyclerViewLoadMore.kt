package com.fire.zhihudaily.rx

import android.annotation.SuppressLint
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import demo.mvp.comkt.kotlin.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable



class EasyRecyclerViewLoadMore <T> : Observable<Any> {

    @SuppressLint("RestrictedApi")
    override fun subscribeActual(observer: Observer<in Any>) {
        if (null == (observer)) {
            return
        }
        val listener = ItemClickListener(observer)
        observer.onSubscribe(listener)
        adapter.setMore(R.layout.view_loadmore1,listener)
    }

    private val adapter: RecyclerArrayAdapter<T>

    constructor(adapter: RecyclerArrayAdapter<T>){
        this.adapter = adapter
    }


    internal class ItemClickListener( private val observer: Observer<in Any>) : MainThreadDisposable(),RecyclerArrayAdapter.OnLoadMoreListener{
        override fun onLoadMore() {
            if (!isDisposed) {
                observer.onNext(Any())
            }
        }

        override fun onDispose() {
        }
    }
}