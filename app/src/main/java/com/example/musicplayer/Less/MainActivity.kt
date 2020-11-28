package com.example.musicplayer.Less

//import com.example.musicplayer.Helper.MyTrackAdapter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Bundle
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.musicplayer.Controllers.FragmentTracks
import com.example.musicplayer.Helper.MyPagerAdapter
import com.example.musicplayer.Helper.MyTrackAdapter
import com.example.musicplayer.Helper.mediaPlayer
import com.example.musicplayer.Models.SongInfo
import com.example.musicplayer.R
import com.example.musicplayer.Services.MediaPlayerService
import com.example.musicplayer.Services.MediaPlayerService.LocalBinder
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_play.*


//++++services
private var player: MediaPlayerService? = null
var serviceBound = false

var listofsongs = ArrayList<SongInfo>()

@Suppress("DEPRECATION", "DEPRECATED_IDENTITY_EQUALS")
open class MainActivity : AppCompatActivity() {

    var adapter: MyTrackAdapter? = null

    companion object{
        const val PERMISSION_REQUEST_CODE = 12

        var songinfo:SongInfo? = null
        var ongoingCall = false
        var phoneStateListener: PhoneStateListener? = null
        var telephonyManager: TelephonyManager? = null
        var audioManager: AudioManager? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callStateListener()

        //ToDo this fixes Services
        //+++services
        songinfo?.SongURL?.let { playAudio(it) }

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
                PERMISSION_REQUEST_CODE
            )
        else
        {
            loadData()
        }

    }

    private fun loadData() {
        val fragmentAdapter = MyPagerAdapter(
            supportFragmentManager
        )
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_favorite_24)
    }

    //  Requested allo 2
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                loadData()
                TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.appBarSearch),
                        "Search bar", "it can be used in later Updates")
                        .tintTarget(true)
                        .outerCircleColor(R.color.colorRed)
                )
            }
        }
    }


//ToDo this fixes   Search 1
//SEARCH
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
             R.id.appBarSearch ->{
                 val intent = Intent(this, SearchActivity::class.java)
                 startActivity(intent)
             }
             R.id.about ->{
                 val intent = Intent(this, AboutActivity::class.java)
                 startActivity(intent)
             }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?):Boolean{

        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu!!.findItem(R.id.appBarSearch)
        return super.onCreateOptionsMenu(menu)
    }



//pause music when calling
    private fun callStateListener() {

        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?

        phoneStateListener = object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String) {
                when (state) {
                    TelephonyManager.CALL_STATE_OFFHOOK, TelephonyManager.CALL_STATE_RINGING -> if (mediaPlayer != null) {
                        mediaPlayer!!.pause()
                        ongoingCall = true
                    }
                    TelephonyManager.CALL_STATE_IDLE ->
                        if (mediaPlayer != null) {
                            if (ongoingCall) {
                                ongoingCall = false
                                mediaPlayer!!.start()
                            }
                        }
                }
            }
        }

        telephonyManager!!.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }



    //++++services
    private var serviceConnection: ServiceConnection? = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as LocalBinder
            player = binder.service
            serviceBound = true
            Toast.makeText(this@MainActivity, "Service Bound", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceBound = false
        }
    }


    open fun playAudio(media: String) {
        //Check is service is active
        if (!serviceBound) {
            val playerIntent = Intent(this, MediaPlayerService::class.java)
            playerIntent.putExtra("media", media)
            startService(playerIntent)
            bindService(playerIntent, serviceConnection!!, BIND_AUTO_CREATE)
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putBoolean("ServiceState", serviceBound)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        serviceBound = savedInstanceState.getBoolean("ServiceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (serviceBound) {
            unbindService(serviceConnection!!)
            //service is active
            player!!.stopSelf()
        }
    }


}



