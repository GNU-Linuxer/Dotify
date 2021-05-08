package com.davidxie.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.davidxie.dotify.databinding.FragmentAboutBinding
import com.davidxie.dotify.BuildConfig;

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAboutBinding.inflate(inflater)
        with(binding){
            //Toast.makeText(requireContext(), , Toast.LENGTH_SHORT).show()
            tvVersion.text = "Version ${BuildConfig.VERSION_NAME}"
        }
        return binding.root
    }
}