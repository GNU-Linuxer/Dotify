package com.davidxie.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.davidxie.dotify.databinding.ActivitySongListBinding
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding) {
            title = "All Songs"
            // SongDataProvider.getAllSongs() will return a a list of Songs
            val songs = SongDataProvider.getAllSongs()

            // Set Adapter to Recycler View with data
            val adapter = SongListAdapter(songs)
            rvSongList.adapter = adapter;

            // Handle code when clicking one song (from the list)
            adapter.onSongClickListener = {position: Int, song: Song ->

                Toast.makeText(this@SongListActivity, "Clicked on $position with song ${song.title}.", Toast.LENGTH_SHORT).show()
                // We want to launch the PlayerActivity

            }

            shuffleButton.setOnClickListener {
                // On Refresh Click, update the list
                adapter.updateSong(songs.toMutableList().shuffled())
            }
        }
    }
}