package com.davidxie.dotify

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.davidxie.dotify.databinding.PlayerActivityBinding
import kotlin.random.Random


class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: PlayerActivityBinding
    private var playCount: Int = 0
    private var isEditing: Boolean = false;
    private lateinit var prevUsername: Editable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)
        binding = PlayerActivityBinding.inflate(layoutInflater).apply { setContentView(root) }

        // Function that set random play count text
        playCount = Random.nextInt(100000, 1000000);
        binding.playCountText.text = playCount.toString() + " plays"


        // Function that handles album art long click
        // Code for handel long-click is adapted from https://stackoverflow.com/questions/49712663/how-to-properly-use-setonlongclicklistener-with-kotlin
        binding.albumCoverImage.setOnLongClickListener {
            // Generate a random color
            binding.playCountText.setTextColor(Color.rgb(Random.nextInt(0, 256),Random.nextInt(0, 256),Random.nextInt(0, 256)))
            return@setOnLongClickListener true
        }

        // Function steps that get called when previous button is pressed
        // binding.previousButton.setOnClickListener { view: View ->
        binding.previousButton.setOnClickListener {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        // Function steps that get called when play button is pressed
        binding.playButton.setOnClickListener {
            playCount = playCount + 1;
            binding.playCountText.text = playCount.toString() + " plays"
        }

        // Function steps that get called when next button is pressed
        binding.nextButton.setOnClickListener {
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }

}
