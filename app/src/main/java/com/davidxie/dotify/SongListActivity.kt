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
    private var isSongSelected: Boolean = false
    private lateinit var selectedSong: Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // we're re-creating this activity
            with(savedInstanceState) {
                isSongSelected = getBoolean(IS_MINI_PLAYER_SHOWN_KEY, false)

                // Only read the selected song content if a song is selected
                if(isSongSelected) {
                    var selectedSongTemp = getParcelable<Song>(SELECTED_SONG_KEY)
                    if (selectedSongTemp == null) {
                        // if songObject is not passed in to this savedInstanceState, use the first song from SongDataProvider
                        selectedSongTemp=SongDataProvider.getAllSongs()[0]
                        Toast.makeText(this@SongListActivity,"Null Song Object detected", Toast.LENGTH_SHORT).show()
                    }
                    selectedSong = selectedSongTemp
                } else {
                    Toast.makeText(this@SongListActivity,"Song is not selected yet", Toast.LENGTH_SHORT).show()
                }
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

                selectedSong = song;
                songPreviewText.text = "${selectedSong.title} - ${selectedSong.artist}"
                isSongSelected = true
            }

            // Note: this is only used when we're restoring this Activity (will not be called when onSongClickListener is clicked at first time)
            if(isSongSelected) {
                // check whether a lateinit var is initialized
                if(!this@SongListActivity::selectedSong.isInitialized) {
                    Toast.makeText(this@SongListActivity,"Null Song Object detected", Toast.LENGTH_SHORT).show()
                } else {
                    //Toast.makeText(this@SongListActivity,"Selected 1 song", Toast.LENGTH_SHORT).show()
                    songSnippetBar.visibility = View.VISIBLE
                    songPreviewText.text = "${selectedSong.title} - ${selectedSong.artist}"
                }
            } else {
                songSnippetBar.visibility = View.GONE
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_MINI_PLAYER_SHOWN_KEY, isSongSelected)
        if(isSongSelected) {
            outState.putParcelable(SELECTED_SONG_KEY, selectedSong)
        } else {
            //Toast.makeText(this@SongListActivity,"Song is not selected yet", Toast.LENGTH_SHORT).show()
        }
        super.onSaveInstanceState(outState)
    }
}