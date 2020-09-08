package com.example.musicplayer.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.musicplayer.Fragments.FragmentFour
import com.example.musicplayer.Fragments.FragmentOne
import com.example.musicplayer.Fragments.FragmentThree
import com.example.musicplayer.Fragments.FragmentTwo

class MyPagerAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0->{
                FragmentOne()
            }
            1->{
                FragmentTwo()
            }
            2->{
                FragmentThree()
            }
            else->{
                return FragmentFour()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0-> "Device"
            1-> "Online"
            2-> "Album"
            else-> {
                return ""

            }

        }
    }
}