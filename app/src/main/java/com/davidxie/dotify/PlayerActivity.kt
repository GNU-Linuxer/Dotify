package com.davidxie.dotify

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.davidxie.dotify.databinding.PlayerActivityBinding
import com.ericchee.songdataprovider.Song
import kotlin.random.Random

const val SONG_KEY = "song object"

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
    private lateinit var binding: PlayerActivityBinding
    // Function that set random play count text
    private var playCount: Int = Random.nextInt(100000, 1000000);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)
        binding = PlayerActivityBinding.inflate(layoutInflater).apply { setContentView(root) }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            val launchIntent = intent
            val songObj: Song? = launchIntent.extras?.getParcelable(SONG_KEY)
            //Toast.makeText(this@PlayerActivity, "received ${songObj?.title}", Toast.LENGTH_SHORT).show()

            // Dynamically set PlayerActivity's content using passed-in songObj
            if (songObj != null) { // otherwise, default values hard-coded in the player_activity.xml will be used
                albumCoverImage.setImageResource(songObj.largeImageID)
                titleText.text = songObj.title
                artistText.text = songObj.artist
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

    // Handel when the top-left back button is clicked
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}
