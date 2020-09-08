package com.example.musicplayer.Activity

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.*
import com.example.musicplayer.Adapters.MyPagerAdapter
import com.example.musicplayer.Adapters.MySongAdapter
import com.example.musicplayer.DataBace.DBManager
import com.example.musicplayer.R
import com.example.musicplayer.OtherClass.SongInfo
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {

    var adapter: MySongAdapter?= null
    var listofsongs = ArrayList<SongInfo>()
    companion object{
        val PERMISSION_REQUEST_CODE = 12
    }



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


        val fragmentAdapter = MyPagerAdapter(
            supportFragmentManager
        )
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_favorite_24)

    }


    //ToDo: fix this    Data base
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


                listofsongs.add(
                    SongInfo(
                        ID.toString(),
                        Title,
                        Artist
                    )
                )

            }while (cursor.moveToNext())
        }

//        adapter = MySongAdapter(this ,listofsongs)
//        listVewSong.adapter = adapter



    }

    //ToDo: fix this    Search

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

