package com.example.app1

import retrofit2.Call
import retrofit2.http.GET

interface WikipediaApiService {
    @GET("w/api.php?format=json&action=query&generator=random&grnnamespace=0&prop=revisions|images&rvprop=content&grnlimit=10")
    fun getRandomArticles(): Call<Map<String, Any>>

}


data class CategoryResponse(val query: CategoryQuery)

data class CategoryQuery(val allcategories: List<Category>)

interface CategoryApiService {
    @GET("w/api.php?acprefix_=List+of&action=query&formatversion=2&list=allcategories&format=json")
    fun getAllCategories(): Call<CategoryResponse>
}


data class ImageResponse(val query: ImageQuery)

data class ImageQuery(val pages: Map<String, ImagePage>)

data class ImagePage(val imageinfo: List<ImageInfo>)

interface ImageApiService {
    @GET("w/api.php?action=query&prop=imageinfo&iiprop=timestamp|user|url&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json")
    fun getImages(): Call<ImageResponse>
}
