package com.davidxie.dotify

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.davidxie.dotify.databinding.ActivityPlayerBinding
import com.ericchee.songdataprovider.Song
import kotlin.random.Random

const val SONG_KEY = "song object"
const val PLAY_COUNT_KEY = "song play count"

fun navigateToPlayerActivity(context: Context, song: Song) = with(context){
    val intent = Intent(this, PlayerActivity::class.java).apply {
        val bundle = Bundle().apply {
            // Pack data inside this bundle
            putParcelable(SONG_KEY, song)
        }
        putExtras(bundle)
    }
    startActivity(intent)
}

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    // Function that set random play count text
    private var playCount: Int = Random.nextInt(100000, 1000000);
    private var songObj: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // we're re-creating this activity
            with(savedInstanceState) {
                // Restore play count number (second parameter is the default value if for some reason the saved value does not pass in)
                playCount = getInt(PLAY_COUNT_KEY, -1)
            }
        }

        setContentView(R.layout.activity_player)
        binding = ActivityPlayerBinding.inflate(layoutInflater).apply { setContentView(root) }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val launchIntent = intent
        songObj = launchIntent.extras?.getParcelable(SONG_KEY)

        with(binding) {
            //Toast.makeText(this@PlayerActivity, "received ${songObj?.title}", Toast.LENGTH_SHORT).show()

            // Dynamically set PlayerActivity's content using passed-in songObj
            // otherwise, default values hard-coded in the player_activity.xml will be used
            songObj?.let { nonNullSongObj ->
                albumCoverImage.setImageResource(nonNullSongObj.largeImageID)
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
        when(item.itemId) {
            R.id.player_menu_settings -> {
                songObj?.let { nonNullSongObj ->
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
