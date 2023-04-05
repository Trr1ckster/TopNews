package com.trr1ckster.topnews.data.model

data class ArticleResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)