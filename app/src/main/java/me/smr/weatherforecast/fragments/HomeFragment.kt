package me.smr.weatherforecast.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import me.smr.weatherforecast.R
import me.smr.weatherforecast.adapters.SearchClickListener
import me.smr.weatherforecast.adapters.SearchResultAdapter
import me.smr.weatherforecast.adapters.WeatherAdapter
import me.smr.weatherforecast.databinding.HomeFragmentBinding
import me.smr.weatherforecast.models.CitySearchResult
import me.smr.weatherforecast.viewmodels.HomeViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var searchMenu:MenuItem
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        val searchAdapter = SearchResultAdapter(object : SearchClickListener {
            override fun onSearchItemClicked(item: CitySearchResult) {
                viewModel.onSearchResultClicked(item)
                searchMenu.collapseActionView()
            }
        })

        binding.lvSearch.adapter = searchAdapter

        val weatherAdapter = WeatherAdapter()

        binding.lvCities.adapter = weatherAdapter

        viewModel.searchResult.observe(viewLifecycleOwner) {
            searchAdapter.submitList(it)
        }

        viewModel.weatherData.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it)
        }

        requireActivity().setTitle(R.string.app_name)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home, menu)
        // Associate searchable configuration with the SearchView
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchMenu = menu.findItem(R.id.search)

        val searchView = searchMenu.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        val listener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                viewModel.showSearch()
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                viewModel.hideSearch()
                return true
            }
        }

        searchMenu.setOnActionExpandListener(listener)

    }

    companion object {
        const val TAG = "HomeFragment"
    }

}