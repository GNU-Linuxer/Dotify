package com.davidxie.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.davidxie.dotify.databinding.ActivitySettingsBinding
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

fun navigateToSettingsActivity(context: Context, song: Song, playCount: Int) = with(context){
    val intent = Intent(this, SettingsActivity::class.java).apply {
        val bundle = Bundle().apply {
            // Pack data inside this bundle
            putParcelable(SONG_KEY, song)
            putInt(PLAY_COUNT_KEY, playCount)
        }
        putExtras(bundle)
    }
    startActivity(intent)
}

class SettingsActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.navHost) }
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //DEBUG codes
        intent.extras?.getParcelable<Song>(SONG_KEY)?.let{
            Toast.makeText(this@SettingsActivity, it.title, Toast.LENGTH_SHORT).show()
        }
        intent.extras?.getInt(PLAY_COUNT_KEY)?.let{
            Toast.makeText(this@SettingsActivity,it.toString(), Toast.LENGTH_SHORT).show()
        }

        binding = ActivitySettingsBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding){
            // Programmatically set the nav graph
            navController.setGraph(R.navigation.nav_graph, Bundle().apply {
                putParcelable("song", intent.extras?.getParcelable<Song>(SONG_KEY))
                putInt("play", intent.extras?.getInt(PLAY_COUNT_KEY) ?: -1)
            })
        }

//        val launchIntent = intent
//        var songObj: Song? = launchIntent.extras?.getParcelable(SONG_KEY)
//        var playCount: Int? = launchIntent.extras?.getInt(PLAY_COUNT_KEY)
//
//        if (songObj == null) {
//            // if songObject is not passed in to this fragment, the first song will be used
//            songObj = SongDataProvider.getAllSongs()[0]
//            Toast.makeText(this@SettingsActivity,"Null Song Object detected", Toast.LENGTH_SHORT).show()
//        }
//        if (playCount == null) {
//            // if playCount is not passed in to this fragment, we set a playcount of -1
//            playCount = -1
//            Toast.makeText(this@SettingsActivity,"Null Player Count detected", Toast.LENGTH_SHORT).show()
//        }
//
//        navController.setGraph(R.navigation.nav_graph, Bundle().apply {
//            putParcelable(SONG_KEY, songObj)
//            putInt(PLAY_COUNT_KEY, playCount)
//        })

        //setupActionBarWithNavController(navController)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        navController.navigateUp()
//        return true
//    }
}