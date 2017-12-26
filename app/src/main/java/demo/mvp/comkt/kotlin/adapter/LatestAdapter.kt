package demo.mvp.comkt.kotlin.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import demo.mvp.comkt.kotlin.R
import demo.mvp.comkt.kotlin.entity.Story
import demo.mvp.comkt.kotlin.utils.DateUtils
import demo.mvp.comkt.kotlin.utils.GlideImageLoader

/**
 * Created by HP on 2017/12/25.
 */
class LatestAdapter : EasyRecyclerAdapter<Story> {
    lateinit var map : HashMap<Int,String>;

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return LatestViewHolder(parent,map)
    }

    constructor(context: Context, map: HashMap<Int,String>) : super(context) {
        this.map = map;
    }

    class LatestViewHolder : BaseViewHolder<Story> {
        var mTvContent : TextView? = null
        var mTvType : TextView? = null
        var mTvTime : TextView? = null
        var mImgPic : ImageView? = null
        var map : HashMap<Int,String>? = null

        constructor(parent: ViewGroup, map : HashMap<Int,String>?) : super(parent, R.layout.item_latest){
            this.map = map;
            mTvContent = itemView.findViewById<TextView>(R.id.tv_content) as TextView;
            mTvType = itemView.findViewById<TextView>(R.id.tv_type)
            mTvTime = itemView.findViewById<TextView>(R.id.tv_time)
            mImgPic = itemView.findViewById<ImageView>(R.id.img_pic)

        };


        override fun setData(data: Story) {
            super.setData(data)
            if (map!!.contains(position)) {
                mTvType!!.visibility = View.VISIBLE
                if (position == 1) {
                    mTvType!!.text = context.getString(R.string.latestnews)
                } else {
                    mTvType!!.text = DateUtils.getFormatWeek(DateUtils.parseToDate(map!!.get(position)!!,DateUtils.dateFormat13)!!)
                }
            } else{
                mTvType!!.visibility = View.GONE
            }
            if (data.images == null || data.images.size == 0) {
                mImgPic!!.visibility = View.GONE
            } else{
                mImgPic!!.visibility = View.VISIBLE
                GlideImageLoader.loadImage(data.images.get(0),R.color.default1).centerCrop().into(mImgPic)
            }
            if (TextUtils.isEmpty(data.display_date)) {
                mTvTime?.visibility = View.GONE
            } else{
                mTvTime?.visibility = View.VISIBLE
                mTvTime?.text = data.display_date
            }
            mTvContent?.text = data.title
        }
    }
}