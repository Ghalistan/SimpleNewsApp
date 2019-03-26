package com.example.simplenewsapp

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail__news.*

class Detail_News : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__news)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Picasso.get().load(intent.getStringExtra(MainViewHolder.IMG_KEY)).into(newsPict)
        newsTitle.text = intent.getStringExtra(MainViewHolder.TITLE_KEY)
        newsAuthor.text = Validity.checkAuthor(intent.getStringExtra(MainViewHolder.AUTHOR_KEY))
        newsDate.text = Validity.setDate(intent.getStringExtra(MainViewHolder.DATE_KEY))
        newsContent.text = Validity.checkContent(intent.getStringExtra(MainViewHolder.CONTENT_KEY))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val url = intent.getStringExtra(MainViewHolder.WEB_KEY)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        this.startActivity(intent)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}