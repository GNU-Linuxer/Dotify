package com.davidxie.dotify

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.davidxie.dotify.model.Song
import com.davidxie.dotify.repository.DataRepository
import com.davidxie.dotify.util.FetchNewSongManager
import com.davidxie.dotify.util.SongNotificationManager

const val DOTIFY_APP_PREFS_KEY = "Dotify App Preferences"

class DotifyApplication : Application() {
    lateinit var dataRepository: DataRepository
    var selectedSong : Song? = null
    lateinit var preferences: SharedPreferences
    lateinit var fetchNewSongManager: FetchNewSongManager
    lateinit var notificationManager: SongNotificationManager

    override fun onCreate() {
        super.onCreate()
        dataRepository=DataRepository()

        this.fetchNewSongManager = FetchNewSongManager(this)
        this.notificationManager = SongNotificationManager(this)
        this.preferences = getSharedPreferences(DOTIFY_APP_PREFS_KEY, Context.MODE_PRIVATE)
        //Log.i("Dotify App", "App has booted")
    }
}