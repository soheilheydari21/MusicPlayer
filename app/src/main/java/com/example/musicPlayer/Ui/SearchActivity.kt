package com.example.musicPlayer.Ui

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicPlayer.Helper.MySearchAdapter
import com.example.musicPlayer.Models.SongInfo
import com.example.musicPlayer.R
import kotlinx.android.synthetic.main.activity_search.*


@Suppress("SENSELESS_COMPARISON")
class SearchActivity : AppCompatActivity() {

    private var listOfSearches = ArrayList<SongInfo>()
    private lateinit var recyclerSearch: RecyclerView
    private var adapterSearch = MySearchAdapter(this, listOfSearches)
    private lateinit var mLayoutManager:RecyclerView.LayoutManager

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        backSearch.setOnClickListener {
            onBackPressed()
            animationBack()
        }

        val editTextSearch = findViewById<EditText>(R.id.textBoxSearch)
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
                adapterSearch.notifyDataSetChanged()

                if (s.count() >= 1) {
                    layoutSongs.visibility = VISIBLE
                    layoutSearch.visibility = GONE
                }
                else if (s.count() < 1) {
                    layoutSongs.visibility = GONE
                    layoutSearch.visibility = VISIBLE
                }

            }
        })

        buildRecyclerView()

        recyclerSearch = findViewById(R.id.recyclerSearch)
        recyclerSearch.adapter = adapterSearch
        MainActivity.Cover = findViewById(R.id.coverNavar)

        val allSearch = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = this.contentResolver.query(allSearch, null, null, null, sortOrder)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @Suppress("DEPRECATION")
                    val songURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songDesc = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val cover = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK))

                    try {
                        listOfSearches.add(
                            SongInfo(
                                songName,
                                songDesc,
                                songURL,
                                cover
                            )
                        )
                    }
                    catch (e:Exception){ }

                } while (cursor.moveToNext())
            }
            cursor.close()

            recyclerSearch.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@SearchActivity, 1)
            }
        }
    }


    //SEARCH
    @SuppressLint("DefaultLocale")
    private fun filter(text: String) {
        val filteredList: ArrayList<SongInfo> = ArrayList()
        for (item in listOfSearches) {
            if (item.gettitle()!!.toLowerCase().contains(text.toLowerCase()) || item.getdesc()!!.toLowerCase().contains(text.toLowerCase()))
                filteredList.add(item)
        }
        adapterSearch.filterList(filteredList)
    }

    private fun animationBack() {
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
