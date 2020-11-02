package com.example.musicplayer.Less

//import com.example.musicplayer.Helper.MyTrackAdapter
import android.app.SearchManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.musicplayer.Controllers.FragmentTracks
import com.example.musicplayer.Helper.MyPagerAdapter
import com.example.musicplayer.Helper.MyTrackAdapter
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    var adapter: MyTrackAdapter? = null
    var listofsongs = ArrayList<SongInfo>()
    companion object{
        const val PERMISSION_REQUEST_CODE = 12
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Request allo
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        else
        {
            loadData()
        }

        // Search
//        LoadData("%")

//        var dbManager = DBManager(this)
//        var values = ContentValues()
//        values.put("Title", findViewById<TextView>(R.id.textViewTitle).text.toString())
//        values.put("Artist", findViewById<TextView>(R.id.textViewArtist).text.toString())

//        val ID = dbManager.Insert(values)

    }

    private fun loadData() {
        val fragmentAdapter = MyPagerAdapter(
            supportFragmentManager
        )
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_favorite_24)

    }

    //ToDo: fix this    Search
     //  source search
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu,menu)
//
//        val searchView = menu!!.findItem(R.id.appBarSearch).actionView as SearchView
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener
//        {
//            override fun onQueryTextChange(newText: String?): Boolean {
//                LoadData("%" + newText + "%")
//                return false
//            }
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//        })
//        return super.onCreateOptionsMenu(menu)
//
//    }

    //  Requested allo 2
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MainActivity.PERMISSION_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                loadData()
            }
        }
    }


//ToDo Search NEW2
//SEARCH
    override fun onCreateOptionsMenu(menu: Menu?):Boolean{

        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu!!.findItem(R.id.appBarSearch)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint= """Search something!"""

//        val searchView = menu!!.findItem(R.id.appBarSearch).actionView as SearchView
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String):Boolean{

        val userInput:String = newText.toLowerCase()
        val myFiles: ArrayList<SongInfo> = ArrayList()
        for (song:SongInfo in listofsongs)
        {
            if(song.Title!!.toLowerCase().contains(userInput))
            {
                myFiles.add(song)
            }
        }
        FragmentTracks.musicAdapter?.updateList(myFiles)
        loadData()
        return true
    }

}

