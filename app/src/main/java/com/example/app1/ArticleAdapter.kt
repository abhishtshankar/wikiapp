package com.example.app1

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
    private val images: List<ImageInfo>
    ) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView) // Add this line

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(itemView)
    }

override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
    val article = articles[position]
    holder.titleTextView.text = article.title

    if (position < categories.size) {
        holder.categoryTextView.text = categories[position].category
    } else {
        holder.categoryTextView.text = "" // No category available
    }
    if (position < images.size) {
        Picasso.get().load(images[position].url).into(holder.imageView)
    } else {
        holder.imageView.setImageResource(R.drawable.img)
    }

    holder.itemView.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
        it.context.startActivity(intent)
    }
}    override fun getItemCount(): Int {
        return articles.size
    }

}


