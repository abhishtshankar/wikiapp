package com.example.app1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ArticleAdapter(
    private var articles: List<Article>,
    private val categories: List<Category>,
    private val images: List<ImageInfo>,
    private val viewModel: ArticleViewModel ,
    private val context: Context
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(itemView)
    }

//override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
//    val article = articles[position]
//    holder.titleTextView.text = article.title
//
//    if (position < categories.size) {
//        holder.categoryTextView.text = categories[position].category
//    } else {
//        holder.categoryTextView.text = ""
//    }
//    if (position < images.size) {
//        Picasso.get().load(images[position].url).into(holder.imageView)
//    } else {
//        holder.imageView.setImageResource(R.drawable.img)
//    }
//
//    holder.itemView.setOnClickListener {
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
//        it.context.startActivity(intent)
//    }
//    holder.itemView.setOnClickListener {
//    }
//}

    override fun onBindViewHolder(holder: ArticleAdapter.ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title

        if (position < categories.size) {
            holder.categoryTextView.text = categories[position].category
        } else {
            holder.categoryTextView.text = ""
        }

        if (position < images.size) {
            Picasso.get().load(images[position].url).into(holder.imageView)
        } else {
            holder.imageView.setImageResource(R.drawable.img)
        }

        // Set a single click listener to open the article using WebView
        holder.itemView.setOnClickListener {
            // Launch the ArticleWebViewActivity and pass the article URL
            val intent = Intent(context, ArticleWebViewActivity::class.java)
            intent.putExtra("article_url", article.url)
            context.startActivity(intent)
            viewModel.saveArticle(article)

        }
    }


    override fun getItemCount(): Int {
        return articles.size
    }

}




