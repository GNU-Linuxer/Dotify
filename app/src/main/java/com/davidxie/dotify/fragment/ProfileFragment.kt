package com.davidxie.dotify.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.davidxie.dotify.DotifyApplication
import com.davidxie.dotify.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private val myApp: DotifyApplication by lazy { requireActivity().application as DotifyApplication }
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        loadUserData()

        binding.pullDownContainer.setOnRefreshListener {
            loadUserData()
        }

        return binding.root
    }

    private fun loadUserData() {

        lifecycleScope.launch{
            kotlin.runCatching {
                val userInfo = myApp.dataRepository.getUser()
                //Toast.makeText(requireContext(), userInfo.toString(), Toast.LENGTH_SHORT).show()

                // Hide loading text and error text
                // The tvLoading is set to visible in the layout .xml file
                binding.tvLoading.visibility = View.GONE
                binding.ivError.visibility = View.GONE
                binding.tvError.visibility = View.GONE

                // Populate the data from the github repository
                binding.ivMoviePic.visibility = View.VISIBLE
                binding.ivMoviePic.load(userInfo.profilePicURL)

                binding.tvPersonName.visibility = View.VISIBLE
                binding.tvPersonName.text = "${userInfo.firstName} ${userInfo.lastName}"

                binding.tvUsername.visibility = View.VISIBLE
                binding.tvUsername.text = "Username: ${userInfo.username}"

                binding.tvPlatform.visibility = View.VISIBLE
                binding.tvPlatform.text = "Platform: ${userInfo.platform}"

                binding.tvHasNose.visibility = View.VISIBLE
                binding.tvHasNose.text = "Has Nose: ${userInfo.hasNose}"
            }.onFailure {
                // Hide loading text
                binding.tvLoading.visibility = View.GONE

                // Show error text
                binding.ivError.visibility = View.VISIBLE
                binding.tvError.visibility = View.VISIBLE

                // Hide actual content
                binding.ivMoviePic.visibility = View.GONE
                binding.tvPersonName.visibility = View.GONE
                binding.tvUsername.visibility = View.GONE
                binding.tvPlatform.visibility = View.GONE
                binding.tvHasNose.visibility = View.GONE
            }
            // Stop pull down to refresh animation
            binding.pullDownContainer.isRefreshing = false
        }
    }
}