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
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.row_layout.*
import kotlinx.android.synthetic.main.row_layout.view.*
import kotlinx.android.synthetic.main.row_layout.view.textViewDesc

open class MainActivity : AppCompatActivity() {

    var listofsongs = ArrayList<SongInfo>()
    var adapter:MySongAdapter ?= null
    var mediaPlayer:MediaPlayer ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        LoadData("%")
//
//        var dbManager = DBManager(this)
//
//        var values = ContentValues()
//        values.put("Title", textViewTitle.text.toString())
//        values.put("Artist", textViewArtist.text.toString())
//
//        val ID = dbManager.Insert(values)



        LoadSongInfo()



        LoadSongFromLocal()
        adapter = MySongAdapter(listofsongs)

        listVewSong.adapter = adapter

        

    }

    fun LoadSongFromLocal()
    {
        val allsong = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
        val selection =MediaStore.Audio.Media.IS_MUSIC + "!=0"

        val cursor = contentResolver.query(allsong,null,selection,null,null)

        if (cursor != null)
        {
            if (cursor.moveToFirst() == true)
            {
                do {

                    val songURL = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))

                    listofsongs.add(SongInfo(songName,songAuthor,songURL))

                }while (cursor.moveToNext() == true)
            }
            cursor!!.close()



        }

    }

    fun LoadSongInfo()
    {
        listofsongs.add(SongInfo("Divooneh Jan","Babak Jahanbakhsh","https://dl.nex1music.ir/1396/04/15Babak Jahanbakhsh - Divooneh Jan.mp3?time=1590501993&filename=/1396/04/15Babak Jahanbakhsh - Divooneh Jan.mp3"))
        listofsongs.add(SongInfo("Fogholade","Saman Jalili","https://www.mybia2music.com/113949035/babak-jahanbakhsh-payane-taze"))

    }



    open inner class MySongAdapter : BaseAdapter
    {
        var myListSong = ArrayList <SongInfo>()
        constructor(myListSong:ArrayList<SongInfo>)
        {
            this.myListSong = myListSong
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.row_layout,null)
            val song = this.myListSong[position]


            myView.textViewTitle.setText(song.Title)
            myView.textViewDesc.setText(song.Desc)


            myView.coverMusic.setOnClickListener {
                val playIntent = Intent(this@MainActivity,PlayActivity::class.java)
                startActivity(playIntent)

            }

            myView.PlayMusic.setOnClickListener {

                if (textViewDesc.textColors.equals("#00d6b3"))
                {
                    mediaPlayer!!.stop()
                    myView.textViewDesc.setTextColor(Color.parseColor("#C3C3C3"))
                    myView.textViewTitle.setTextColor(Color.parseColor("#ffffff"))
                }
                else
                {
                    mediaPlayer = MediaPlayer()

                    mediaPlayer!!.setDataSource(song.SongURL)
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
//                    seekBar.max = mediaPlayer!!.duration


                    myView.textViewDesc.setTextColor(Color.parseColor("#00d6b3"))
                    myView.textViewTitle.setTextColor(Color.parseColor("#13f8d1"))


                }
            }
            return myView


        }

        override fun getItem(position: Int): Any {
            return this.myListSong[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return this.myListSong.size
        }




    }

///  code data base
    fun LoadData (title: String)
    {
        var dbManager = DBManager(this)

        val columns = arrayOf("ID", "Title", "Artist")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.RunQuery(columns,"Title like ?", selectionArgs,"Title")

        listofsongs.clear()
//        if (cursor.moveToFirst() == true)
//        {
//            do {
//                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
//                val Title = cursor.getString(cursor.getColumnIndex("Title"))
//                val Artist = cursor.getString(cursor.getColumnIndex("Artist"))
//                val SongURL = cursor.getString(cursor.getColumnIndex("SongURL"))
//
//                listofsongs.add(SongInfo(ID, Title, Artist))
//
//            }while (cursor.moveToNext())
//        }
        adapter = MySongAdapter(listofsongs)
        listVewSong.adapter = adapter

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

