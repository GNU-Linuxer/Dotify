package com.davidxie.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.davidxie.dotify.databinding.ActivitySettingsBinding
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

fun navigateToSettingsActivity(context: Context, song: Song, playCount: Int) = with(context){
    val intent = Intent(this, SettingsActivity::class.java).apply {
        val bundle = Bundle().apply {
            // Pack data inside this bundle
            putParcelable(SONG_KEY, song)
            putInt(PLAY_COUNT_KEY, playCount)
        }
        putExtras(bundle)
    }
    startActivity(intent)
}

class SettingsActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.navHost) }
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding){ // TIP: if I set view binding, I should not use the setContentView(R.layout.activity_settings) (that will create the View twice, which will crash the app)
            // Programmatically set the nav graph
            navController.setGraph(R.navigation.nav_graph, intent.extras)
        }


        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }
}