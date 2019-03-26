package com.example.simplenewsapp.Model

data class News (
    val source: Detail,
    val author:String?,
    val title:String,
    val description:String?,
    val url:String,
    val urlToImage:String,
    val publishedAt:String,
    val content:String
)