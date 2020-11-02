package com.example.musicplayer.Helper

import android.content.Context
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.Models.AlbumInfo
import com.example.musicplayer.R


var adaptere: MyAllbumAdapter?= null
var changTextAlbum = "Title"
var changTextArtistAlbum = "Artist"

class MyAllbumAdapter(
    val context: Context,
    private val albums: ArrayList<AlbumInfo>,
    private val itemClicked: (AlbumInfo) -> Unit
) : RecyclerView.Adapter<MyAllbumAdapter.AlbumHolder>() {

    companion object{
        var myAlbumSong = ArrayList<AlbumInfo>()
    }

    private val mContext: Context
    init {
        MyAllbumAdapter.myAlbumSong = albums
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.allbum_item, parent, false)
        return AlbumHolder(view, itemClicked)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: MyAllbumAdapter.AlbumHolder, position: Int) {
        return holder.bindAlbum(albums[position], context, position)
    }

    inner class AlbumHolder(itemView: View?, val itemClicked: (AlbumInfo) -> Unit) : RecyclerView.ViewHolder(itemView!!)
    {
        private var setAlbumOne = itemView?.findViewById<ImageView>(R.id.imageViewCoverAllbum)
        private var setAlbumTwo = itemView?.findViewById<ImageView>(R.id.imageViewCoverAllbum2)
        private var setAlbumThree = itemView?.findViewById<ImageView>(R.id.imageViewCoverAllbum3)
        private var albumTitle = itemView?.findViewById<TextView>(R.id.textViewTitleAllbum)
        private var albumArtist = itemView?.findViewById<TextView>(R.id.textViewArtistAllbum)

        fun bindAlbum(albuminfo:AlbumInfo, context:Context, position: Int)
        {
            val resourceId = context.resources.getIdentifier(albuminfo.album, "drawable", context.packageName)

            albumTitle?.text = albuminfo.title
            albumArtist?.text = albuminfo.artist

            //cover
            val image = albums[position].getpach()?.let { getAlbumArt(it) }
            if(image != null)
            {
                setAlbumOne?.let {
                    Glide.with(mContext).asBitmap()
                        .load(image)
                        .into(it)
                }
            }
            else{
                setAlbumOne?.let {
                    Glide.with(mContext)
                        .load(R.drawable.coverrrl)
                        .into(it)
                };
            }

            //cover2
            val imageTwo = albums[position].getpach()?.let { getAlbumArt(it) }
            if(image != null)
            {
                setAlbumTwo?.let {
                    Glide.with(mContext).asBitmap()
                        .load(image)
                        .into(it)
                }
            }
            else{
                setAlbumTwo?.let {
                    Glide.with(mContext)
                        .load(R.drawable.coverrrl)
                        .into(it)
                };
            }

            //cover3
            val imageThree = albums[position].getpach()?.let { getAlbumArt(it) }
            if(image != null)
            {
                setAlbumThree?.let {
                    Glide.with(mContext).asBitmap()
                        .load(image)
                        .into(it)
                }
            }
            else{
                setAlbumThree?.let {
                    Glide.with(mContext)
                        .load(R.drawable.coverrrl)
                        .into(it)
                };
            }

            itemView.setOnClickListener { itemClicked(albuminfo)
                changTextAlbum = albumTitle?.text.toString()
                changTextArtistAlbum = albumArtist?.text.toString()
            }
        }

    }

    //cover
    private fun getAlbumArt(uri: String): ByteArray? {
        val retrever = MediaMetadataRetriever()
        retrever.setDataSource(uri)
        val art: ByteArray? = retrever.embeddedPicture
        retrever.release()
        return art
    }

}