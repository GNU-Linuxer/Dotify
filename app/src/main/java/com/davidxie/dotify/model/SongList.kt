package com.davidxie.dotify.model
import com.davidxie.dotify.model.Song

data class SongList(
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>,
)
