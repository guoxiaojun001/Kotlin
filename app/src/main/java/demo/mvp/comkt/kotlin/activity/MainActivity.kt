package demo.mvp.comkt.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import demo.mvp.comkt.kotlin.R.id.custom_tab_container
import demo.mvp.comkt.kotlin.customview.CustomTabView
import demo.mvp.comkt.kotlin.fragments.*

class MainActivity : AppCompatActivity(), CustomTabView.OnTabCheckListener {

    val mTabRes = intArrayOf(R.drawable.tab_home_selector,
            R.drawable.tab_discovery_selector,
            R.drawable.tab_attention_selector,
            R.drawable.tab_profile_selector)
    val mTabResPressed = intArrayOf(R.mipmap.ic_tab_strip_icon_feed_selected,
            R.mipmap.ic_tab_strip_icon_category_selected,
            R.mipmap.ic_tab_strip_icon_pgc_selected,
            R.mipmap.ic_tab_strip_icon_profile_selected)
    val mTabTitle = arrayOf("首页", "发现", "关注", "我的")

    private var mCustomTabView : CustomTabView?  = null;

    private var mFragmensts : List<Fragment>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_tab_ac_layout)
        mFragmensts = getFragments( "CustomTabView Tab");
        initView();
    }

    private fun initView() {
        mCustomTabView = findViewById<CustomTabView>(R.id.custom_tab_container) as CustomTabView;
        var tabHome = CustomTabView.Tab().setText("首页")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.ic_tab_strip_icon_profile)
                .setPressedIcon(R.mipmap.ic_tab_strip_icon_profile_selected);
        mCustomTabView?.addTab(tabHome);

        val tabDis = CustomTabView.Tab().setText("发现")
                .setColor(resources.getColor(android.R.color.darker_gray))
                .setCheckedColor(resources.getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.ic_tab_strip_icon_category)
                .setPressedIcon(R.mipmap.ic_tab_strip_icon_category_selected)
        mCustomTabView?.addTab(tabDis);

        val tabAttention = CustomTabView.Tab().setText("管制")
                .setColor(resources.getColor(android.R.color.darker_gray))
                .setCheckedColor(resources.getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.ic_tab_strip_icon_pgc)
                .setPressedIcon(R.mipmap.ic_tab_strip_icon_pgc_selected)
        mCustomTabView?.addTab(tabAttention);

        val tabProfile = CustomTabView.Tab().setText("我的")
                .setColor(resources.getColor(android.R.color.darker_gray))
                .setCheckedColor(resources.getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.ic_tab_strip_icon_feed)
                .setPressedIcon(R.mipmap.ic_tab_strip_icon_feed_selected)
        mCustomTabView?.addTab(tabProfile);

        mCustomTabView?.setOnTabCheckListener(this);
        mCustomTabView?.setCurrentItem(0);
    }



    fun getFragments(from: String): ArrayList<Fragment> {
        var list = ArrayList<Fragment>();
        list.add(HomeFragment().newInstance(from));
        list.add(DiscoveryFragment().newInstance(from));
        list.add(AttentionFragment().newInstance(from));
        list.add(ProfileFragment().newInstance(from));

        return list;
    }


    override fun onTabSelected(v: View, position: Int) {
        Log.d("TTTT", "position:" + position)
        onTabItemSelected(position);
    }


    private fun onTabItemSelected(position: Int) {
        var fragment: Fragment? = null
        when (position) {
            0->{
                fragment = mFragmensts?.get(0);
            };

            1->{
                fragment = mFragmensts?.get(1);
            };

            2->{
                fragment = mFragmensts?.get(2);
            };

            3->{
                fragment = mFragmensts?.get(3);
            };

            else -> {

                Log.d("TTTT","其他tab");
            }

        }
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.home_container, fragment).commit()
        }
    }

}
