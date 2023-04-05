package com.trr1ckster.topnews.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trr1ckster.topnews.data.model.ArticleResponse
import com.trr1ckster.topnews.data.network.RetrofitClient
import com.trr1ckster.topnews.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private val _newsLiveData: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()
    val newsLiveData: LiveData<Resource<ArticleResponse>> = _newsLiveData

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        _newsLiveData.postValue(Resource.Loading())
        val response = RetrofitClient.newsApi.getNews()
        _newsLiveData.postValue(handleNews(response))
    }

    private fun handleNews(response: Response<ArticleResponse>): Resource<ArticleResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}