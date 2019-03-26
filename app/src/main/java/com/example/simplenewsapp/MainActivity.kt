package com.example.simplenewsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.simplenewsapp.Model.ApiData
import com.example.simplenewsapp.Model.NewsDBApi
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("News App")

        connect()
        rc_main.layoutManager = LinearLayoutManager(this)
    }

    fun connect() {
        val request = NewsDBApi().getNews()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) { }
            override fun onResponse(call: Call, response: Response) {
                val gson = Gson()

                val data = gson.fromJson(response.body()?.string(), ApiData::class.java)

                runOnUiThread {
                    rc_main.adapter = MainAdapter(data)
                }
            }
        })
    }
}
