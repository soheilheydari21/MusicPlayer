package com.example.musicplayer.Fragments

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.Adapters.MyAllbumAdapter
import com.example.musicplayer.Adapters.MySongAdapter
import com.example.musicplayer.Adapters.adapter
import com.example.musicplayer.OtherClass.AllbumInfo
import com.example.musicplayer.OtherClass.SongInfo
import com.example.musicplayer.R
import kotlinx.android.synthetic.main.allbum_item.*
import kotlinx.android.synthetic.main.allbum_item.view.*

class FragmentThree : Fragment() {

    lateinit var adapter : MyAllbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_three, container, false)

//        adapter = MyAllbumAdapter(context, getAllbum(Title)) { product ->
//
//        }
//        val layoutManager = FrameLayoutManager(this, 2)
//        RecycleViewAllbum.LayoutManager = layoutManager
//        RecycleViewAllbum.setHazFixedSize(true)
//
//
//        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
//        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
//        val cursor = activity!!.contentResolver.query(allsong,null,selection,null,sortOrder)
//        var listofsongs = ArrayList<AllbumInfo>()
//
//        if (cursor != null) {
//            if (cursor.moveToFirst() == true) {
//                do {
//                    @Suppress("DEPRECATION") val songURL = cursor!!.getString(
//                        cursor!!.getColumnIndex(
//                            MediaStore.Audio.Media.DATA
//                        )
//                    )
//                    val songAuthor =
//                        cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ARTIST))
//                    val songName =
//                        cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
//                    val allbum =
//                        cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ALBUM))
//
//                    listofsongs.add(
//                        AllbumInfo(
//                            imageViewCoverAllbum,
//                            imageViewCoverAllbum2,
//                            imageViewCoverAllbum3,
//                            textViewTitleAllbum,
//                            textViewArtistAllbum
//                        )
//                    )
//
//                } while (cursor.moveToNext() == true)
//            }
//            cursor!!.close()
//
//            val songList = view.findViewById<RecyclerView>(R.id.ReciycleViewAllbum)
//            songList.adapter = MyAllbumAdapter(
//                activity!!.applicationContext,
//                listofsongs
//            )
//        }

        return view
      }

}