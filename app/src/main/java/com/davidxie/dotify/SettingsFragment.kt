package com.davidxie.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.davidxie.dotify.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)

        with(binding) {
            profileButton.setOnClickListener{
                Toast.makeText(requireContext(), "Profile button is clicked", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}