package demo.mvp.comkt.kotlin.activity

import android.content.Intent
import android.net.Uri
import android.preference.CheckBoxPreference
import android.preference.ListPreference
import android.preference.Preference
import android.preference.SwitchPreference
import android.widget.Toast
import demo.mvp.comkt.kotlin.R
import demo.mvp.comkt.kotlin.base.BasePreferenceActivity
import demo.mvp.comkt.kotlin.extension.showToast
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Created by HP on 2017/12/26.
 */
class SettingActivity : BasePreferenceActivity() {

    private var pre_download: CheckBoxPreference? = null
    private var pre_noimage: SwitchPreference? = null
    private var pre_push: SwitchPreference? = null
    private var pre_fontlist: ListPreference? = null

    private var pre_clearcache: Preference? = null
    private var pre_shareweibo: Preference? = null
    private var pre_update: Preference? = null
    private var pre_redback: Preference? = null

    override fun getLayout(): Int {
        return R.layout.activity_setting;
    }

    override fun getXml(): Int {
        getPreference()
        return R.xml.setting_activity
    }

    override fun initView() {
        setToolBar(toolbar, "设置")
        pre_download = findPreference("pre_download") as CheckBoxPreference
        pre_noimage = findPreference("pre_noimage") as SwitchPreference
        pre_push = findPreference("pre_push") as SwitchPreference
        pre_fontlist = findPreference("pre_fontlist") as ListPreference

        pre_shareweibo = findPreference("pre_shareweibo")
        pre_clearcache = findPreference("pre_clearcache")
        pre_update = findPreference("pre_update")
        pre_redback = findPreference("pre_redback")
    }

    override fun initData() {
        getVersion()
        initPreDownload(pre_download!!)
        initPreImage(pre_noimage!!)
        initFontList(pre_fontlist!!.value)
        pre_clearcache!!.summary = String.format("缓存大小：%s", "500KB")
        pre_download!!.setOnPreferenceClickListener {
            initPreDownload(it as CheckBoxPreference)
            return@setOnPreferenceClickListener true
        }
        pre_noimage!!.setOnPreferenceClickListener {
            initPreImage(it as SwitchPreference)
            return@setOnPreferenceClickListener true
        }


        pre_fontlist!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            initFontList(newValue as String)
            true
        }


        pre_shareweibo!!.setOnPreferenceClickListener {
            this.showToast("pre_shareweibo");
            return@setOnPreferenceClickListener true
        }

        pre_clearcache!!.setOnPreferenceClickListener {
            Observable.create(object : ObservableOnSubscribe<Any> {
                override fun subscribe(e: ObservableEmitter<Any>) {
                    //CacheUtils.clearAllCache(this@SettingActivity)
                    this@SettingActivity.showToast("pre_clearcache");
                    e.onNext(e)
                    e.onComplete()
                }
            })
                    .subscribe({
                        Toast.makeText(this@SettingActivity, "清除成功", Toast.LENGTH_SHORT).show()
                        pre_clearcache!!.summary = String.format("缓存大小：%s", "156")
                    })
            return@setOnPreferenceClickListener true
        }

        pre_update!!.setOnPreferenceClickListener {
            Toast.makeText(this@SettingActivity, "已是最新版本", Toast.LENGTH_SHORT).show()
            return@setOnPreferenceClickListener true
        }

        pre_redback!!.setOnPreferenceClickListener {
            val uri = Uri.parse(getString(R.string.downloaddir))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            return@setOnPreferenceClickListener true
        }
    }


    private fun getVersion() {
        val packageManager = packageManager
        val packInfo = packageManager.getPackageInfo(packageName, 0)
        pre_update!!.summary = String.format("版本：%s", packInfo.versionName)
    }

    private fun initFontList(value: String) {
        var stringArray = resources.getStringArray(R.array.font_id_list)
        for (s in stringArray.indices) {
            if (stringArray[s] == value) {
                pre_fontlist!!.summary = resources.getStringArray(R.array.font_name_list).get(s)
            }
        }
    }

    private fun initPreDownload(it: CheckBoxPreference) {
        if (it!!.isChecked) {
            it.summary = String.format("%s（已勾选）", it.summary.toString().replace("（未勾选）", ""))
        } else {
            it.summary = String.format("%s（未勾选）", it.summary.toString().replace("（已勾选）", ""))
        }
    }

    private fun initPreImage(it: SwitchPreference) {
        if (it!!.isChecked) {
            it.summary = String.format("%s（已打开）", it.summary.toString().replace("（未打开）", ""))
        } else {
            it.summary = String.format("%s（未打开）", it.summary.toString().replace("（已打开）", ""))
        }
    }

}