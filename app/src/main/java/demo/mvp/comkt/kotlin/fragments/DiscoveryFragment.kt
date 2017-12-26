package demo.mvp.comkt.kotlin.fragments

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
import demo.mvp.comkt.kotlin.R
import demo.mvp.comkt.kotlin.adapter.StringAdapter
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
    }

    override fun initData() {
        var array = arrayListOf("1","2","3","4","5","6","7","8","9");

        mAdapter?.addAll(array)
    }

}