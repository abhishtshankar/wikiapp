package com.example.app1
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.SavedArticleEntity
import com.example.app1.R

class HistoryAdapter : ListAdapter<SavedArticleEntity, HistoryAdapter.HistoryViewHolder>(HistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_article, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val savedArticle = getItem(position)
        holder.bind(savedArticle)
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.savedTitleTextView)
        private val contentTextView: TextView = itemView.findViewById(R.id.savedContentTextView)
        private val urlTextView: TextView = itemView.findViewById(R.id.savedUrlTextView)

        fun bind(savedArticle: SavedArticleEntity) {
            titleTextView.text = savedArticle.title
            contentTextView.text = savedArticle.content
            urlTextView.text = savedArticle.url
        }
    }

    private class HistoryDiffCallback : DiffUtil.ItemCallback<SavedArticleEntity>() {
        override fun areItemsTheSame(oldItem: SavedArticleEntity, newItem: SavedArticleEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SavedArticleEntity, newItem: SavedArticleEntity): Boolean {
            return oldItem == newItem
        }
    }
}
