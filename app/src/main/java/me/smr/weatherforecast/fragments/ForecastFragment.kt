package me.smr.weatherforecast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.smr.weatherforecast.adapters.ForecastAdapter
import me.smr.weatherforecast.databinding.ForecastFragBinding
import me.smr.weatherforecast.models.CityData
import me.smr.weatherforecast.utils.Result
import me.smr.weatherforecast.viewmodels.ForecastViewModel

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private lateinit var binding: ForecastFragBinding
    private val vm: ForecastViewModel by viewModels()
    private val safeArgs: ForecastFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ForecastFragBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = vm

        requireActivity().title = safeArgs.argCityName
        val adapter = ForecastAdapter()

        binding.lvForecast.adapter = adapter

        vm.data.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    Snackbar.make(
                        binding.root,
                        "ERR: ${result.error.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is Result.Value -> {
                    adapter.submitList(result.value)
                }
                Result.Loading -> {
                    // do nothing
                }
            }
        }

        return binding.root
    }


    companion object {

        const val ARG_CITY_ID = "argCityID"

        fun newInstance(data: CityData?): ForecastFragment {
            val fragment = ForecastFragment()
            val extras = Bundle()
            extras.putParcelable(ARG_CITY_ID, data)
            fragment.arguments = extras
            return fragment
        }
    }
}