package com.davidxie.dotify.util

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.davidxie.dotify.DotifyApplication
import com.davidxie.dotify.model.Song

class FetchNewSongWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    private val DotifyApp by lazy { context.applicationContext as DotifyApplication }
    private val songNotificationManager by lazy { DotifyApp.notificationManager }

    override suspend fun doWork(): Result {

        //Log.i("EmailSyncWorker", "syncing emails now")

        // Pick a random song from this dataRepository and then send to notification
        val allSong = DotifyApp.dataRepository.getSongList()
        val songs: List<Song> = allSong.songs
        val randomSong: Song = songs.random()

        songNotificationManager.publishNewSongNotification(randomSong)

        return Result.success()
    }


}
