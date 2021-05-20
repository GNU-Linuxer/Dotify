package com.davidxie.dotify

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.davidxie.dotify.model.Song
import com.davidxie.dotify.repository.DataRepository
import com.davidxie.dotify.util.UpdateNewSongManager

const val DOTIFY_APP_PREFS_KEY = "Dotify App Preferences"
const val NOTIFICATIONS_ENABLED_PREF_KEY = "notifications_enabled"

class DotifyApplication : Application() {
    lateinit var dataRepository: DataRepository
    var selectedSong : Song? = null
    lateinit var preferences: SharedPreferences
    // this work manager should not be restricted to a single activity, so hold it in the application object
    lateinit var updateNewSongManager: UpdateNewSongManager

    override fun onCreate() {
        super.onCreate()
        dataRepository=DataRepository()

        this.updateNewSongManager = UpdateNewSongManager(this)
        this.preferences = getSharedPreferences(DOTIFY_APP_PREFS_KEY, Context.MODE_PRIVATE)

        // If the user previously turned on notification, it should automatically fetch song updates
        if(preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, false)){
            //Toast.makeText(this, "Notifications enabled", Toast.LENGTH_SHORT).show()

            // Publish notification immediately after 5 seconds
            //updateNewSongManager.updateNewSong()
            // Publish notification every 20 seconds
            updateNewSongManager.updateNewSongPeriodic()
        }

        //Log.i("Dotify App", "App has booted")
    }
}