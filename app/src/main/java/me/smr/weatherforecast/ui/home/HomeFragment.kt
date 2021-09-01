package me.smr.weatherforecast.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.smr.weatherforecast.R
import me.smr.weatherforecast.databinding.HomeFragmentBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.btnAddCity.setOnClickListener {
            findNavController().navigate(R.id.action_add_city)
        }
        requireActivity().setTitle(R.string.app_name)
        viewModel.data.asLiveData().observe(viewLifecycleOwner, {
            // TODO send data to recyclerview
        })
        return binding.root
    }

}