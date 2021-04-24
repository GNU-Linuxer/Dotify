package com.davidxie.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.davidxie.dotify.databinding.ActivitySongListBinding
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

const val IS_MINI_PLAYER_SHOWN_KEY = "is mini player shown"
const val SELECTED_SONG_KEY = "selected song in list"

class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding
    private var selectedSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // we're re-creating this activity
            with(savedInstanceState) {
                // Only read the selected song content if a song is selected
                selectedSong = getParcelable(SELECTED_SONG_KEY)
            }
        }

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
                this@SongListActivity.selectedSong = song;
                songPreviewText.text = "${song.title} - ${song.artist}"
            }

            selectedSong?.let { nonNullSelectedSong ->
                // Note: two statements below won't be executed if selectedSong is null
                songSnippetBar.visibility = View.VISIBLE
                songPreviewText.text = "${nonNullSelectedSong.title} - ${nonNullSelectedSong.artist}"
            }

            shuffleButton.setOnClickListener {
                // On Refresh Click, update the list
                adapter.updateSong(songs.shuffled())
            }

            songSnippetBar.setOnClickListener {
                // if selectedSong is null, immediately end this callback function and not navigate to PlayerActivity
                val selectedSong: Song = this@SongListActivity.selectedSong ?: return@setOnClickListener
                navigateToPlayerActivity(this@SongListActivity, selectedSong)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // if selectedSong is null, we would not continue the SaveInstanceState
        val selectedSong: Song = this.selectedSong ?: return
        outState.putParcelable(SELECTED_SONG_KEY, selectedSong)
        super.onSaveInstanceState(outState)
    }
}