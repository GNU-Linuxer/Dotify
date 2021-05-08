package com.davidxie.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.davidxie.dotify.DotifyApplication
import com.davidxie.dotify.R
import com.davidxie.dotify.databinding.ActivitySongListBinding
import com.davidxie.dotify.util.SongListAdapter
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the Dotify Application
        val DotifyApp = (application as DotifyApplication)

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
            adapter.onSongClickListener = { _: Int, song: Song ->
                //Toast.makeText(this@SongListActivity, "Clicked on $position with song ${song.title}.", Toast.LENGTH_SHORT).show()
                songSnippetBar.visibility = View.VISIBLE
                DotifyApp.selectedSong = song;
                songPreviewText.text = "${song.title} - ${song.artist}"
            }

            DotifyApp.selectedSong?.let { nonNullSelectedSong ->
                // Note: two statements below won't be executed if selectedSong is null
                songSnippetBar.visibility = View.VISIBLE
                songPreviewText.text = "${nonNullSelectedSong.title} - ${nonNullSelectedSong.artist}"
            }

            shuffleButton.setOnClickListener {
                // On Refresh Click, update the list
                adapter.updateSong(songs.shuffled())
            }

            songSnippetBar.setOnClickListener {
                //Toast.makeText(this@SongListActivity, DotifyApp.selectedSong.toString(), Toast.LENGTH_SHORT).show()

                // if selectedSong is null, immediately end this callback function and not navigate to PlayerActivity
                val selectedSong: Song = DotifyApp.selectedSong ?: return@setOnClickListener

                if(selectedSong == null) {
                    Toast.makeText(this@SongListActivity, "Song is not selected yet", Toast.LENGTH_SHORT).show()
                } else {
                    navigateToPlayerActivity(this@SongListActivity)
                }
            }
        }
    }
}