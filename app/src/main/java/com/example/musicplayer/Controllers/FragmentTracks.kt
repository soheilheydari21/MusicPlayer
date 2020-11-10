package com.example.musicplayer.Controllers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.*
import com.example.musicplayer.Less.PlayActivity
import com.example.musicplayer.Helper.*
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.Services.DataService.Companion.playSong
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.fragment_track.*
//import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence
//import com.example.musicplayer.Servis.DataServis.pause
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class FragmentTracks : Fragment() {

    lateinit var RecycleTrack:RecyclerView
    lateinit var adapter : MyTrackAdapter

    companion object{
        var TitleN:TextView? = null
        var ArtistN:TextView? = null
        var PlayN:ImageView? = null
        var NextN:ImageView? = null
        var PreviewN:ImageView? = null
        var musicAdapter:MyTrackAdapter? = null
        var Cover:ImageView? = null
        var coverList:ImageView? = null
    }

    @SuppressLint("WrongViewCast", "Recycle", "CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_track, container, false)
        RecycleTrack = view.findViewById(R.id.RecyclerViewTrack)

        RecycleTrack.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity,1)
        }

        TitleN = view.findViewById(R.id.textViewTitleN)
        ArtistN = view.findViewById(R.id.textViewArtistN)
        Cover = view.findViewById(R.id.coverNavar)
        PlayN = view.findViewById(R.id.imageViewPlayN)
        NextN = view.findViewById(R.id.imageViewNextN)
        PreviewN = view.findViewById(R.id.imageViewPreviewN)
        coverList = view.findViewById(R.id.coverMusic)

        FragmentTracks.TitleN?.text = changTextTitle
        FragmentTracks.ArtistN?.text = changTextArtist
        FragmentTracks.TitleN?.isSelected =true
        FragmentTracks.ArtistN?.isSelected =true


        val allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//        val selection = MediaStore.Audio.Media.IS_MUSIC+ "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = activity!!.contentResolver.query(allsong,null,null,null,sortOrder)
        val listofsongs = ArrayList<SongInfo>()

        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    @Suppress("DEPRECATION")
                    val songURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    val cover = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK))

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

            val songList = view.findViewById<RecyclerView>(R.id.RecyclerViewTrack)
            songList.adapter = MyTrackAdapter(
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

            //ToDo this fixes   Preview btn
            //navar Control music preview btn
            FragmentTracks.PreviewN?.setOnClickListener {
                if (currentSongIndex>0) {
                    playSong(currentSongIndex - 1)
                    currentSongIndex -= 1

                }else
                {
                    playSong(MyTrackAdapter.myListSong.size -1)
                    currentSongIndex = MyTrackAdapter.myListSong.size -1
                }
            }

            //ToDo this fixes   Next btn
            //navar Control music nextbtn
            FragmentTracks.NextN?.setOnClickListener {

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
