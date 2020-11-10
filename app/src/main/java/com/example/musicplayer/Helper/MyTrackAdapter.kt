package com.example.musicplayer.Helper


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.Controllers.FragmentTracks
import com.example.musicplayer.Less.PlayActivity
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R


var adapter: MyTrackAdapter? = null
var mediaPlayer: MediaPlayer? = null
var songe:SongInfo? = null
var currentSongIndex = 0

var changTextTitle = "Title"
var changTextArtist = "Artist"
var changeCover:ByteArray? = null

class MyTrackAdapter(
    context: Context,
    myListSong: ArrayList<SongInfo>
) : RecyclerView.Adapter<MyTrackAdapter.SongHolder>() {

    companion object {
        var myListSong = ArrayList<SongInfo>()
        var TextTitle: TextView? = null
        var TextArtist: TextView? = null
        var ItemMusic: LinearLayout? = null
        var setCover: ImageView? = null
    }

    private val mContext: Context
    init {
        MyTrackAdapter.myListSong = myListSong
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.track_item, parent, false)

        return SongHolder(view)
    }

    override fun getItemCount(): Int {
        return myListSong.size
    }

    override fun onBindViewHolder(holder: MyTrackAdapter.SongHolder, position: Int) {
        return holder.bindMusic(myListSong[position], position)
    }

    inner class SongHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!)
    {
        private val setCover = itemView?.findViewById<ImageView>(R.id.coverMusic)
        private val songTitle = itemView?.findViewById<TextView>(R.id.textViewTitle)
        private val songArtist = itemView?.findViewById<TextView>(R.id.textViewDesc)

        @SuppressLint("ResourceType")
        fun bindMusic(songinfo:SongInfo, position: Int)
        {

            songTitle?.text = songinfo.Title
            songArtist?.text = songinfo.Desc

            //cover
            val image = myListSong[position].getpach()?.let { getSongArt(it) }
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
                mediaPlayer!!.setDataSource(songinfo.SongURL)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()

                itemView.findViewById<TextView>(R.id.textViewDesc).setTextColor(Color.parseColor("#00d6b3"))
                itemView.findViewById<TextView>(R.id.textViewTitle).setTextColor(Color.parseColor("#13f8d1"))
                changTextTitle = itemView.findViewById<TextView>(R.id.textViewTitle).text.toString()
                changTextArtist = itemView.findViewById<TextView>(R.id.textViewDesc).text.toString()
                changeCover = image

                FragmentTracks.TitleN?.text = changTextTitle
                FragmentTracks.ArtistN?.text = changTextArtist
                FragmentTracks.PlayN?.setImageResource(R.drawable.pause_icon_15)

                if (image != null) {
                    FragmentTracks.Cover?.let { it1 ->
                        Glide.with(mContext).load(image).into(
                            it1
                        )
                    }
                }else
                {
                    FragmentTracks.Cover?.let { it1 ->
                        Glide.with(mContext).load(R.drawable.coverrrl)
                            .into(it1)
                    }
                }

                songe = songinfo
            }
        }

    }

    //cover
    private fun getSongArt(uri: String): ByteArray? {
        val retrever = MediaMetadataRetriever()
        retrever.setDataSource(uri)
        val art: ByteArray? = retrever.embeddedPicture
        retrever.release()
        return art
    }

    //ToDo this fixes   Search tab Track
    //SEARCH
    fun updateList(musicFilesArrayList: ArrayList<SongInfo>)
    {
        myListSong = ArrayList()
        myListSong.addAll(musicFilesArrayList)
        this.notifyDataSetChanged()
    }


}