package com.example.app1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleViewModel(application: Application) : AndroidViewModel(application) {
    val articles: MutableLiveData<List<Article>> = MutableLiveData()
    val categories: MutableLiveData<List<Category>> = MutableLiveData()
    val images: MutableLiveData<List<ImageInfo>> = MutableLiveData()

    private val appDatabase: AppDatabase = AppDatabase.getDatabase(application)

    init {
        loadArticles()
        loadCategories()
        loadImages()
        loadNextArticles()

    }

    private fun loadArticles() {
        val apiService = Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikipediaApiService::class.java)

        val call = apiService.getRandomArticles()
        val articleList = mutableListOf<Article>()

        call.enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(
                call: Call<Map<String, Any>>,
                response: Response<Map<String, Any>>
            ) {
                if (response.isSuccessful) {
                    val query = response.body()?.get("query") as Map<String, Any>
                    val pages = query["pages"] as Map<String, Any>

                    pages.entries.forEach { entry ->
                        val pageData = entry.value as Map<String, Any>
                        val title = pageData["title"].toString()
                        val revisions = pageData["revisions"] as List<Map<String, Any>>
                        val content = revisions[0]["*"].toString()
                        val url =
                            "https://en.wikipedia.org/wiki/$title"

                        articleList.add(Article(title, content, url))
                    }

                    articles.value = articleList
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    fun loadNextArticles() {
        val apiService = Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikipediaApiService::class.java)

        val call = apiService.getRandomArticles()
        val articleList = mutableListOf<Article>()

        call.enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(
                call: Call<Map<String, Any>>,
                response: Response<Map<String, Any>>
            ) {
                if (response.isSuccessful) {
                    val query = response.body()?.get("query") as Map<String, Any>
                    val pages = query["pages"] as Map<String, Any>

                    pages.entries.forEach { entry ->
                        val pageData = entry.value as Map<String, Any>
                        val title = pageData["title"].toString()
                        val revisions = pageData["revisions"] as List<Map<String, Any>>
                        val content = revisions[0]["*"].toString()
                        val url =
                            "https://en.wikipedia.org/wiki/$title"
                        articleList.add(Article(title, content, url))
                    }

                    articles.value = articleList
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                // Handle failure
            }
        })
    }
    private fun loadCategories() {
        val categoryApiService = Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryApiService::class.java)

        val categoryCall = categoryApiService.getAllCategories()

        categoryCall.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    val categoryResponse = response.body()
                    val categoryList = categoryResponse?.query?.allcategories ?: emptyList()
                    categories.value = categoryList
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun loadImages() {
        val imageApiService = Retrofit.Builder()
            .baseUrl("https://commons.wikimedia.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApiService::class.java)

        val imageCall = imageApiService.getImages()

        imageCall.enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {
                    val imageResponse = response.body()
                    val imageList = imageResponse?.query?.pages?.values?.mapNotNull { page ->
                        val imageInfo = page.imageinfo[0]
                        val title = imageInfo.title
                        val url = imageInfo.url
                        if (title != null) {
                            ImageInfo(title, url)
                        } else {
                            null
                        }
                    } ?: emptyList()

                    images.value = imageList
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    fun saveArticle(article: Article) {
        val savedArticleEntity = SavedArticleEntity(
            title = article.title,
            content = article.content,
            url = article.url
        )
        viewModelScope.launch {
            appDatabase.savedArticleDao().insertSavedArticle(savedArticleEntity)
        }
    }

    fun getAllSavedArticles(): LiveData<List<SavedArticleEntity>> {
        return appDatabase.savedArticleDao().getAllSavedArticles()
    }
}
