package com.davidxie.dotify

import android.app.Application
import android.util.Log
import com.davidxie.dotify.model.Song
import com.davidxie.dotify.repository.DataRepository

class DotifyApplication : Application() {
    lateinit var dataRepository: DataRepository
    var selectedSong : Song? = null

    override fun onCreate() {
        super.onCreate()
        dataRepository=DataRepository()
        //Log.i("Dotify App", "App has booted")
    }
}