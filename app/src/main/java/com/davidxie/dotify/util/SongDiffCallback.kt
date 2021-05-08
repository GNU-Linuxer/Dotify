package com.davidxie.dotify.util

import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song

class SongDiffCallback(private val newSongList: List<Song>, private val oldSongList: List<Song>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldSongList.size
    }

    override fun getNewListSize(): Int {
        return newSongList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newSong = newSongList[newItemPosition]
        val oldSong = oldSongList[oldItemPosition]

        // If the id of 2 Song objects is same, then we consider 2 Song objects is same
        return newSong.id == oldSong.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newSong = newSongList[newItemPosition]
        val oldSong = oldSongList[oldItemPosition]

        // Special fade-in/fade-out
        return newSong.artist == oldSong.artist && newSong.title == oldSong.title
    }
}