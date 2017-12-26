package demo.mvp.comkt.kotlin.customview

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import demo.mvp.comkt.kotlin.R
import kotlinx.android.synthetic.main.custom_tab_item_layout.view.*
import java.util.ArrayList

/**
 * Created by HP on 2017/12/22.
 */
class CustomTabView : LinearLayout, View.OnClickListener {
    private var mTabViews: MutableList<View>? = null
    private var mTabs: MutableList<Tab>? = null
    private var mOnTabCheckListener: OnTabCheckListener? = null

    fun setOnTabCheckListener(onTabCheckListener: OnTabCheckListener) {
        mOnTabCheckListener = onTabCheckListener
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
        mTabViews = ArrayList()
        mTabs = ArrayList()

    }

    /**
     * 添加Tab
     * @param tab
     */
    fun addTab(tab: Tab) {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_tab_item_layout, null)
        val textView   = view.findViewById<TextView>(R.id.custom_tab_text) as TextView
        val imageView  = view.findViewById<ImageView>(R.id.custom_tab_icon) as ImageView
        imageView.setImageResource(tab.mIconNormalResId)
        textView.text = tab.mText
        textView.setTextColor(tab.mNormalColor)

        view.setTag(mTabViews!!.size)
        view.setOnClickListener(this)

        mTabViews!!.add(view)
        mTabs!!.add(tab)

        addView(view)

    }

    /**
     * 设置选中Tab
     * @param position
     */
    fun setCurrentItem(position: Int) {
        var position = position
        if (position >= mTabs!!.size || position < 0) {
            position = 0
        }

        mTabViews!![position].performClick()

        updateState(position)


    }

    /**
     * 更新状态
     * @param position
     */
    private fun updateState(position: Int) {

        if(null != mTabViews){
            mTabViews!!.indices.forEach { i ->
                val view = mTabViews!![i]
                //弃用该方式
            val textView = view.findViewById<TextView>(R.id.custom_tab_text) as TextView
            val imageView = view.findViewById<ImageView>(R.id.custom_tab_icon) as ImageView

                if (i == position) {
                    imageView.setImageResource(mTabs!![i].mIconPressedResId)
                    textView.setTextColor(mTabs!![i].mSelectColor)

                } else {
                    imageView.setImageResource(mTabs!![i].mIconNormalResId)
                    textView.setTextColor(mTabs!![i].mNormalColor)
                }
            }
        }

    }


    override fun onClick(v: View) {
        val position = v.tag as Int
        if(null != mOnTabCheckListener){
            mOnTabCheckListener!!.onTabSelected(v, position)
        }

        updateState(position)
    }

    interface OnTabCheckListener {
        fun onTabSelected(v: View, position: Int)
    }


    class Tab {
        public var mIconNormalResId: Int = 0
        public var mIconPressedResId: Int = 0
        public var mNormalColor: Int = 0
        public var mSelectColor: Int = 0
        public var mText: String? = null


        fun setText(text: String): Tab {
            mText = text
            return this
        }

        fun setNormalIcon(res: Int): Tab {
            mIconNormalResId = res
            return this
        }

        fun setPressedIcon(res: Int): Tab {
            mIconPressedResId = res
            return this
        }

        fun setColor(color: Int): Tab {
            mNormalColor = color
            return this
        }

        fun setCheckedColor(color: Int): Tab {
            mSelectColor = color
            return this
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mTabViews != null) {
            mTabViews!!.clear()
        }
        if (mTabs != null) {
            mTabs!!.clear()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        for (i in mTabViews!!.indices) {
            val view = mTabViews!![i]
            val width = resources.displayMetrics.widthPixels / mTabs!!.size
            val params = LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT)

            view.layoutParams = params
        }

    }
}
