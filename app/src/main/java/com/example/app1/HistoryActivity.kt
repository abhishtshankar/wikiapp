package com.example.app1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.ArticleViewModel
import com.example.app1.SavedArticleEntity

class HistoryActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticleViewModel
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val recyclerView: RecyclerView = findViewById(R.id.historyRecyclerView)
        adapter = HistoryAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        viewModel.getAllSavedArticles().observe(this, Observer { savedArticles ->
            adapter.submitList(savedArticles)
        })
    }
}
