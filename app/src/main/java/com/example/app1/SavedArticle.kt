package com.example.app1

import androidx.room.PrimaryKey

data class SavedArticle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val url: String
)
