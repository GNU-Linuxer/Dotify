package com.davidxie.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.davidxie.dotify.databinding.ActivitySongListBinding
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding
    private var isSongSelected: Boolean = false
    private lateinit var selectedSong: Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        // SongDataProvider.getAllSongs() will return a a list of Song Objects
        val songs = SongDataProvider.getAllSongs().toMutableList()

        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding) {
            title = "All Songs"

            // Set Adapter to Recycler View with data
            val adapter = SongListAdapter(songs)
            rvSongList.adapter = adapter;

            // Handle code when clicking one song (from the list)
            adapter.onSongClickListener = {position: Int, song: Song ->
                //Toast.makeText(this@SongListActivity, "Clicked on $position with song ${song.title}.", Toast.LENGTH_SHORT).show()
                songSnippetBar.visibility = View.VISIBLE

                songPreviewText.text = "${song.title} - ${song.artist}"
                selectedSong = song;
                isSongSelected = true
            }

            shuffleButton.setOnClickListener {
                // On Refresh Click, update the list
                adapter.updateSong(songs.shuffled())
            }

            songSnippetBar.setOnClickListener {
                if (isSongSelected) {
                    navigateToPlayerActivity(this@SongListActivity, selectedSong)
                } else {
                    Toast.makeText(this@SongListActivity, "Please tap a song from this list to select a song.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}