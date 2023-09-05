package com.example.app1

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class ArticleWebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_web_view)

        val webView: WebView = findViewById(R.id.webView)
        val articleUrl = intent.getStringExtra("article_url")

        webView.settings.javaScriptEnabled = true

        webView.webViewClient = WebViewClient()

        if (!articleUrl.isNullOrEmpty()) {
            webView.loadUrl(articleUrl)
        }
    }
}
