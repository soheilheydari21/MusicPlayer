package com.example.musicplayer.Controllers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.musicplayer.*
import com.example.musicplayer.Less.PlayActivity
import com.example.musicplayer.Helper.*
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.Services.DataServis.Companion.playSong
import com.rishabhharit.roundedimageview.RoundedImageView
//import com.example.musicplayer.Servis.DataServis.pause
import kotlin.collections.ArrayList

class FragmentTracks : Fragment() {

    companion object{
        var TitleN:TextView? = null
        var ArtistN:TextView? = null
        var PlayN:ImageView? = null
        var NextN:ImageView? = null
        var PreviewN:ImageView? = null
        var musicAdapter:MySongAdapter? = null
        var Cover:ImageView? = null
    }

    @SuppressLint("WrongViewCast", "Recycle")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_one, container, false)

        TitleN = view.findViewById(R.id.textViewTitleN)
        ArtistN = view.findViewById(R.id.textViewArtistN)
        Cover = view.findViewById(R.id.coverNavar)
        PlayN = view.findViewById(R.id.imageViewPlayN)
        NextN = view.findViewById(R.id.imageViewNextN)
        PreviewN = view.findViewById(R.id.imageViewPreviewN)

        FragmentTracks.TitleN?.text = changTextTitle
        FragmentTracks.ArtistN?.text = changTextArtist
//        FragmentTracks.Cover?.setImageResource(changeCover)
        FragmentTracks.TitleN?.isSelected =true
        FragmentTracks.ArtistN?.isSelected =true

        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = activity!!.contentResolver.query(allsong,null,selection,null,sortOrder)
        var listofsongs = ArrayList<SongInfo>()


        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    @Suppress("DEPRECATION")
                    val songURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    val cover = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))

                    listofsongs.add(
                        SongInfo(
                            songName,
                            songAuthor,
                            songURL,
                            cover
                        )
                    )

                }while (cursor.moveToNext())
            }
            cursor.close()

            val songList = view.findViewById<ListView>(R.id.listVewSong)
            songList.adapter = MySongAdapter(
                activity!!.applicationContext,
                listofsongs
            )

            //navar Control music playbtn
            FragmentTracks.PlayN?.setOnClickListener {
                if (mediaPlayer!!.isPlaying)
                {
                    FragmentTracks.PlayN?.setImageResource(R.drawable.play_navar)
                    mediaPlayer!!.pause()
                }
                else if (mediaPlayer!=null)
                {
                    FragmentTracks.PlayN?.setImageResource(R.drawable.pause_icon_15)
                    mediaPlayer!!.start()
                }
            }

            //ToDo this fixes  previewbtn
            //navar Control music previewbtn
            FragmentTracks.PreviewN?.setOnClickListener {
                if (currentSongIndex>0) {
                    playSong(currentSongIndex - 1)
                    currentSongIndex -= 1

                }else
                {
                    playSong(MySongAdapter.myListSong.size -1)
                    currentSongIndex = MySongAdapter.myListSong.size -1
                }
            }

            //ToDo this fixes  nextbtn
            //navar Control music nextbtn
            FragmentTracks.NextN?.setOnClickListener {

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
            if (mediaPlayer != null) {
                val intent = Intent(context, PlayActivity::class.java)
                startActivity(intent)
            }
            else
                Toast.makeText(context,"click on a Song please",Toast.LENGTH_SHORT).show()
        }
    }



}
