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
import com.example.musicplayer.Helper.MyAlbumAdapter
import com.example.musicplayer.Helper.MyFolderAdapter
import com.example.musicplayer.Less.AlbumActivity
import com.example.musicplayer.Less.FolderActivity
import com.example.musicplayer.Models.AlbumInfo
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R

@Suppress("DEPRECATION")
class FragmentFolders : Fragment() {

    private lateinit var recyclerFolder: RecyclerView

    @SuppressLint("CutPasteId", "Recycle")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_folder, container, false)
        recyclerFolder = view.findViewById(R.id.ReciycleViewFolder)

        recyclerFolder.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity,1)
        }
        
        val allFolder = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.AudioColumns.TRACK
        val sortOrder = MediaStore.Audio.AudioColumns.TITLE + " ASC"
        val cursor = activity!!.contentResolver.query(allFolder,null,selection,null,sortOrder)
        val listOfFolders = ArrayList<SongInfo>()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @Suppress("DEPRECATION")
                    val folderURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA))
                    val folderDesc = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA))
                    val folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE))
                    val folderIcon = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM))

                    listOfFolders.add(
                        SongInfo(
                            folderName,
                            folderDesc,
                            folderURL,
                            folderIcon
                        )
                    )

                } while (cursor.moveToNext())
            }
            cursor.close()

            val folderList = view.findViewById<RecyclerView>(R.id.ReciycleViewFolder)
            folderList.adapter = MyFolderAdapter(
                activity!!.applicationContext,
                listOfFolders
            ){
                val intent = Intent(context, FolderActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }

}