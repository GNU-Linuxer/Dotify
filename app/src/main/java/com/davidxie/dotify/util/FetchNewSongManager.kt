package com.davidxie.dotify.util

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

private const val FETCH_SONG_WORK_TAG = "FETCH_SONG_WORK_TAG"

class FetchNewSongManager(context: Context) {

    private val workManager: WorkManager = WorkManager.getInstance(context)

    fun updateNewSong() {

        val request = OneTimeWorkRequestBuilder<FetchNewSongWorker>()
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

    fun updateNewSongPeriodic() {
        // Will not start this periodic worker again if it's already started
        if (isFetchingNewSong()) {
            return
        }

        val request = PeriodicWorkRequestBuilder<FetchNewSongWorker>(20, TimeUnit.MINUTES)
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
