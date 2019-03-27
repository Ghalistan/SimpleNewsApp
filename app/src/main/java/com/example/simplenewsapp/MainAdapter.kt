package com.example.simplenewsapp

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simplenewsapp.Model.ApiData
import com.example.simplenewsapp.Model.News
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_row.view.*

class MainAdapter(data:ApiData) : RecyclerView.Adapter<MainViewHolder>() {

    private val newsData = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val callrow = layoutInflater.inflate(R.layout.news_row, parent, false)
        return MainViewHolder(callrow)
    }

    override fun getItemCount(): Int {
        return newsData.articles.count()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        // Thumbnail
        val NewsImage = holder.view.newsImage
        val image = Validity.checkImage(newsData.articles.get(position).urlToImage)
        Picasso.get().load(image).into(NewsImage)

        //Title
        holder.view.title_row.text = newsData.articles.get(position).title

        //Author
        holder.view.authorNews.text = Validity.checkAuthor(newsData.articles.get(position).author)

        //Date
        holder.view.newsDate.text = Validity.setDate(newsData.articles.get(position).publishedAt)

        //Description
        holder.view.newsDesc.text = Validity.checkDesc(newsData.articles.get(position).description)

        //Pass data to Activity
        holder.detail = newsData.articles.get(position)
    }
}

class MainViewHolder(val view: View, var detail:News? = null) : RecyclerView.ViewHolder(view) {
    companion object {
        val AUTHOR_KEY = "AUTHOR"
        val TITLE_KEY = "TITLE"
        val IMG_KEY = "IMAGE"
        val DATE_KEY = "DATE"
        val CONTENT_KEY = "CONTENT"
        val WEB_KEY = "WEB_LINK"
    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, Detail_News::class.java)
            intent.putExtra(AUTHOR_KEY,detail?.author)
            intent.putExtra(TITLE_KEY,detail?.title)
            intent.putExtra(IMG_KEY,detail?.urlToImage)
            intent.putExtra(DATE_KEY,detail?.publishedAt)
            intent.putExtra(CONTENT_KEY,detail?.content)
            intent.putExtra(WEB_KEY,detail?.url)
            view.context.startActivity(intent)
        }
    }
}

object Validity {
    fun setDate(news: String):String {
        var dateRange = news.indexOf('T',0, false)
        return news.substring(0, dateRange)
    }

    fun checkAuthor(auth:String?):String {
        if (auth == null || auth == "") {
            return "Unknown Author, "
        } else {
            return auth + ", "
        }
    }

    fun checkContent(string:String?):String {
        if(string == null) {
            return "No Data for this news."
        } else if(string.length < 260) {
            return string
        } else {
            return string.substring(0, 260)
        }
    }

    fun checkDesc(string:String?):String {
        if (string==null) {
            return "No Description for this news."
        } else {
            return string
        }
    }

    fun checkImage(string: String?):String {
        if (string==null) {
            return "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png"
        } else {
            return string
        }
    }
}