package com.davidxie.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song

fun navigateToSettingsActivity(context: Context, song: Song, playCount: Int) = with(context){
    val intent = Intent(this, SettingsActivity::class.java).apply {
        val bundle = Bundle().apply {
            // Pack data inside this bundle
            putParcelable(SONG_KEY, song)
        }
        putExtras(bundle)
    }
    startActivity(intent)
}

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}