package com.github.shahriyar13.webview.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.shahriyar13.webview.R
import com.github.shahriyar13.webview.data.local.SharedPreference
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 221

        fun getNewIntent(context: Context) = Intent(context, SettingActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        buttonLoad.setOnClickListener {
            val url = editTextUrl.text.toString()
            if (url.isNotEmpty()) {
                SharedPreference.save(
                    SharedPreference.Key.Address,
                    url
                )
                SharedPreference.save(
                    SharedPreference.Key.ShowError,
                    checkBoxShowError.isChecked
                )
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                editTextUrl.error = "empty"
                editTextUrl.requestFocus()
            }
        }
    }
}