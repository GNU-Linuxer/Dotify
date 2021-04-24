package com.davidxie.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.davidxie.dotify.databinding.FragmentStatisticsBinding
import com.ericchee.songdataprovider.Song

class StatisticsFragment : Fragment() {

    private val safeArgs:StatisticsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentStatisticsBinding.inflate(inflater)

        val songObject: Song = safeArgs.songObject
        val playCount: Int = safeArgs.songPlayCount

        with(binding){
            ivSongPic.setImageResource(songObject.largeImageID)

            tvPlayCount.text = "Play Count: ${playCount.toString()}"
            tvSongTitle.text = songObject.title

        }

        return binding.root
    }
}