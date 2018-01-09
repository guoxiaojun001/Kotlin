package demo.mvp.comkt.kotlin.activity

import demo.mvp.comkt.kotlin.R
import demo.mvp.comkt.kotlin.base.BaseActivity

/**
 * Created by HP on 2018/1/9.
 */
class ConstantActivity : BaseActivity() {
    override fun attachRes(): Int {
        return R.layout.child_item;
    }

    override fun initView() {    }


}