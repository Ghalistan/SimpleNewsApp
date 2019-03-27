package com.example.simplenewsapp.Model

import com.example.simplenewsapp.BuildConfig
import okhttp3.*

class NewsDBApi {

    fun getNews(country: String, category: String):Request {
        val request = Request.Builder()
            .url(BuildConfig.BASE_URL + "/v2/top-headlines?country=${country}&category=${category}&apiKey=${BuildConfig.API_KEY}")
            .build()
        return request
    }
}