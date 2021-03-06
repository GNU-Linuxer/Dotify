package com.davidxie.dotify.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import coil.load
import com.davidxie.dotify.DotifyApplication
import com.davidxie.dotify.R
import com.davidxie.dotify.databinding.ActivityPlayerBinding
import com.davidxie.dotify.model.Song
import kotlin.random.Random

const val SONG_KEY = "song object"
const val PLAY_COUNT_KEY = "song play count"

fun navigateToPlayerActivity(context: Context) = with(context){
    val intent = Intent(this, PlayerActivity::class.java).apply {
    }
    startActivity(intent)
}

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    // Function that set random play count text
    private var playCount: Int = Random.nextInt(100000, 1000000);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the Dotify Application
        val DotifyApp = (application as DotifyApplication)

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // we're re-creating this activity
            with(savedInstanceState) {
                // Restore play count number (second parameter is the default value if for some reason the saved value does not pass in)
                playCount = getInt(PLAY_COUNT_KEY, -1)
            }
        } else {
            // If a Song object is passed in while launching player activity, update the DotifyApplication's selected song
            val songFromIntent: Song? = intent.extras?.getParcelable<Song>(SONG_KEY)
            if(songFromIntent != null) {
                //Toast.makeText(this, "song is passed in from intent", Toast.LENGTH_SHORT).show()
                DotifyApp.selectedSong = songFromIntent
            }
        }
        setContentView(R.layout.activity_player)
        binding = ActivityPlayerBinding.inflate(layoutInflater).apply { setContentView(root) }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            //Toast.makeText(this@PlayerActivity, "received ${songObj?.title}", Toast.LENGTH_SHORT).show()

            // Dynamically set PlayerActivity's content using passed-in songObj
            // otherwise, default values hard-coded in the player_activity.xml will be used
            DotifyApp.selectedSong?.let { nonNullSongObj ->
                albumCoverImage.load(nonNullSongObj.largeImageURL)
                titleText.text = nonNullSongObj.title
                artistText.text = nonNullSongObj.artist
            }
            playCountText.text = playCount.toString() + " plays"

            // Function that handles album art long click
            // Code for handle long-click is adapted from https://stackoverflow.com/questions/49712663/how-to-properly-use-setonlongclicklistener-with-kotlin
            albumCoverImage.setOnLongClickListener {
                // Generate a random color
                playCountText.setTextColor(Color.rgb(Random.nextInt(0, 256),Random.nextInt(0, 256),Random.nextInt(0, 256)))
                return@setOnLongClickListener true
            }

            // Function steps that get called when previous button is pressed
            // binding.previousButton.setOnClickListener { view: View ->
            previousButton.setOnClickListener {
                Toast.makeText(this@PlayerActivity, "Skipping to previous track", Toast.LENGTH_SHORT).show() }

            // Function steps that get called when play button is pressed
            playButton.setOnClickListener {
                playCount += 1;
                playCountText.text = playCount.toString() + " plays"
            }

            // Function steps that get called when next button is pressed
            nextButton.setOnClickListener {
                Toast.makeText(this@PlayerActivity, "Skipping to next track", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // Menu icon behavior
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.player_menu, menu)
        return true // we modified option menu (return false if we need to hide it, for instance, user is not logged in yet)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Get the Dotify Application
        val DotifyApp = (application as DotifyApplication)
        when(item.itemId) {
            R.id.player_menu_settings -> {
                DotifyApp.selectedSong?.let { nonNullSongObj ->
                    // Note: pass the nonNullSongObj to the SettingsActivity to simplify the intent that also get relayed to the SettingsFragment
                    navigateToSettingsActivity(this@PlayerActivity, nonNullSongObj, playCount)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Handle when the top-left back button is clicked
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    // Save play count number
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(PLAY_COUNT_KEY, playCount)
        super.onSaveInstanceState(outState)
    }
}
