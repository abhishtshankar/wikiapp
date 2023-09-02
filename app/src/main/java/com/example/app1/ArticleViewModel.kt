package com.example.app1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleViewModel : ViewModel() {
    val articles: MutableLiveData<List<Article>> = MutableLiveData()
    val categories: MutableLiveData<List<Category>> = MutableLiveData()
    val images: MutableLiveData<List<ImageInfo>> = MutableLiveData()

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

                    // Extract titles and URLs and add them to the list
                    pages.entries.forEach { entry ->
                        val pageData = entry.value as Map<String, Any>
                        val title = pageData["title"].toString()
                        val revisions = pageData["revisions"] as List<Map<String, Any>>
                        val content = revisions[0]["*"].toString()
                        val url =
                            "https://en.wikipedia.org/wiki/$title" // Construct the article URL

                        articleList.add(Article(title, content, url)) // Initialize category as null
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

                    // Extract titles and URLs and add them to the list
                    pages.entries.forEach { entry ->
                        val pageData = entry.value as Map<String, Any>
                        val title = pageData["title"].toString()
                        val revisions = pageData["revisions"] as List<Map<String, Any>>
                        val content = revisions[0]["*"].toString()
                        val url =
                            "https://en.wikipedia.org/wiki/$title" // Construct the article URL

                        articleList.add(Article(title, content, url)) // Initialize category as null
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
                            null // Skip items with null titles
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
}
