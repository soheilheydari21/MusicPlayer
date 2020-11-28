package com.example.musicplayer.Less

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.Controllers.FragmentTracks
import com.example.musicplayer.Helper.MySearchAdapter
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R
import kotlinx.android.synthetic.main.activity_search.*


@Suppress("SENSELESS_COMPARISON")
class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerSearch: RecyclerView
    private lateinit var adapterSearch : MySearchAdapter
    private lateinit var mLayoutManager:RecyclerView.LayoutManager
    private var listOfSearches = ArrayList<SongInfo>()

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        backSearch.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            animationBack()
        }

        val editTextSearch = findViewById<EditText>(R.id.textBoxSearch)
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())

                if (s.count() >= 1) {
                    layoutSongs.visibility = VISIBLE
                    layoutSearch.visibility = GONE
                } else if (s.count() < 1) {
                    layoutSongs.visibility = GONE
                    layoutSearch.visibility = VISIBLE
                }

            }
        })

//        layoutSearch.visibility = GONE
//        layoutSongs.visibility = VISIBLE

        buildRecyclerView()

//        recyclerSearch = findViewById(R.id.recyclerSearch)
//        recyclerSearch.adapter = adapterSearch
//        adapterSearch = MySearchAdapter(this, listOfSearches)
//
//        recyclerSearch.apply {
//            setHasFixedSize(true)
//            layoutManager = GridLayoutManager(this@SearchActivity, 1)
//        }

        FragmentTracks.Cover = findViewById(R.id.coverNavar)

        val allSearch = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = this.contentResolver.query(allSearch, null, null, null, sortOrder)

        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    @Suppress("DEPRECATION")
                    val songURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songDesc = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val cover = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK))

                    listOfSearches.add(
                        SongInfo(
                            songName,
                            songDesc,
                            songURL,
                            cover
                        )
                    )

                }while (cursor.moveToNext())
            }
            cursor.close()

            val searchList = findViewById<RecyclerView>(R.id.recyclerSearch)
            searchList.adapter = MySearchAdapter(
                this.applicationContext,
                listOfSearches
            )
        }

    }


    //ToDo this fixes   Search 1
    //SEARCH
    @SuppressLint("DefaultLocale")
    private fun filter(text: String) {
        val filteredList: ArrayList<SongInfo> = ArrayList()
        for (item in listOfSearches) {
            if (item.gettitle()!!.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapterSearch.filterList(filteredList)
    }

    private fun animationBack()
    {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        backSearch.startAnimation(animScale)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun buildRecyclerView() {
        recyclerSearch = findViewById(R.id.recyclerSearch)
        recyclerSearch.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        adapterSearch = MySearchAdapter(this,listOfSearches)
        recyclerSearch.layoutManager = mLayoutManager
        recyclerSearch.adapter = adapterSearch
    }


}
