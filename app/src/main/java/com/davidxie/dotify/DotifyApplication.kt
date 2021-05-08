package com.davidxie.dotify

import android.app.Application
import android.util.Log
import com.ericchee.songdataprovider.Song

class DotifyApplication : Application() {

    var selectedSong : Song? = null

    override fun onCreate() {
        super.onCreate()

        Log.i("Dotify App", "App has booted")
    }
}