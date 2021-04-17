package com.davidxie.dotify

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.davidxie.dotify.databinding.ActivityMainBinding
import kotlin.random.Random


class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var playCount: Int = 0
    private var isEditing: Boolean = false;
    private lateinit var prevUsername: Editable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        // Function that set random play count text
        playCount = Random.nextInt(100000, 1000000);
        binding.playCountText.text = playCount.toString() + " plays"

        // Function that handles when change username button is clicked
        binding.changeUserButton.setOnClickListener {
            // When the user is currently not editing the username
            if (!isEditing) {
                binding.changeUserButton.text = "Apply";

                // Prefill the current username to the editable text field
                binding.usernameTextEdit.setText(binding.usernameText.text);

                // Hide Textview and show EditText
                binding.usernameText.visibility = View.GONE
                binding.usernameTextEdit.visibility = View.VISIBLE

                // Change the constraint layout of change user button to app:layout_constraintLeft_toRightOf="@id/usernameTextEdit"
                val constraintLayout = findViewById<ConstraintLayout>(R.id.username_row)
                val constraintSet = ConstraintSet()
                constraintSet.clone(constraintLayout)
                constraintSet.connect(
                    R.id.changeUserButton,
                    ConstraintSet.LEFT,
                    R.id.usernameTextEdit,
                    ConstraintSet.RIGHT,
                    0
                )
                constraintSet.applyTo(constraintLayout)

                isEditing = true;
            }
            // When the user is currently editing the username
            else {
                binding.changeUserButton.text = "Change User";

                // Conditionally apply new username to Textview
                val newUsername = binding.usernameTextEdit.text;
                if (newUsername.toString().length == 0) {
                    Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    binding.usernameText.text = newUsername
                }

                // Show Textview and hide EditText
                binding.usernameText.visibility = View.VISIBLE
                binding.usernameTextEdit.visibility = View.GONE

                // Change the constraint layout of change user button to app:layout_constraintLeft_toRightOf="@id/usernameText"
                val constraintLayout = findViewById<ConstraintLayout>(R.id.username_row)
                val constraintSet = ConstraintSet()
                constraintSet.clone(constraintLayout)
                constraintSet.connect(
                    R.id.changeUserButton,
                    ConstraintSet.LEFT,
                    R.id.usernameText,
                    ConstraintSet.RIGHT,
                    0
                )
                constraintSet.applyTo(constraintLayout)

                isEditing = false;
            }
        }

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
