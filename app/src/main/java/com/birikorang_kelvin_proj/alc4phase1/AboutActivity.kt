package com.birikorang_kelvin_proj.alc4phase1

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.birikorang_kelvin_proj.alc4phase1.utils.NetworkStateUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_about.*
import org.jetbrains.anko.toast


class AboutActivity : AppCompatActivity() {
    private var webView: WebView? = null
    private var loadingProgress: ProgressBar? = null
    private var snackBar: Snackbar? = null
    private var alertBuilder:AlertDialog.Builder? = null

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

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                alertBuilder = AlertDialog.Builder(this@AboutActivity)
                alertBuilder?.setTitle("Warning")
                alertBuilder?.setMessage(getString(R.string.ssl_warning))
                alertBuilder?.setPositiveButton("proceed"){dialog, _ ->
                    handler?.proceed()
                    dialog.dismiss()
                }
                alertBuilder?.setNegativeButton("cancel"){dialog, _ ->
                    handler?.cancel()
                    dialog.dismiss()
                }
                alertBuilder?.setCancelable(false)
                alertBuilder?.show()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                toast(error.toString())
            }
        }

        val webSettings = webView?.settings
        webSettings?.javaScriptEnabled = true
        webSettings?.useWideViewPort = true
        webView?.loadUrl(getString(R.string.andela_about_page_url))
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
