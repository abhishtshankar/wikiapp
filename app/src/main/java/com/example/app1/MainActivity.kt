package com.example.app1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticleViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        // Initialize the toolbar
        setSupportActionBar(toolbar)

        // Set the title of the toolbar
        supportActionBar?.title = "MY WIKI"

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ArticleViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.articleRecyclerView)
        viewModel.articles.observe(this, Observer { articles ->
            // When articles are updated, update the adapter
            viewModel.categories.observe(this, Observer { categories ->
                viewModel.images.observe(this, Observer { images ->
                    val adapter = ArticleAdapter(articles, categories, images)
                    recyclerView.adapter = adapter
                })
            })
        })

        // Inside MainActivity onCreate method

        val nextButton1: Button = findViewById(R.id.next1)
        val nextButton2: Button = findViewById(R.id.next2)
        val nextButton3: Button = findViewById(R.id.next3)

        nextButton1.setOnClickListener {
            viewModel.loadNextArticles()
        }

        nextButton2.setOnClickListener {
            viewModel.loadNextArticles()
        }

        nextButton3.setOnClickListener {
            viewModel.loadNextArticles()
        }

    }

}

