package com.project.androidbingo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.project.androidbingo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Dodaj obserwatora do NavController oraz wymuszenie ponownego tworzenia menu po zmianie fragmentu
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isRegisterFragmentVisible = destination.id == R.id.registerFragment
            if (isRegisterFragmentVisible) {
                binding.fab.hide()
            } else {
                binding.fab.show()
            }
            invalidateOptionsMenu()
        }

        // Czy użytkownik jest zarejestrowany
        val sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        val isRegistered = sharedPreferences.getBoolean("isRegistered", false)

        // Przekierowanie do RegisterFragment jeśli nie jest zarejestrowany
        if (!isRegistered) {
            navController.navigate(R.id.registerFragment)
        }

        // Button ikona poczty
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // Pobieramy navcontroller, sprawdzamy czty aktualny fragment to register fragment i finalnie ukrywyamy przycisk moj profil jesli jesteśmy w registerfragmennt
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val isRegisterFragmentVisible = navController.currentDestination?.id == R.id.registerFragment
        val profileMenuItem = menu?.findItem(R.id.action_profile)
        profileMenuItem?.isVisible = !isRegisterFragmentVisible

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                // Nawiguj do ProfileFragment
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.profileFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}