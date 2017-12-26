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
import com.fire.zhihudaily.network.HttpClient
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.jude.rollviewpager.RollPagerView
import com.trello.rxlifecycle2.android.FragmentEvent
import demo.mvp.comkt.kotlin.R
import demo.mvp.comkt.kotlin.adapter.LatestAdapter
import demo.mvp.comkt.kotlin.entity.LatestNews
import demo.mvp.comkt.kotlin.entity.Story
import demo.mvp.comkt.kotlin.entity.Top_story
import demo.mvp.comkt.kotlin.extension.showToast
import demo.mvp.comkt.kotlin.presenter.HomePresenter
import demo.mvp.comkt.kotlin.rx.*
import demo.mvp.comkt.kotlin.utils.DateUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.home_fragment_layout.*
import retrofit2.Response


/**
 * Created by HP on 2017/12/22.
 */
class HomeFragment : BaseFragment<HomePresenter>() {

    private var mFrom: String? = null
    private lateinit var mAdapter : LatestAdapter;
    private var position: Int = 0
    private var mHeaderView: View? = null
    private var mTopStories: List<Top_story>? = null


    fun newInstance(form : String ) : HomeFragment{
        val fragment = HomeFragment()
        var bundle = Bundle();
        bundle.putString("from",form);
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
        context.showToast("-----onResume-----");
        Log.d("TTTT","HomeFragment onResume = " );
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //不为空的时候  调用此方法
        var view = inflater?.inflate(R.layout.home_fragment_layout,null);
        var textView = view?.findViewById<TextView>(R.id.title_from) as TextView;

        textView.text=mFrom;

        var text2 = view?.findViewById<TextView>(R.id.fragment_content) as TextView;
        text2.text = "Homefragment";
        //mRecyclerView = view?.findViewById(R.id.mRecyclerView)
        return view

    }

    override fun initView() {
        mAdapter = LatestAdapter(context, HashMap<Int, String>());
        mRecyclerView.setLayoutManager(LinearLayoutManager(activity));
        mRecyclerView.adapter = mAdapter;
        mRecyclerView?.setRefreshingColorResources(R.color.colorPrimaryDark,R.color.colorPrimary);
        mAdapter?.setNoMore(R.layout.view_nomore);
    }

    override fun initData() {
        RxViews.setLoadMore(mAdapter!!)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        loadData(false)
                    }
                })

        RxViews.onItemClick(mAdapter!!)
                .compose(DefaultButtonTransformer<Int>())
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Int>() {
                    override fun onNext(it: Int) {
//                        var intent = Intent(activity, ArticledetialActivity::class.java);
//                        intent!!.putExtra("data", mAdapter!!.getItem(it))
//                        startActivity(intent)
                    }
                })

        RxViews.adapterLoadMoreError(mAdapter!!)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        loadData(false)
                    }
                })

        RxViews.EasyRecyclererrorViewClick(mRecyclerView!!)
                .compose(RxViews.transBOOL(true))
                .startWith(true)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : OtherObser<Any>() {
                    override fun onNext(t: Any) {
                        loadData(true)
                    }
                })

        mRecyclerView.setRefreshListener {
            loadData(true)
        }
    }

    private fun loadData(isFirst: Boolean) {
        if (mAdapter!!.allData.size == 0) {
            mRecyclerView.showProgress()
        }
        var observable: Observable<Response<LatestNews>>? = null;
        if (isFirst) {
            position = 0
            observable = HttpClient.getInstance()
                    .service()
                    .latestNews()
        } else {
            observable = HttpClient.getInstance()
                    .service()
                    .beforeNews(DateUtils.formatDateBackDays(-position));
        }
        observable!!.compose(SchedulerTransformer<Response<LatestNews>>())
                .compose(DataTransformer<LatestNews>())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    mRecyclerView.showRecycler()
                    if (isFirst) {
                        mAdapter!!.map!!.clear()
                        mTopStories = it.top_stories;
                    }
                    mAdapter!!.map!!.put(mAdapter!!.count + 1, it.date)
                    return@map it.stories
                }
                .subscribe(object : RecyclerNetObser<Story>(mRecyclerView) {
                    override fun onNext(it: List<Story>){
                        super.onNext(it)
                        if (isFirst) {
                            mAdapter!!.allData!!.clear()
                        }
                        mAdapter?.addAll(it)
                        position++
                    }
                })
    }

}