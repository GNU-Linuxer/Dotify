package com.davidxie.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davidxie.dotify.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding= FragmentStatisticsBinding.inflate(inflater)
        return binding.root
    }
}