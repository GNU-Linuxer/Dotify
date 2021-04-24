package com.davidxie.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.davidxie.dotify.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private val navController by lazy{findNavController()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)

        with(binding) {
            profileButton.setOnClickListener{
                Toast.makeText(requireContext(), "Profile button is clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.profileFragment)
            }
            statisticsButton.setOnClickListener{
                Toast.makeText(requireContext(), "Statistics button is clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.statisticsFragment)
            }
            aboutButton.setOnClickListener{
                Toast.makeText(requireContext(), "About button is clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.aboutFragment)
            }
        }

        return binding.root
    }
}