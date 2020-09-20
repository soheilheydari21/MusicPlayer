package com.example.musicplayer.Fragments

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.musicplayer.*
import com.example.musicplayer.Activity.PlayActivity
import com.example.musicplayer.Adapters.MySongAdapter
import com.example.musicplayer.Adapters.changTextArtist
import com.example.musicplayer.Adapters.changTextTitle
import com.example.musicplayer.Adapters.mediaPlayer
import com.example.musicplayer.OtherClass.SongInfo
import kotlinx.android.synthetic.main.fragment_one.*
//import com.example.musicplayer.Servis.DataServis.pause
import kotlin.collections.ArrayList


class FragmentOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_one, container, false)

        // ToDo: this fixes Set text Artist & Title
        view.findViewById<TextView>(R.id.textViewTitleN).setText(changTextTitle)
        view.findViewById<TextView>(R.id.textViewArtistN).setText(changTextArtist)

        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = activity!!.contentResolver.query(allsong,null,selection,null,sortOrder)
        var listofsongs = ArrayList<SongInfo>()

        if (cursor != null)
        {
            if (cursor.moveToFirst() == true)
            {
                do {
                    @Suppress("DEPRECATION") val songURL = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    val allbum = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ALBUM))

                    listofsongs.add(
                        SongInfo(
                            songName,
                            songAuthor,
                            songURL,
                            allbum
                        )
                    )

                }while (cursor.moveToNext() == true)
            }
            cursor!!.close()

            val songList = view.findViewById<ListView>(R.id.listVewSong)
            songList.adapter = MySongAdapter(
                activity!!.applicationContext,
                listofsongs
            )


            var flage = 0
            view.findViewById<ImageView>(R.id.imageViewPlayN).setOnClickListener {
                if (flage == 0)
                {
                    view.findViewById<ImageView>(R.id.imageViewPlayN).setImageResource(
                        R.drawable.pause_navar
                    );
                    flage = 1;
                    mediaPlayer = MediaPlayer()
                    mediaPlayer!!.start()
                }

                else if (flage == 1)
                {
                    view.findViewById<ImageView>(R.id.imageViewPlayN).setImageResource(
                        R.drawable.play_navar
                    );
                    flage = 0;
                    mediaPlayer!!.stop()
                }
            }

        }
        return view

    }

//    ****
//    fun loadData()
//    {
//        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//
//
//        val cursor = activity!!.contentResolver.query(allsong,null,null,null,null)
//        var listofsongs = ArrayList<SongInfo>()
//        if (cursor != null)
//        {
//            if (cursor.moveToFirst() == true)
//            {
//                do {
//                    val songURL = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DATA))
//                    val songAuthor = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ARTIST))
//                    val songName = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
//
//                    listofsongs.add(SongInfo(songName,songAuthor,songURL))
//
//                }while (cursor.moveToNext() == true)
//            }
//            cursor!!.close()
//
//            val songList = view.findViewById<ListView>(R.id.listVewSong)
//            songList.adapter = MySongAdapter(activity!!.applicationContext, listofsongs)
//            return
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ConstraintLayout>(R.id.navarLayout).setOnClickListener {

            var intent = Intent(context, PlayActivity::class.java)
            startActivity(intent)
        }
    }


}