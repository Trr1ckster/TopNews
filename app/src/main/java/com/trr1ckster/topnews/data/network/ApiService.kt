package com.trr1ckster.topnews.data.network

import com.trr1ckster.topnews.data.model.ArticleResponse
import com.trr1ckster.topnews.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<ArticleResponse>
}