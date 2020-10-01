package com.example.musicplayer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.OtherClass.AllbumInfo
import com.example.musicplayer.R

var adaptere: MySongAdapter?= null

class MyAllbumAdapter(val context:Context, val allbums:List<AllbumInfo>, val itemClicked: (AllbumInfo) -> Unit) : RecyclerView.Adapter<MyAllbumAdapter.AllbumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllbumHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.allbum_item, parent, false)
        return AllbumHolder(view, itemClicked)
    }

    override fun getItemCount(): Int {
        return allbums.count()
    }

    override fun onBindViewHolder(holder: AllbumHolder, position: Int) {
        holder?.bindAllbum(allbums[position], context)
    }

    inner class AllbumHolder(itemView: View?, val itemClicked: (AllbumInfo) -> Unit) : RecyclerView.ViewHolder(itemView!!)
    {
        val allbumImage = itemView?.findViewById<ImageView>(R.id.imageViewCoverAllbum)
        val allbumImage2 = itemView?.findViewById<ImageView>(R.id.imageViewCoverAllbum2)
        val allbumImage3 = itemView?.findViewById<ImageView>(R.id.imageViewCoverAllbum3)
        val allbumTitle = itemView?.findViewById<TextView>(R.id.textViewTitleAllbum)
        val allbumArtist = itemView?.findViewById<TextView>(R.id.textViewArtistAllbum)

        fun bindAllbum(product:AllbumInfo, context:Context)
        {
            val resourceId = context.resources.getIdentifier(product.allbum, "drawble", context.packageName)

            allbumImage!!.setImageResource(resourceId)
            allbumImage2!!.setImageResource(resourceId)
            allbumImage3!!.setImageResource(resourceId)
            allbumTitle?.text = product.title
            allbumArtist?.text = product.artist

            itemView.setOnClickListener { itemClicked(product) }
        }
    }
}