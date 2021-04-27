package com.davidxie.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.davidxie.dotify.databinding.FragmentSettingsBinding
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class SettingsFragment : Fragment() {

    private val navController by lazy{findNavController()}
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
        }

        return binding.root
    }
}