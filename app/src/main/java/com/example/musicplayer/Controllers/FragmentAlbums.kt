package com.example.musicplayer.Controllers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.Less.AlbumActivity
import com.example.musicplayer.Helper.MyAllbumAdapter
import com.example.musicplayer.Models.AlbumInfo
import com.example.musicplayer.R


class FragmentAlbums : Fragment() {

    lateinit var RecycleAlbum:RecyclerView
    lateinit var adapter : MyAllbumAdapter

    @SuppressLint("CutPasteId", "Recycle")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_two, container, false)

        RecycleAlbum = view.findViewById(R.id.ReciycleViewAllbum)

        RecycleAlbum.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity,2)
        }

        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.ALBUM + "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = activity!!.contentResolver.query(allsong,null,selection,null,sortOrder)
        var listofsongs = ArrayList<AlbumInfo>()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @Suppress("DEPRECATION")
                    val songURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val album2 = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val album3 = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))

                    listofsongs.add(
                        AlbumInfo(
                            album,
                            album2,
                            album3,
                            songName,
                            songAuthor,
                            songURL
                        )
                    )

                } while (cursor.moveToNext())
            }
            cursor.close()

            val songList = view.findViewById<RecyclerView>(R.id.ReciycleViewAllbum)
            songList.adapter = MyAllbumAdapter(
                activity!!.applicationContext,
                listofsongs
            ){
                val intent = Intent(context, AlbumActivity::class.java)
                startActivity(intent)
            }
        }

         return view
    }


}