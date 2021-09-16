package com.example.musicPlayer.Ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.musicPlayer.BuildConfig
import com.example.musicPlayer.Helper.*
import com.example.musicPlayer.Models.SongInfo
import com.example.musicPlayer.R
import com.example.musicPlayer.Services.DataService
import com.example.musicPlayer.Services.MediaPlayerService
import com.example.musicPlayer.Services.MediaPlayerService.LocalBinder
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_play.*

//services
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

        var TitleN: TextView? = null
        var ArtistN: TextView? = null
        var PlayN: ImageView? = null
        var NextN: ImageView? = null
        var PreviewN: ImageView? = null
        var Cover: ImageView? = null
        var backNavar: ImageView? = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callStateListener()
        blurBackground()

        TitleN = findViewById(R.id.textViewTitleN)
        ArtistN = findViewById(R.id.textViewArtistN)
        Cover = findViewById(R.id.coverNavar)
        backNavar = findViewById(R.id.imageViewBackNavar)
        PlayN = findViewById(R.id.imageViewPlayN)
        NextN = findViewById(R.id.imageViewNextN)
        PreviewN = findViewById(R.id.imageViewPreviewN)

        TitleN?.text = changTextTitle
        ArtistN?.text = changTextArtist
        TitleN?.isSelected =true
        ArtistN?.isSelected =true

        if (changeCover != null) {
            Cover?.let { it1 -> Glide.with(this).load(changeCover).into(it1) }
            backNavar?.let { it1 -> Glide.with(this).load(changeCover).into(it1) }
        }
        else {
            Cover?.let { it1 ->
                Glide.with(this).load(R.drawable.coverrrl)
                    .into(it1)
            }
            backNavar?.let { it1 ->
                Glide.with(this).load(R.drawable.back_navar_music)
                    .into(it1)
            }
        }

        if (mediaPlayer==null)
            PlayN?.setImageResource(R.drawable.ic_round_play)
        else
            PlayN?.setImageResource(R.drawable.ic_round_pause)

        //strip Control music play button
        PlayN?.setOnClickListener {
            animationZoom(PlayN!!)
            if (mediaPlayer!!.isPlaying) {
                PlayN?.setImageResource(R.drawable.ic_round_play)
                mediaPlayer!!.pause()
            }
            else if (mediaPlayer!=null) {
                PlayN?.setImageResource(R.drawable.ic_round_pause)
                mediaPlayer!!.start()
            }
        }

        //ToDo this fixes   Preview btn
        //strip Control music preview btn
        PreviewN?.setOnClickListener {
            animationBlur(PreviewN!!)
            if (currentSongIndex>0) {
                DataService.playSong(currentSongIndex - 1)
                currentSongIndex -= 1
            }
            else {
                DataService.playSong(MyTrackAdapter.myListTrack.size - 1)
                currentSongIndex = MyTrackAdapter.myListTrack.size -1
            }
        }

        //strip Control music next button
        NextN?.setOnClickListener {
            animationBlur(NextN!!)
            //ToDo this fixes   Next btn
        }

        val navarLayout = findViewById<ConstraintLayout>(R.id.navarLayout).setOnClickListener {
            if (mediaPlayer != null) {
                val intent = Intent(this, PlayActivity::class.java)
                intent.putExtra("position", PlayActivity.position)
                startActivity(intent)
            }
            else
                Toast.makeText(this,"click on a Song please",Toast.LENGTH_SHORT).show()
        }

        //ToDo this fixes Services
        //services
        songinfo?.getpach()?.let { playAudio(it) }

        // Request allow
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST_CODE
            )
        else
            loadData()

    }

    private fun loadData() {
        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(3)!!.setIcon(R.drawable.favourite_selected)
    }

    //  Requested allow 2
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                loadData()
                TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.appBarSearch),
                        "Search bar", "Find the music you want")
                        .tintTarget(true)
                        .outerCircleColor(R.color.colorRed)
                )
            }
        }
    }


//SEARCH
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
             R.id.appBarSearch -> {
                 val intent = Intent(this, SearchActivity::class.java)
                 startActivity(intent)
             }
             R.id.about -> {
                 val intent = Intent(this, AboutActivity::class.java)
                 startActivity(intent)
             }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
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
                    TelephonyManager.CALL_STATE_OFFHOOK, TelephonyManager.CALL_STATE_RINGING ->
                        if (mediaPlayer != null) {
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


    //services
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
        //Check in service is active
        if (!serviceBound) {
            val playerIntent = Intent(this, MediaPlayerService::class.java)
            playerIntent.putExtra("media", media)
            startService(playerIntent)
            bindService(playerIntent, serviceConnection!!, BIND_AUTO_CREATE)
        }
        else {
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

    private fun blurBackground() {
        val radius = 5f
        val decorView = window.decorView
        val rootView: ViewGroup = decorView.findViewById(android.R.id.content)
        val windowBackground = decorView.background

        blurBack.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)
    }

    //animation
    private fun animationZoom(imageView: ImageView) {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_pause)
        imageView.startAnimation(animScale)
    }

    private fun animationBlur(imageView: ImageView) {
        val animScale = AnimationUtils.loadAnimation(this, R.anim.anim_backward)
        imageView.startAnimation(animScale)
    }


}



