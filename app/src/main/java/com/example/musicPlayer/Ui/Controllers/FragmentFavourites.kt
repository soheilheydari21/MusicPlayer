package com.example.musicPlayer.Ui.Controllers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicPlayer.DataBase.DBManager
import com.example.musicPlayer.Helper.*
import com.example.musicPlayer.Ui.MainActivity
import com.example.musicPlayer.Models.LikeInfo
import com.example.musicPlayer.R
import kotlinx.android.synthetic.main.track_item.view.*


@Suppress("DEPRECATION")
class FragmentFavourites : Fragment() {

    private lateinit var recycleLike: RecyclerView
    private val listOfLikes = ArrayList<LikeInfo>()

    companion object{
        var musicAdapter: MyFavouritesAdapter? = null
        var coverList: ImageView? = null
    }

    @SuppressLint("Recycle", "CutPasteId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        recycleLike = view.findViewById(R.id.RecyclerViewLike)
        recycleLike.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity,1)
        }

        coverList = view.findViewById(R.id.coverMusic)

        //list music Like
        val allSong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = requireActivity().contentResolver.query(allSong,null,null,null,sortOrder)

        return view
    }

    inner class MyFavouritesAdapter(
        context: Context,
        myListSong: ArrayList<LikeInfo>
    ) : RecyclerView.Adapter<MyFavouritesAdapter.LikeHolder>() {

        var myListLike = ArrayList<LikeInfo>()
        private val mContext: Context
        init {
            myListLike = myListSong
            mContext = context
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.track_item, parent, false)
            return LikeHolder(view)
        }

        override fun getItemCount(): Int {
            return myListLike.size
        }

        override fun onBindViewHolder(holder: LikeHolder, position: Int) {
            return holder.bindMusic(myListLike[position])
        }

        inner class LikeHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

            private val layoutCover = itemView?.findViewById<ConstraintLayout>(R.id.layoutCover)
            private val setCover = itemView?.findViewById<ImageView>(R.id.coverMusic)
            private val songTitle = itemView?.findViewById<TextView>(R.id.textViewTitle)
            private val songArtist = itemView?.findViewById<TextView>(R.id.textViewDesc)
            private val itemMusic = itemView?.findViewById<ConstraintLayout>(R.id.PlayMusic)

            @SuppressLint("ResourceType")
            fun bindMusic(likeInfo:LikeInfo) {
                songTitle?.text = likeInfo.Title
                songArtist?.text = likeInfo.Artist

                //cover
                val image = myListLike[position].getpach()?.let { getSongArt(it) }
                if(image != null) {
                    setCover?.let {
                        Glide.with(mContext).asBitmap()
                            .load(image)
                            .into(it)
                    }
                }
                else {
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
                    mediaPlayer!!.setDataSource(likeInfo.SongURL)
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
                    animationItem()
                    animationCover()

                    itemView.textViewDesc.setTextColor(Color.parseColor("#00d6b3"))
                    itemView.textViewTitle.setTextColor(Color.parseColor("#13f8d1"))
                    changTextTitle = itemView.textViewTitle.text.toString()
                    changTextArtist = itemView.textViewDesc.text.toString()
                    changeCover = image

                    MainActivity.TitleN?.text = changTextTitle
                    MainActivity.ArtistN?.text = changTextArtist
                    MainActivity.PlayN?.setImageResource(R.drawable.ic_round_pause)

                    if (image != null)
                        MainActivity.Cover?.let { it1 -> Glide.with(mContext).load(image).into(it1) }
                    else
                        MainActivity.Cover?.let { it1 -> Glide.with(mContext).load(R.drawable.coverrrl).into(it1) }

                }

            }

            private fun animationItem() {
                val animScale = AnimationUtils.loadAnimation(mContext, R.anim.anim_pause)
                itemMusic?.startAnimation(animScale)
            }

            private fun animationCover() {
                val animScale = AnimationUtils.loadAnimation(mContext, R.anim.anim_pause)
                layoutCover?.startAnimation(animScale)
            }

        }

    }

    fun loadData(title: String){
        val dbManager = context?.let { DBManager(it) }
        val columns = arrayOf("ID", "Title", "Artist", "SongUrl", "Cover")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager?.RunQuery(columns, "Title Like ?", selectionArgs, "ID")

        listOfLikes.clear()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                    val Title = cursor.getString(cursor.getColumnIndex("Title"))
                    val Artist = cursor.getString(cursor.getColumnIndex("Artist"))
                    val SongUrl = cursor.getString(cursor.getColumnIndex("SungUrl"))
                    val Cover = cursor.getString(cursor.getColumnIndex("Cover"))
                    listOfLikes.add(LikeInfo(ID, Title, Artist, SongUrl, Cover))

                }while(cursor.moveToNext())
            }
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