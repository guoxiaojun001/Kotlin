package demo.mvp.comkt.kotlin.entity

/**
 * Created by HP on 2017/12/28.
 */

data class TestBean(var name : String, var issueList:List<ChildListBean>?){


    data class ChildListBean( var detail: String,
                             var itemList:List<String>){

    }

}