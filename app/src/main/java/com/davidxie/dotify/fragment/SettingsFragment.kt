package com.davidxie.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.davidxie.dotify.DotifyApplication
import com.davidxie.dotify.NOTIFICATIONS_ENABLED_PREF_KEY
import com.davidxie.dotify.R
import com.davidxie.dotify.databinding.FragmentSettingsBinding
import com.davidxie.dotify.util.UpdateNewSongManager




class SettingsFragment : Fragment() {

    private val navController by lazy{findNavController()}
    private val dotifyApp by lazy { requireActivity().application as DotifyApplication }
    private val updateNewSongManager: UpdateNewSongManager by lazy { dotifyApp.updateNewSongManager }
    private val preferences by lazy { dotifyApp.preferences }
    private val safeArgs: SettingsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsBinding.inflate(inflater)

        // if songObject is not passed in to this fragment, the first song will be use, with a playcount of -1
        //val demoSong: Song = SongDataProvider.getAllSongs()[0]

        with(binding) {
            profileButton.setOnClickListener{
                //Toast.makeText(requireContext(), "Profile button is clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.profileFragment)
            }
            statisticsButton.setOnClickListener{
                //Toast.makeText(requireContext(), "Statistics button is clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(SettingsFragmentDirections.actionGlobalStatisticsFragment(safeArgs.songObject, safeArgs.songPlayCount))
            }
            aboutButton.setOnClickListener{
                //Toast.makeText(requireContext(), "About button is clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.aboutFragment)
            }

            refreshButton.isChecked = preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, false)

            refreshButton.setOnCheckedChangeListener { _, isChecked ->
                // Saving the value in preferences whether the switch is on or not
                preferences.edit {
                    putBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, isChecked)
                }
                if (isChecked) {
                    //Toast.makeText(requireContext(), "Notifications enabled", Toast.LENGTH_SHORT).show()

                    // Publish notification immediately after 5 seconds (note that DotifyApplication will also have this code that handles when user has previously turned on notification)
                    //updateNewSongManager.updateNewSong()
                    // Publish notification every 20 seconds
                    updateNewSongManager.updateNewSongPeriodic()
                } else {
                    //Toast.makeText(requireContext(), "Notifications are turned off", Toast.LENGTH_SHORT).show()
                    updateNewSongManager.stopPeriodicallyRefreshing()
                }
            }
        }

        return binding.root
    }
}