package com.example.musicplayer.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import com.example.musicplayer.Adapters.*
import com.example.musicplayer.OtherClass.SongInfo
import com.example.musicplayer.Servis.DataServis.Companion.playSong
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_one.*
import kotlinx.android.synthetic.main.row_layout.*
//import com.example.musicplayer.Servis.DataServis.pause
import kotlin.collections.ArrayList


class FragmentOne : Fragment() {

    @SuppressLint("WrongViewCast")
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
                    val cover = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))

                    listofsongs.add(
                        SongInfo(
                            songName,
                            songAuthor,
                            songURL,
                            cover
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

            //ToDo this fixes   play navarbtn
            //navar Control music
            view.findViewById<ImageView>(R.id.imageViewPlayN).setOnClickListener {
                if (mediaPlayer!!.isPlaying)
                {
                    view.findViewById<ImageView>(R.id.imageViewPlayN).setImageResource(
                        R.drawable.play_navar
                    );
                    mediaPlayer!!.pause()

                }
                else if (mediaPlayer!=null)
                {
                    view.findViewById<ImageView>(R.id.imageViewPlayN).setImageResource(
                        R.drawable.pause_icon_15
                    );
                    mediaPlayer!!.start()

                }
            }

            //ToDo this fixes  previewbtn
//            view.findViewById<ImageView>(R.id.prevButtonN).setOnClickListener {
//                if (currentSongIndex>0) {
//                    playSong(currentSongIndex - 1);
//                    currentSongIndex = currentSongIndex - 1;
//
//                }else
//                {
//                    playSong(myListSong.size()-1);
//                    currentSongIndex = myListSong.size()-1;
//
//                }
//            }

            //ToDo this fixes  nextbtn
//            view.findViewById<ImageView>(R.id.nextButtonN).setOnClickListener {
//                if (currentSongIndex<(myListSong.size()-1)) {
//                    playSong(currentSongIndex + 1);
//                    currentSongIndex = currentSongIndex + 1;
//
//                }
//                else
//                {
//                    playSong(0);
//                    currentSongIndex=0;
//                }
//            }

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
            if (mediaPlayer!=null) {
                var intent = Intent(context, PlayActivity::class.java)
                startActivity(intent)

            }
            else
                Toast.makeText(context,"click on a Song please",Toast.LENGTH_SHORT).show()
        }
    }



}
