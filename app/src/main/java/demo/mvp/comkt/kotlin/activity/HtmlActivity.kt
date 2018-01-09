package demo.mvp.comkt.kotlin.activity

import android.util.Log
import android.view.KeyEvent
import demo.mvp.comkt.kotlin.R
import demo.mvp.comkt.kotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_html.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.webkit.WebChromeClient




/**
 * Created by HP on 2017/12/28.
 */
class HtmlActivity : BaseActivity() {

    private var url : String? = null;

    override fun initView() {
        var title : TextView = findViewById<TextView>(R.id.title) as TextView;
        val webSettings = webView.settings
        webView.isVerticalScrollBarEnabled = false;
        webView.isHorizontalScrollBarEnabled = false;
        webSettings.javaScriptEnabled = true

        // init webview settings
        webSettings.allowContentAccess = true
        webSettings.databaseEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.setAppCacheEnabled(true)
        webSettings.savePassword = false
        webSettings.saveFormData = false
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true


        url = intent.getStringExtra("url");
        webView.loadUrl(url);

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
//                title.text = (view.title)
                Log.d("TTTT","view.title = " + view.title);
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // TODO Auto-generated method stub
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                Log.d("TTTT","url = " + url);
                if(url.startsWith("http")){
                    view.loadUrl(url);
                }
                return true
            }
        }


        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title1: String) {
                super.onReceivedTitle(view, title1)
                Log.d("ANDROID_LAB", "TITLE=" + title1)
                 title.text = (title1)
            }
        }

    }

    override fun attachRes(): Int {
        return R.layout.activity_html;
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){

            if(webView.canGoBack()){
                webView.goBack();
                return true
            }else{
                return super.onKeyDown(keyCode, event)
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}