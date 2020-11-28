package com.example.musicplayer.Helper


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.Controllers.FragmentTracks
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R
import kotlinx.android.synthetic.main.track_item.view.*


var adapter: MyTrackAdapter? = null
var mediaPlayer: MediaPlayer? = null
var songe:SongInfo? = null
var currentSongIndex = 0

var changTextTitle = "Title"
var changTextArtist = "Artist"
var changeCover:ByteArray? = null

@Suppress("DEPRECATION")
class MyTrackAdapter(
    context: Context,
    myListSong: ArrayList<SongInfo>
) : RecyclerView.Adapter<MyTrackAdapter.SongHolder>() {

    companion object {
        var myListTrack = ArrayList<SongInfo>()
    }

    private val mContext: Context
    init {
        myListTrack = myListSong
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.track_item, parent, false)

        return SongHolder(view)
    }

    override fun getItemCount(): Int {
        return myListTrack.size
    }

    override fun onBindViewHolder(holder: MyTrackAdapter.SongHolder, position: Int) {

        return holder.bindMusic(myListTrack[position])
    }

    inner class SongHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!)
    {
        private val layoutCover = itemView?.findViewById<ConstraintLayout>(R.id.layoutCover)
        private val setCover = itemView?.findViewById<ImageView>(R.id.coverMusic)
        private val songTitle = itemView?.findViewById<TextView>(R.id.textViewTitle)
        private val songArtist = itemView?.findViewById<TextView>(R.id.textViewDesc)
        private val itemMusic = itemView?.findViewById<ConstraintLayout>(R.id.PlayMusic)

        @SuppressLint("ResourceType")
        fun bindMusic(songInfo:SongInfo)
        {
            songTitle?.text = songInfo.Title
            songArtist?.text = songInfo.Desc

            //cover
            val image = myListTrack[position].getpach()?.let { getSongArt(it) }
            if(image != null)
            {
                setCover?.let {
                    Glide.with(mContext).asBitmap()
                        .load(image)
                        .into(it)
                }
            }
            else{
                setCover?.let {
                    Glide.with(mContext)
                        .load(R.drawable.coverrrl)
                        .into(it)
                }
            }

            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer()


            //ToDo this fixes   Item color click
            itemView.setOnClickListener {

                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(songInfo.SongURL)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
                animationItem()
                animationCover()

                itemView.textViewDesc.setTextColor(Color.parseColor("#00d6b3"))
                itemView.textViewTitle.setTextColor(Color.parseColor("#13f8d1"))
                changTextTitle = itemView.textViewTitle.text.toString()
                changTextArtist = itemView.textViewDesc.text.toString()
                changeCover = image

                FragmentTracks.TitleN?.text = changTextTitle
                FragmentTracks.ArtistN?.text = changTextArtist
                FragmentTracks.PlayN?.setImageResource(R.drawable.pause_navar1)

                if (image != null) {
                    FragmentTracks.Cover?.let { it1 ->
                        Glide.with(mContext).load(image).into(it1)
                    }
                }else
                {
                    FragmentTracks.Cover?.let { it1 ->
                        Glide.with(mContext).load(R.drawable.coverrrl)
                            .into(it1)
                    }
                }

                songe = songInfo
            }

        }


        private fun animationItem()
        {
            val animScale = AnimationUtils.loadAnimation(mContext, R.anim.anim_pause)
            itemMusic?.startAnimation(animScale)
        }

        private fun animationCover()
        {
            val animScale = AnimationUtils.loadAnimation(mContext, R.anim.anim_pause)
            layoutCover?.startAnimation(animScale)
        }

    }

    //cover
    private fun getSongArt(uri: String): ByteArray? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(uri)
        val art: ByteArray? = retriever.embeddedPicture
        retriever.release()
        return art
    }


}