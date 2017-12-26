package demo.mvp.comkt.kotlin.adapter

import android.content.Context
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import io.reactivex.functions.Consumer

/**
 * Created by HP on 2017/12/25.
 */
abstract class EasyRecyclerAdapter<T> : RecyclerArrayAdapter<T> {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, objects: Array<out T>?) : super(context, objects)
    constructor(context: Context?, objects: MutableList<T>?) : super(context, objects)

    fun asLoadAction(): Consumer<List<T>> {
        return Consumer<List<T>>() {
            fun call(ts: List<T>) {
                clear()
                addAll(ts)
            }
        }
    }
}