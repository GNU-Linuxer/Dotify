package com.davidxie.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import com.davidxie.dotify.R
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.davidxie.dotify.databinding.FragmentStatisticsBinding
import com.davidxie.dotify.model.Song

class StatisticsFragment : Fragment() {

    private val safeArgs:StatisticsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding= FragmentStatisticsBinding.inflate(inflater)

        val songObject: Song = safeArgs.songObject
        val playCount: Int = safeArgs.songPlayCount

        with(binding){
            ivSongPic.load(songObject.largeImageURL)

            tvPlayCount.text = "Play Count: ${playCount.toString()}"
            tvSongTitle.text = songObject.title

        }

        return binding.root
    }
}