package com.dl7.mvp.module.base



import android.content.Context
import android.os.Bundle
import demo.mvp.comkt.kotlin.base.IBasePresenter
import demo.mvp.comkt.kotlin.base.IBaseView
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by long on 2016/5/31.
 * 碎片基类
 */
abstract class BaseFragment<T : IBasePresenter> :  RxFragment(), IBaseView {


    protected var mPresenter : T? = null;
    protected lateinit var mContext : Context;


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView();
        initData();
    }


    abstract fun initView()

    abstract fun initData()

}
