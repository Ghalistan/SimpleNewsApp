package com.example.simplenewsapp.Model

data class ApiData(
    val status:String = "",
    val totalResults:Int = -1,
    val articles:List<News> = emptyList()
)