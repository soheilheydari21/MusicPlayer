package com.example.musicplayer.Helper


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.Controllers.FragmentTracks
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R


var adapter: MyTrackAdapter? = null
public var mediaPlayer: MediaPlayer? = null
var songe:SongInfo? = null
var currentSongIndex = 0

var changTextTitle = "Title"
var changTextArtist = "Artist"
var changeCover:ImageView? = null

class MyTrackAdapter(
    val context: Context,
    myListSong: ArrayList<SongInfo>,
    private val itemClicked: (SongInfo) -> Unit
) : RecyclerView.Adapter<MyTrackAdapter.SongHolder>() {

    companion object {
        var myListSong = ArrayList<SongInfo>()
        var TextTitle: TextView? = null
        var TextArtist: TextView? = null
        var ItemMusic: LinearLayout? = null
        var setCover: ImageView? = null
        var view:View? = null
    }

    private val mContext: Context
    init {
        MyTrackAdapter.myListSong = myListSong
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        view = LayoutInflater.from(context).inflate(R.layout.track_item, parent, false)

        var setCover = view?.findViewById<ImageView>(R.id.coverMusic)
        var songTitle = view?.findViewById<TextView>(R.id.textViewTitle)
        var songArtist = view?.findViewById<TextView>(R.id.textViewDesc)
        var itemMusic = view?.findViewById<LinearLayout>(R.id.PlayMusic)

        return SongHolder(view, itemClicked)
    }

    override fun getItemCount(): Int {
        return myListSong.size
    }

    override fun onBindViewHolder(holder: MyTrackAdapter.SongHolder, position: Int) {
        return holder.bindMusic(myListSong[position], context, position)
    }

    inner class SongHolder(itemView: View?, val itemClicked: (SongInfo) -> Unit) : RecyclerView.ViewHolder(itemView!!)
    {
        private var setCover = itemView?.findViewById<ImageView>(R.id.coverMusic)
        private var songTitle = itemView?.findViewById<TextView>(R.id.textViewTitle)
        private var songArtist = itemView?.findViewById<TextView>(R.id.textViewDesc)
        private var itemMusic = itemView?.findViewById<LinearLayout>(R.id.PlayMusic)

        @SuppressLint("ResourceType")
        fun bindMusic(songinfo:SongInfo, context:Context, position: Int)
        {
            val resourceId = context.resources.getIdentifier(songinfo.Cover, "drawable", context.packageName)
            val layoutInflate = LayoutInflater.from(mContext)
            val Songy = MyTrackAdapter.myListSong[position]

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
                };
            }

            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer()

            itemView.setOnClickListener { itemClicked(songinfo)
                var flags = true
                if (!flags)
                {
                    mediaPlayer!!.stop()
                    itemView.findViewById<TextView>(R.id.textViewDesc).setTextColor(Color.parseColor("#c3c3c3"))
                    itemView.findViewById<TextView>(R.id.textViewTitle).setTextColor(Color.parseColor("#ffffff"))
                    FragmentTracks.PlayN?.setImageResource(R.drawable.play_navar)
                    flags = true

                }
                else if (flags)
                {
                    mediaPlayer!!.reset()
                    mediaPlayer!!.setDataSource(Songy.SongURL)
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
                    itemView.findViewById<TextView>(R.id.textViewDesc).setTextColor(Color.parseColor("#00d6b3"))
                    itemView.findViewById<TextView>(R.id.textViewTitle).setTextColor(Color.parseColor("#13f8d1"))
                    changTextTitle = itemView.findViewById<TextView>(R.id.textViewTitle).text.toString()
                    changTextArtist = itemView.findViewById<TextView>(R.id.textViewDesc).text.toString()
                    changeCover = setCover

                    FragmentTracks.TitleN?.text = changTextTitle
                    FragmentTracks.ArtistN?.text = changTextArtist
                    FragmentTracks.PlayN?.setImageResource(R.drawable.pause_icon_15)
                    flags = false
                }
                 songe = Songy

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

    //SEARCH
    fun updateList(musicFilesArrayList: ArrayList<SongInfo>)
    {
        myListSong =  ArrayList()
        myListSong.addAll(musicFilesArrayList)
        this.notifyDataSetChanged()
    }

}