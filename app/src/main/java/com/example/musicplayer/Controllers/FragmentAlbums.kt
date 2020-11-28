package com.example.musicplayer.Controllers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.Less.AlbumActivity
import com.example.musicplayer.Helper.MyAlbumAdapter
import com.example.musicplayer.Models.AlbumInfo
import com.example.musicplayer.R
import kotlinx.android.synthetic.main.activity_play.*


class FragmentAlbums : Fragment() {

    lateinit var recyclerAlbum:RecyclerView
    lateinit var adapter : MyAlbumAdapter

    @SuppressLint("CutPasteId", "Recycle")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_album, container, false)

        recyclerAlbum = view.findViewById(R.id.ReciycleViewAlbum)

        recyclerAlbum.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity,2)
        }

        val allAlbum = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.ALBUM + "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = activity!!.contentResolver.query(allAlbum,null,selection,null,sortOrder)
        val listOfAlbum = ArrayList<AlbumInfo>()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @Suppress("DEPRECATION")
                    val albumURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val albumDesc = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val album2 = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val album3 = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))

                    listOfAlbum.add(
                        AlbumInfo(
                            album,
                            album2,
                            album3,
                            albumName,
                            albumDesc,
                            albumURL
                        )
                    )

                } while (cursor.moveToNext())
            }
            cursor.close()

            val albumList = view.findViewById<RecyclerView>(R.id.ReciycleViewAlbum)
            albumList.adapter = MyAlbumAdapter(
                activity!!.applicationContext,
                listOfAlbum
            ){
                val intent = Intent(context, AlbumActivity::class.java)
                startActivity(intent)

            }

        }
         return view

    }


}