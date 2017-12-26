package demo.mvp.comkt.kotlin.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import demo.mvp.comkt.kotlin.R
import demo.mvp.comkt.kotlin.entity.Story
import kotlinx.android.synthetic.main.discovery_fragment_layout.view.*

/**
 * Created by HP on 2017/12/26.
 */
class StringAdapter : EasyRecyclerAdapter<String> {

   var array : ArrayList<String>? = null ;

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        return StringHolder(parent,array);
    }


    constructor(context: Context, arraylist : ArrayList<String>) : super(context){
        this.array = arraylist;
    };


    class StringHolder : BaseViewHolder<String>{

        var list_item : TextView? = null;
        var array : ArrayList<String>?;
        constructor(view : ViewGroup,array : ArrayList<String>?) : super(view, R.layout.string_item){
            this.array = array;
            list_item = itemView.findViewById<TextView>(R.id.list_item);
        };


        override fun setData(data: String) {
            super.setData(data)
            if(null != list_item){
                list_item!!.text = data;
            }
        }
    }

}