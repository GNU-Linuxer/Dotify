package com.davidxie.dotify.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.davidxie.dotify.DotifyApplication
import com.davidxie.dotify.R
import com.davidxie.dotify.activity.PlayerActivity
import com.davidxie.dotify.activity.SONG_KEY
import com.davidxie.dotify.model.Song
import kotlin.random.Random

private const val NEW_SONG_CHANNEL_ID = "NEW_SONG_CHANNEL_ID"

class FetchNewSongWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    private val DotifyApp by lazy { context.applicationContext as DotifyApplication }

    override suspend fun doWork(): Result {
        // Initialize all channels
        initNotificationChannels()
        Log.i("FetchNewSongWorker", "updating new songs now")

        // Pick a random song from this dataRepository and then send to notification
        val allSong = DotifyApp.dataRepository.getSongList()
        val songs: List<Song> = allSong.songs
        val randomSong: Song = songs.random()

        publishNewSongNotification(randomSong)

        return Result.success()
    }

    private val notificationManager = NotificationManagerCompat.from(context)

    private fun publishNewSongNotification(selectedSong: Song) {
        // Define the intent or action you want when user taps on notification
        val intent = Intent(context, PlayerActivity::class.java).apply { // launch PlayerActivity; note: the selected Song is stored in the DotifyApplication and therefore no need to pass intent
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(SONG_KEY, selectedSong)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT) // dont forget to add PendingIntent.FLAG_UPDATE_CURRENT to send data over


        // Build information you want the notification to show
        val builder = NotificationCompat.Builder(context, NEW_SONG_CHANNEL_ID)    // channel id from creating the channel
            .setSmallIcon(R.drawable.ic_song_foreground)
            .setContentTitle("${selectedSong.artist} just released a new song!!!")
            .setContentText("Listen to ${selectedSong.title} now on Dotify")
            .setContentIntent(pendingIntent)    // sets the action when user clicks on notification
            .setAutoCancel(true)    // This will dismiss the notification tap
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Tell the OS to publish the notification using the info
        with(notificationManager) {
            // notificationId is a unique int for each notification that you must define
            val notificationId = Random.nextInt()
            notify(notificationId, builder.build())
        }
    }

    private fun initNotificationChannels() {
        initNewSongChannel()
    }

    private fun initNewSongChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Info about the channel
            val name = "New Uploaded Music"
            val descriptionText = "This Notification will show notification of new updated music"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            // Create channel object
            val channel = NotificationChannel(NEW_SONG_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Tell the Android OS to create a channel
            notificationManager.createNotificationChannel(channel)
        }
    }
}
