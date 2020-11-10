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
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R

//ToDo this fixes  folder adapter

var changNameFolder = "Title"
var changAddressFolder = "Artist"

class MyFolderAdapter(
    val context: Context,
    private val folders: ArrayList<SongInfo>,
    private val itemClicked: (SongInfo) -> Unit
) : RecyclerView.Adapter<MyFolderAdapter.FolderHolder>() {

    companion object{
        var myFolderSong = ArrayList<SongInfo>()
    }

    private val mContext: Context
    init {
        MyFolderAdapter.myFolderSong = folders
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.folder_item, parent, false)
        return FolderHolder(view, itemClicked)
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    override fun onBindViewHolder(holder: MyFolderAdapter.FolderHolder, position: Int) {
        return holder.bindFolder(folders[position], context, position)
    }

    inner class FolderHolder(itemView: View?, val itemClicked: (SongInfo) -> Unit) : RecyclerView.ViewHolder(itemView!!)
    {
        private var setCoverFolder = itemView?.findViewById<ImageView>(R.id.coverFolder)
        private var folderTitle = itemView?.findViewById<TextView>(R.id.folderName)
        private var folderAddress = itemView?.findViewById<TextView>(R.id.folderAddres)

        fun bindFolder(songinfo:SongInfo, context:Context, position: Int)
        {
            val resourceId = context.resources.getIdentifier(songinfo.Cover, "drawable", context.packageName)
            folderTitle?.text = songinfo.Title
            folderAddress?.text = songinfo.Desc

            itemView.setOnClickListener { itemClicked(songinfo)
                changNameFolder = folderTitle?.text.toString()
                changAddressFolder = folderAddress?.text.toString()

            }
        }

    }


}