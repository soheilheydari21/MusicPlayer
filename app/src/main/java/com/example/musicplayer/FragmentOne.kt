package com.example.musicplayer

import android.content.ContentResolver
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import kotlinx.android.synthetic.main.fragment_one.*
import java.util.*
import kotlin.collections.ArrayList


class FragmentOne : Fragment() {

//    var listofsongs = ArrayList<SongInfo>()
//    var adapter:MySongAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_one, container, false)



//        LoadSongsFromLocal()**
//        view.findViewById<ListView>(R.id.listVewSong).adapter = MySongAdapter(activity!!.applicationContext,listVewSong)
//        val songList = view.findViewById<ListView>(R.id.listVewSong)
//        songList.adapter = MySongAdapter(activity!!.applicationContext,listVewSong)

        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//        val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"


        val cursor = activity!!.contentResolver.query(allsong,null,null,null,null)
        var listofsongs = ArrayList<SongInfo>()
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

            val songList = view.findViewById<ListView>(R.id.listVewSong)
            songList.adapter = MySongAdapter(activity!!.applicationContext, listofsongs)
//            adapter = MySongAdapter(context!!.applicationContext,listofsongs)
//            listVewSong.adapter = adapter

            //....
            var flage = 0
            view.findViewById<ImageView>(R.id.imageViewPlayN).setOnClickListener {

                if (flage == 0)
                {
                    view.findViewById<ImageView>(R.id.imageViewPlayN).setImageResource(R.drawable.pause_navar);
                    flage = 1;
                    mediaPlayer = MediaPlayer()

                    mediaPlayer!!.start()

                }
                else if (flage == 1)
                {
                    view.findViewById<ImageView>(R.id.imageViewPlayN).setImageResource(R.drawable.play_navar);
                    flage = 0;
                    mediaPlayer!!.pause()
                }
            }
            //....

        }
        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<ImageView>(R.id.coverMusic).setOnClickListener {
//
//            val playIntent = Intent(this.context, PlayActivity::class.java)
//            startActivity(playIntent)
//        }
//
//    }

}