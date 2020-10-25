package com.example.musicplayer.Helper

import android.content.Context
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.musicplayer.Controllers.FragmentTracks
import com.example.musicplayer.Less.MainActivity
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R
import com.rishabhharit.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.fragment_one.*
import kotlinx.android.synthetic.main.fragment_one.view.*

var adapter: MySongAdapter?= null
//var listofsongs = ArrayList<SongInfo>()
var mediaPlayer: MediaPlayer? = null

var changTextTitle = "Title"
var changTextArtist = "Artist"
var changeCover:ImageView? = null

var currentSongIndex = 0
var songe:SongInfo ?= null

class MySongAdapter(context: Context, myListSong: ArrayList<SongInfo>) : BaseAdapter() {

    companion object{
        var TextTitle:TextView? = null
        var TextArtist:TextView? = null
        var ItemMusic:LinearLayout? = null
        var setCover:ImageView? = null
        var myListSong = ArrayList<SongInfo>()
    }

    private val mContext: Context
    init {
        MySongAdapter.myListSong = myListSong
        mContext = context
    }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val layoutInflate = LayoutInflater.from(mContext)
            var myView = layoutInflate.inflate(R.layout.row_layout,parent,false)
            val song = MySongAdapter.myListSong[position]

            TextTitle = myView.findViewById(R.id.textViewTitle)
            TextArtist = myView.findViewById(R.id.textViewDesc)
            ItemMusic = myView.findViewById(R.id.PlayMusic)
            setCover = myView.findViewById(R.id.coverMusic)

            MySongAdapter.TextTitle?.text = song.Title
            MySongAdapter.TextArtist?.text = song.Desc

            //cover
            val image = MySongAdapter.myListSong.get(position).getpach()?.let { getAlbumArt(it) }
            if(image != null)
            {
                MySongAdapter.setCover?.let {
                    Glide.with(mContext).asBitmap()
                        .load(image)
                        .into(it)
                }
            }
            else{
                MySongAdapter.setCover?.let {
                    Glide.with(mContext)
                        .load(R.drawable.coverrrl)
                        .into(it)
                }
            }

//            val holder : ViewHolder
//            if (convertView == null)
//            {
//                holder = ViewHolder()
//
//                myView.tag = holder
//            }
//            else
//            {
//                holder = convertView.tag as ViewHolder
//                myView = convertView
//            }

            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer()

            var flags = 1
            MySongAdapter.ItemMusic?.setOnClickListener {
                if (flags == 0)
                {
                    mediaPlayer!!.stop()
                    myView.findViewById<TextView>(R.id.textViewDesc).setTextColor(Color.parseColor("#c3c3c3"))
                    myView.findViewById<TextView>(R.id.textViewTitle).setTextColor(Color.parseColor("#ffffff"))
                    FragmentTracks.PlayN?.setImageResource(R.drawable.play_navar)
                    flags = 1;

                }
                else if (flags == 1)
                {
                    mediaPlayer!!.reset()
                    mediaPlayer!!.setDataSource(song.SongURL)
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()

                    myView.findViewById<TextView>(R.id.textViewDesc).setTextColor(Color.parseColor("#00d6b3"))
                    myView.findViewById<TextView>(R.id.textViewTitle).setTextColor(Color.parseColor("#13f8d1"))
                    changTextTitle = myView.findViewById<TextView>(R.id.textViewTitle).text.toString()
                    changTextArtist = myView.findViewById<TextView>(R.id.textViewDesc).text.toString()
                    changeCover = myView.findViewById<RoundedImageView>(R.id.coverMusic).coverNavar

                    FragmentTracks.TitleN?.setText(changTextTitle)
                    FragmentTracks.ArtistN?.setText(changTextArtist)
                    FragmentTracks.PlayN?.setImageResource(R.drawable.pause_icon_15)
                    flags = 0;
                }
                songe = song
            }
            return myView

        }

        override fun getItem(position: Int): Any {
            return MySongAdapter.myListSong[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return MySongAdapter.myListSong.size
        }

    //cover
    private fun getAlbumArt(uri: String): ByteArray? {
        val retrever = MediaMetadataRetriever()
        retrever.setDataSource(uri)
        val art: ByteArray? = retrever.embeddedPicture
        retrever.release()
        return art
    }


    //ToDo Search NEW
    //SEARCH
    fun updateList(musicFilesArrayList:ArrayList<SongInfo>)
    {
        myListSong = ArrayList<SongInfo>()
        myListSong.addAll(musicFilesArrayList)
        notifyDataSetChanged()
    }


}


//class ViewHolder
//{
////    var myViewImageCover : RoundedImageView? = null
//    var myViewTextTitle : TextView? = null
//    var myViewTextArtist : TextView? = null
//}
