package com.davidxie.dotify.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.*
import java.util.concurrent.TimeUnit

private const val FETCH_SONG_WORK_TAG = "FETCH_SONG_WORK_TAG"

class UpdateNewSongManager(context: Context) {

    private val workManager: WorkManager = WorkManager.getInstance(context)

    fun updateNewSong() {
        //Log.i("UpdateNewSongManager", "called one-time refreshing")
        val request = OneTimeWorkRequestBuilder<UpdateNewSongWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(FETCH_SONG_WORK_TAG)
            .build()

        workManager.enqueue(request)
    }

    fun updateNewSongPeriodic() {
        //Log.i("UpdateNewSongManager", "called periodic refreshing")
        // Will not start this periodic worker again if it's already started
        if (isFetchingNewSong()) {
            return
        }
        val request = PeriodicWorkRequestBuilder<UpdateNewSongWorker>(20, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(FETCH_SONG_WORK_TAG)
            .build()

        workManager.enqueue(request)

    }

    fun updateNewSongPeriodicLong() {
        // Will not start this periodic worker again if it's already started
        if (isFetchingNewSong()) {
            return
        }

        val request = PeriodicWorkRequestBuilder<UpdateNewSongWorker>(2, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    // The battery can't be too low in order for this requirement to run
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .addTag(FETCH_SONG_WORK_TAG)
            .build()

        workManager.enqueue(request)

    }

    fun stopPeriodicallyRefreshing() {
        workManager.cancelAllWorkByTag(FETCH_SONG_WORK_TAG)
    }

    private fun isFetchingNewSong(): Boolean {
        return workManager.getWorkInfosByTag(FETCH_SONG_WORK_TAG).get().any {
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }
}
