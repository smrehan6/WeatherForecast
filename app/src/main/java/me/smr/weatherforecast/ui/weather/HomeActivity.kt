package me.smr.weatherforecast.ui.weather

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import me.smr.weatherforecast.R
import me.smr.weatherforecast.viewmodels.HomeViewModel

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        setupToolbar()
        handleIntent(intent)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(toolbar)
    }

    override fun onNewIntent(intent: Intent) {
        Log.i(TAG, "onNewIntent: ")
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
            Log.i(TAG, "search: $query")
            homeViewModel.searchCity(query ?: "")
        }
    }

    companion object {
        const val TAG = "HomeActivity"
    }

}