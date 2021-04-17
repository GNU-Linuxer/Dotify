package com.davidxie.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.davidxie.dotify.databinding.ActivitySongListBinding

class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding) {

            title = "All Songs"

        }
    }
}