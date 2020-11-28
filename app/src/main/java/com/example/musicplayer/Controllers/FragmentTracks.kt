package com.example.musicplayer.Controllers

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.*
import com.example.musicplayer.Less.PlayActivity
import com.example.musicplayer.Helper.*
import com.example.musicplayer.Less.listofsongs
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.Services.DataService.Companion.playSong
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.track_item.*

import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class FragmentTracks : Fragment() {

    private lateinit var recycleTrack:RecyclerView
    private lateinit var recycleRecent:RecyclerView

    companion object{
        var TitleN:TextView? = null
        var ArtistN:TextView? = null
        var PlayN:ImageView? = null
        var NextN:ImageView? = null
        var PreviewN:ImageView? = null
        var Cover:ImageView? = null

        var musicAdapter:MyTrackAdapter? = null
        var coverList:ImageView? = null
    }

    @SuppressLint("WrongViewCast", "Recycle", "CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_track, container, false)
        recycleTrack = view.findViewById(R.id.RecyclerViewTrack)
        recycleRecent = view.findViewById(R.id.RecyclerViewRecent)

        recycleTrack.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity,1)
        }

        recycleRecent.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity,1,LinearLayoutManager.HORIZONTAL,false)
        }

        TitleN = view.findViewById(R.id.textViewTitleN)
        ArtistN = view.findViewById(R.id.textViewArtistN)
        Cover = view.findViewById(R.id.coverNavar)
        PlayN = view.findViewById(R.id.imageViewPlayN)
        NextN = view.findViewById(R.id.imageViewNextN)
        PreviewN = view.findViewById(R.id.imageViewPreviewN)
        coverList = view.findViewById(R.id.coverMusic)

        TitleN?.text = changTextTitle
        ArtistN?.text = changTextArtist
        TitleN?.isSelected =true
        ArtistN?.isSelected =true

        if (changeCover != null) {
            Cover?.let { it1 ->
                Glide.with(this).load(changeCover).into(it1)
            }
        }else
        {
            Cover?.let { it1 ->
                Glide.with(this).load(R.drawable.coverrrl)
                    .into(it1)
            }
        }

        //list music track
        val allSong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = activity!!.contentResolver.query(allSong,null,null,null,sortOrder)
        val listOfSongs = ArrayList<SongInfo>()

        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    @Suppress("DEPRECATION")
                    val songURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val cover = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK))

                    listOfSongs.add(
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

            val songList = view.findViewById<RecyclerView>(R.id.RecyclerViewTrack)
            songList.adapter = MyTrackAdapter(
                activity!!.applicationContext,
                listOfSongs
            )

            //strip Control music play button
            PlayN?.setOnClickListener {
                if (mediaPlayer!!.isPlaying)
                {
                    PlayN?.setImageResource(R.drawable.play_navar1)
                    mediaPlayer!!.pause()

                }
                else if (mediaPlayer!=null)
                {
                    PlayN?.setImageResource(R.drawable.pause_navar1)
                    mediaPlayer!!.start()
                }
            }

            //ToDo this fixes   Preview btn
            //strip Control music preview btn
            PreviewN?.setOnClickListener {
                if (currentSongIndex>0) {
                    playSong(currentSongIndex - 1)
                    currentSongIndex -= 1

                }else
                {
                    playSong(MyTrackAdapter.myListTrack.size -1)
                    currentSongIndex = MyTrackAdapter.myListTrack.size -1
                }
            }

            //ToDo this fixes   Next btn
            //strip Control music next button
            NextN?.setOnClickListener {

            }

        }

        //list music recent
        val recentSong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val recentOrder = MediaStore.Audio.Media.DATE_ADDED
        val recentCursor = activity!!.contentResolver.query(recentSong,null,null,null,recentOrder)
        val listOfRecently = ArrayList<SongInfo>()

        if (recentCursor != null)
        {
            if (recentCursor.moveToFirst())
            {
                do {
                    @Suppress("DEPRECATION")
                    val songURL = recentCursor.getString(recentCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor = recentCursor.getString(recentCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = recentCursor.getString(recentCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val cover = recentCursor.getString(recentCursor.getColumnIndex(MediaStore.Audio.Media.TRACK))

                    listOfRecently.add(
                        SongInfo(
                            songName,
                            songAuthor,
                            songURL,
                            cover
                        )
                    )

                }while (recentCursor.moveToNext())
            }
            recentCursor.close()


            val recentlyList = view.findViewById<RecyclerView>(R.id.RecyclerViewRecent)
            recentlyList.adapter = MyRecentlyAdapter(
                activity!!.applicationContext,
                listOfRecently
            )


            //ToDo this fixes   Preview btn
            //strip Control music preview btn
            PreviewN?.setOnClickListener {
                if (currentSongIndex>0) {
                    playSong(currentSongIndex - 1)
                    currentSongIndex -= 1

                }else
                {
                    playSong(MyTrackAdapter.myListTrack.size -1)
                    currentSongIndex = MyTrackAdapter.myListTrack.size -1
                }
            }

            //ToDo this fixes   Next btn
            //strip Control music next btn
            NextN?.setOnClickListener {

            }

        }
        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ConstraintLayout>(R.id.navarLayout).setOnClickListener {
            if (mediaPlayer != null) {
                val intent = Intent(context, PlayActivity::class.java)
                intent.putExtra("position", PlayActivity.position)
                startActivity(intent)

            }
            else
                Toast.makeText(context,"click on a Song please",Toast.LENGTH_SHORT).show()
        }
    }


}
