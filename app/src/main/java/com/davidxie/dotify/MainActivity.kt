package com.davidxie.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.davidxie.dotify.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var playCount: Int = 0
    private var isEditing: Boolean = false;
    private lateinit var prevUsername: Editable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        // Read previous username
        prevUsername = binding.usernameTextEdit.text
        Toast.makeText(this, prevUsername.toString(), Toast.LENGTH_SHORT).show()

        // Function that set random play count text
        playCount = Random.nextInt(100000, 1000000);
        binding.playCountText.text = playCount.toString() + " plays"

        binding.changeUserButton.setOnClickListener {
            // When the user is currently not editing the username
            if (!isEditing) {
                binding.changeUserButton.text = "Apply";
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
