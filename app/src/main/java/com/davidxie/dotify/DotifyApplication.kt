package com.davidxie.dotify

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.davidxie.dotify.model.Song
import com.davidxie.dotify.repository.DataRepository
import com.davidxie.dotify.util.FetchNewSongManager

const val DOTIFY_APP_PREFS_KEY = "Dotify App Preferences"
const val NOTIFICATIONS_ENABLED_PREF_KEY = "notifications_enabled"

class DotifyApplication : Application() {
    lateinit var dataRepository: DataRepository
    var selectedSong : Song? = null
    lateinit var preferences: SharedPreferences
    lateinit var fetchNewSongManager: FetchNewSongManager

    override fun onCreate() {
        super.onCreate()
        dataRepository=DataRepository()

        this.fetchNewSongManager = FetchNewSongManager(this)
        this.preferences = getSharedPreferences(DOTIFY_APP_PREFS_KEY, Context.MODE_PRIVATE)

        // If the user previously turned on notification, it should automatically fetch song updates
        if(preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, false)){
            Toast.makeText(this, "Notifications enabled", Toast.LENGTH_SHORT).show()
            fetchNewSongManager.updateNewSong()
        }

        //Log.i("Dotify App", "App has booted")
    }
}