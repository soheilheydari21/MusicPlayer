package com.example.musicplayer.Controllers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.Helper.MyAllbumAdapter
import com.example.musicplayer.Helper.MyFolderAdapter
import com.example.musicplayer.Less.AlbumActivity
import com.example.musicplayer.Less.FolderActivity
import com.example.musicplayer.Models.AlbumInfo
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R

class FragmentFolders : Fragment() {

    lateinit var RecycleFolder: RecyclerView
    lateinit var adapter : MyFolderAdapter

    @SuppressLint("CutPasteId", "Recycle")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_three, container, false)
        RecycleFolder = view.findViewById(R.id.ReciycleViewFolder)

        RecycleFolder.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity,2)
        }
        
        val allFolder = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.TRACK+ "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor = activity!!.contentResolver.query(allFolder,null,selection,null,sortOrder)
        val listofFolders = ArrayList<SongInfo>()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @Suppress("DEPRECATION")
                    val folderURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val folderAuthor = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    val folderIcon = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))

                    listofFolders.add(
                        SongInfo(
                            folderName,
                            folderAuthor,
                            folderURL,
                            folderIcon
                        )
                    )

                } while (cursor.moveToNext())
            }
            cursor.close()

            val songList = view.findViewById<RecyclerView>(R.id.ReciycleViewFolder)
            songList.adapter = MyFolderAdapter(
                activity!!.applicationContext,
                listofFolders
            ){
                val intent = Intent(context, FolderActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }

}