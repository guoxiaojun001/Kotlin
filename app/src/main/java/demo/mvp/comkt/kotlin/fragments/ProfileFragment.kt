package demo.mvp.comkt.kotlin.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import demo.mvp.comkt.kotlin.R
import kotlinx.android.synthetic.main.home_fragment_layout.*


/**
 * Created by HP on 2017/12/22.
 */
class ProfileFragment : Fragment() {

    private var mFrom : String? = null

    fun newInstance(from : String): ProfileFragment {
        var fragment = ProfileFragment();
        var bounder = Bundle();
        bounder.putString("from",from);
        fragment.arguments = bounder;

        return  fragment;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            mFrom = arguments.getString("from");
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("TTTT","ProfileFragment onResume = " );
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //不为空的时候  调用此方法
        var view = inflater?.inflate(R.layout.home_fragment_layout,null);

        var textView = view!!.findViewById<TextView>(R.id.title_from) as TextView;

        textView.text=mFrom;

        var text2 = view.findViewById<TextView>(R.id.fragment_content) as TextView;
        text2.text = "ProfileFragment";
        return view;
    }

}