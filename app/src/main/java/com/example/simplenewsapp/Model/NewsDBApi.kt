package com.example.simplenewsapp.Model

import com.example.simplenewsapp.BuildConfig
import okhttp3.*

class NewsDBApi {

    fun getNews():Request {
        val request = Request.Builder()
            .url(BuildConfig.BASE_URL + "/v2/top-headlines?country=us&apiKey=${BuildConfig.API_KEY}")
            .build()
        return request
    }
}