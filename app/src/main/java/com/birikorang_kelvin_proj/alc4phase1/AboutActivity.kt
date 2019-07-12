package com.birikorang_kelvin_proj.alc4phase1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.birikorang_kelvin_proj.alc4phase1.utils.NetworkStateUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_about.*
import org.jetbrains.anko.toast


class AboutActivity : AppCompatActivity() {
    private var webView: WebView? = null
    private var loadingProgress: ProgressBar? = null
    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        webView = web_view
        loadingProgress = progressBar
        loadingProgress?.visibility = View.GONE
        if (NetworkStateUtil.isOnline(this)) {
            loadAndelaAboutPage()
        } else {
            showNoConnectivityMessage()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadAndelaAboutPage() {
        if (!NetworkStateUtil.isOnline(this)) {
            showNoConnectivityMessage()
            return
        }
        webView?.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                showProgress()
                val url = request?.url.toString()
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                showProgress(false)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                toast(error.toString())
            }
        }

        val webSettings = webView?.settings
        webSettings?.javaScriptEnabled = true
        webSettings?.useWideViewPort = true
        webSettings?.loadWithOverviewMode = true
        webSettings?.domStorageEnabled = true
        webView?.isHorizontalScrollBarEnabled = true
        webSettings?.setAppCacheEnabled(true)
        webSettings?.databaseEnabled = true
        webView?.isVerticalScrollBarEnabled = false
        webSettings?.builtInZoomControls = true
        webSettings?.displayZoomControls = false
        webSettings?.allowFileAccess = true
        webView?.isScrollbarFadingEnabled = false
        webSettings?.cacheMode = WebSettings.LOAD_NO_CACHE
        webView?.webViewClient = WebViewClient()
        webView?.setInitialScale(1)
        webView?.loadUrl("https://andela.com/alc/")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun showProgress(show: Boolean = true) {
        if (show) {
            loadingProgress?.visibility = View.VISIBLE
        } else {
            loadingProgress?.visibility = View.GONE
        }
    }

    private fun showNoConnectivityMessage(msg: String = getString(R.string.connectivty_error)) {
        snackBar = Snackbar.make(root, msg, Snackbar.LENGTH_LONG)
        snackBar?.setAction("retry") {
            loadAndelaAboutPage()
        }
        snackBar?.show()
    }
}
