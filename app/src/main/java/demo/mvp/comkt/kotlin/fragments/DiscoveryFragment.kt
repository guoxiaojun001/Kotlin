package demo.mvp.comkt.kotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dl7.mvp.module.base.BaseFragment
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import demo.mvp.comkt.kotlin.R
import demo.mvp.comkt.kotlin.activity.HtmlActivity
import demo.mvp.comkt.kotlin.adapter.StringAdapter
import demo.mvp.comkt.kotlin.entity.TestBean
import demo.mvp.comkt.kotlin.extension.showToast
import demo.mvp.comkt.kotlin.presenter.DiscoveryPresenter
import kotlinx.android.synthetic.main.custom_tab_ac_layout.view.*
import kotlinx.android.synthetic.main.home_fragment_layout.*


/**
 * Created by HP on 2017/12/22.
 */
class DiscoveryFragment : BaseFragment<DiscoveryPresenter>() {

    private var mFrom : String ? = null;
    private var mAdapter : StringAdapter? = null ;

    fun newInstance(from : String ) : DiscoveryFragment{
        val fragment = DiscoveryFragment()
        var bundle = Bundle();
        bundle.putString("from",from);
        fragment.arguments=bundle;

        return fragment;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            mFrom = arguments.getString("from");
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("TTTT","DiscoveryFragment onResume = " );
    }


    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //不为空的时候  调用此方法
        var view = inflater?.inflate(R.layout.discovery_fragment_layout,null);
        //var mRecyclerView = view!!.findViewById<EasyRecyclerView>(R.id.mRecyclerView) as EasyRecyclerView;
        return view;
    }


    override fun initView() {
        mAdapter = StringAdapter(context, ArrayList<String>());
        mRecyclerView.setLayoutManager(LinearLayoutManager(context));
        mRecyclerView.adapter = mAdapter;
        mRecyclerView?.setRefreshingColorResources(R.color.colorPrimaryDark,R.color.colorPrimary);
        mAdapter?.setNoMore(R.layout.view_nomore);


        mAdapter?.setOnItemClickListener {
            context.showToast("setOnItemClickListener")
            var intent = Intent(activity, HtmlActivity::class.java);
            intent.putExtra("url","http://blog.csdn.net/xx326664162/article/details/50902089");
            context.startActivity(intent)
            return@setOnItemClickListener Unit
        }


        mAdapter?.setOnItemLongClickListener {
            return@setOnItemLongClickListener true
        }


    }

    override fun initData() {
        var array = arrayListOf("1","2","3","4","5","6","7","8","9");

        var mList =  ArrayList<String>();
        var list  = ArrayList<TestBean>();

        var tt = TestBean.ChildListBean("CC",array);
        var ll = arrayListOf<TestBean.ChildListBean>(tt);
        var demo1  = TestBean("11", ll);
        list.add(demo1)


        list.forEach {
            it.issueList!!
                    .flatMap { it.itemList!! }
                    .filter{it.equals("5")}
                    .forEach{mList.add(it)}

            Log.d("TTTTT","mList == " + mList);
        }


//        list!!
//              .flatMap { it.itemList!! }
//              .filter { it.equals("video") }
//              .forEach { list.add(it) }


        mAdapter?.addAll(array)
    }

}