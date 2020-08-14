package com.example.musicplayer

import android.app.SearchManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.activity_play.view.*
import kotlinx.android.synthetic.main.fragment_one.*
import kotlinx.android.synthetic.main.row_layout.*
import kotlinx.android.synthetic.main.row_layout.view.*
import kotlinx.android.synthetic.main.row_layout.view.textViewDesc

open class MainActivity : AppCompatActivity() {

    var adapter:MySongAdapter ?= null
    var listofsongs = ArrayList<SongInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        LoadData("%")
//
//        var dbManager = DBManager(this)
//        var values = ContentValues()
//        values.put("Title", findViewById<TextView>(R.id.textViewTitle).text.toString())
//        values.put("Artist", findViewById<TextView>(R.id.textViewArtist).text.toString())
//
//        val ID = dbManager.Insert(values)


        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

    }





    //ToDo fix this
//   code data base
    fun LoadData (title: String)
    {
        var dbManager = DBManager(this)

        val columns = arrayOf("ID", "Title", "Artist")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.RunQuery(columns,"Title like ?", selectionArgs,"Title")

        listofsongs.clear()
        if (cursor.moveToFirst() == true)
        {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title = cursor.getString(cursor.getColumnIndex("Title"))
                val Artist = cursor.getString(cursor.getColumnIndex("Artist"))
                val SongURL = cursor.getString(cursor.getColumnIndex("SongURL"))


                listofsongs.add(SongInfo(ID.toString(), Title, Artist))

            }while (cursor.moveToNext())
        }

//        adapter = MySongAdapter(this ,listofsongs)
//        listVewSong.adapter = adapter



    }

    //    Todo: fix this 
    //    source search
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val searchView = menu!!.findItem(R.id.appBarSearch).actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener
        {
            override fun onQueryTextChange(newText: String?): Boolean {
                LoadData("%" + newText + "%")
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)

    }


}

