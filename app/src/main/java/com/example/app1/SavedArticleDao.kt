package com.example.app1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedArticleDao {
    @Query("SELECT * FROM saved_articles")
    fun getAllSavedArticles(): LiveData<List<SavedArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedArticle(article: SavedArticleEntity)

    @Delete
    suspend fun deleteSavedArticle(article: SavedArticleEntity)
}
