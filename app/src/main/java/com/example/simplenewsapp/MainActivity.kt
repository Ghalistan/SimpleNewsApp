package com.example.simplenewsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.transition.TransitionInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
    private lateinit var adapter:MainAdapter
    private val data = ApiData()
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("News App")

        rc_main.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(data)

        swipeLayout.setOnRefreshListener {
            connect(spinner.selectedItem.toString(), spinner2.selectedItem.toString())
            adapter.notifyDataSetChanged()
            swipeLayout.isRefreshing = false
        }

        val spinner : Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(this, R.array.country_array, android.R.layout.simple_spinner_item). also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                connect(spinner.selectedItem.toString(), spinner2.selectedItem.toString())
                adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        val spinner2 : Spinner = findViewById(R.id.spinner2)
        ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item). also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                connect(spinner.selectedItem.toString(), spinner2.selectedItem.toString())
                adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        connect("us", "general")
        setupWindowAnimations()
    }

    private fun setupWindowAnimations() {
        val slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide)
        window.exitTransition = slide
    }

    fun connect(country:String, category:String) {
        val request = NewsDBApi().getNews(country, category)

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
