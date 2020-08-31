package com.github.shahriyar13.webview.ui

import android.content.Intent
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.shahriyar13.webview.R
import com.github.shahriyar13.webview.data.local.SharedPreference
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUrl()
    }

    private fun initializeUrl() {
        val url =
            SharedPreference.getString(
                SharedPreference.Key.Address
            )

        if (url.isEmpty()) {
            startActivityForResult(
                SettingActivity.getNewIntent(
                    this
                ),
                SettingActivity.REQUEST_CODE
            )
        } else {
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true
            webView.settings.builtInZoomControls = true
            webView.settings.displayZoomControls = false
            webView.settings.javaScriptEnabled = true

            webView.webViewClient = object : WebViewClient() {
                @RequiresApi(Build.VERSION_CODES.M)
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    showLowRiskError(error?.description.toString() + " " + request?.url.toString())
                    super.onReceivedError(view, request, error)
                }

                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    showLowRiskError(errorResponse?.reasonPhrase + " " + request?.url.toString())
                    super.onReceivedHttpError(view, request, errorResponse)
                }

                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler,
                    error: SslError?
                ) {
                    showHighRiskError("Unsafe Url\n" + (error?.certificate?.toString() ?: "(SSL Error)"))
                    handler.proceed() // Ignore SSL certificate errors
                }
            }

            webView.loadUrl(url)
        }
    }

    private fun showHighRiskError(message: String) {
        if (SharedPreference.getBoolean(
                SharedPreference.Key.ShowError
            )
        ) {
            textViewHighRiskError.text = message
            textViewHighRiskError.isVisible = true
            textViewHighRiskError.setOnClickListener { textViewHighRiskError.isVisible = false }
        }
    }

    private fun showLowRiskError(message: String) {
        if (SharedPreference.getBoolean(
                SharedPreference.Key.ShowError
            )
        ) {
            textViewLowRiskError.text = message
            textViewLowRiskError.isVisible = true
            textViewLowRiskError.setOnClickListener { textViewLowRiskError.isVisible = false }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SettingActivity.REQUEST_CODE) {
            initializeUrl()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}