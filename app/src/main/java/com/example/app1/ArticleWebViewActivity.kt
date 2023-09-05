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

        // Configure the WebView settings
        webView.settings.javaScriptEnabled = true

        // Create a WebViewClient to handle page navigation within the WebView
        webView.webViewClient = WebViewClient()

        // Load the article URL in the WebView
        if (!articleUrl.isNullOrEmpty()) {
            webView.loadUrl(articleUrl)
        }
    }
}
