package com.davidxie.dotify.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.davidxie.dotify.databinding.OneSongLayoutBinding
import com.davidxie.dotify.model.Song

class SongListAdapter(private var songList: List<Song>): RecyclerView.Adapter<SongListAdapter.SongListHolder>() {

    var onSongClickListener: (position: Int, song: Song) -> Unit = { _, _ -> }
    var onSongLongClickListener: (position: Int, song: Song) -> Unit = { _, _ -> }

    // Create a bunch of view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListHolder {
        // Create a binding on One Song Lauout
        val binding = OneSongLayoutBinding.inflate(LayoutInflater.from(parent.context))

        return SongListHolder(binding)
    }

    // On One Row: take the position number, load data, and populate view
    override fun onBindViewHolder(holder: SongListHolder, position: Int) {
        val song = songList[position]

        with(holder.binding) {
            tvSongText.text = song.title
            tvArtistText.text = song.artist
            ivSongPic.load(song.smallImageURL)

            root.setOnClickListener{
                onSongClickListener(position, song)
            }

            root.setOnLongClickListener {
                onSongLongClickListener(position, song)
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    fun updateSong(newSongList: List<Song>) {
        val callback = SongDiffCallback(newSongList, songList)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)

        // Change the list's content after dispatch update
        this.songList = newSongList
    }

    // Create a View Holder Class (responsible to display 1 row of song in the Recycler View List)
    class SongListHolder(val binding: OneSongLayoutBinding): RecyclerView.ViewHolder(binding.root)
}