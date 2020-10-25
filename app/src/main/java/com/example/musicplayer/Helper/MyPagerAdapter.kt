package com.example.musicplayer.Helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.musicplayer.Controllers.FragmentFavourites
import com.example.musicplayer.Controllers.FragmentTracks
import com.example.musicplayer.Controllers.FragmentFolders
import com.example.musicplayer.Controllers.FragmentAlbums

class MyPagerAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0->{
                FragmentTracks()
            }
            1->{
                FragmentAlbums()
            }
            2->{
                FragmentFolders()
            }
            else->{
                return FragmentFavourites()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0-> "Tracks"
            1-> "Albums"
            2-> "Folders"
            else-> {
                return ""

            }

        }
    }
}