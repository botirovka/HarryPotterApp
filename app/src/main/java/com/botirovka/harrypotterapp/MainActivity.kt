package com.botirovka.harrypotterapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.botirovka.harrypotterapp.data.database.HarryPotterDatabase
import com.botirovka.harrypotterapp.data.datastore.DataStoreManager
import com.botirovka.harrypotterapp.data.network.NetworkUtils
import com.botirovka.harrypotterapp.data.repository.HarryPotterRepository
import com.botirovka.harrypotterapp.databinding.ActivityMainBinding
import com.botirovka.harrypotterapp.ui.bottomnav.characters.CharactersFragment
import com.botirovka.harrypotterapp.ui.bottomnav.houses.HousesFragment
import com.botirovka.harrypotterapp.ui.bottomnav.spells.SpellsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var repository: HarryPotterRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)
        repository = HarryPotterRepository(HarryPotterDatabase.getDatabase(this))
        if(dataStoreManager.isFirstLaunch()) {
            initializeDatabase()
        }
        replaceFragment(HousesFragment())
        setupBottomNavigation()



        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupBottomNavigation() {
        bottomNav = binding.bottomNav
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.houses -> replaceFragment(HousesFragment())
                R.id.characters -> replaceFragment(CharactersFragment())
                R.id.spells -> replaceFragment(SpellsFragment())
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        return true
    }

    private fun initializeDatabase() {

        lifecycleScope.launch {
            if (NetworkUtils.isInternetAvailable(this@MainActivity)) {
                try {
                    repository.fetchCharactersFromApiAndSave()
                    repository.fetchSpellsFromApiAndSave()
                    val characters = repository.getAllCharacters()
                    val spells = repository.getAllSpells()

                    if (characters.isNotEmpty() && spells.isNotEmpty()) {
                        dataStoreManager.markFirstLaunchComplete()
                    }
                } catch (e: Exception) {
                    Log.e("Database", "Error during initialization: ${e.message}")
                    Toast.makeText(baseContext,
                        e.message,
                        Toast.LENGTH_LONG)
                        .show()
                }
            }
            else{
                Toast.makeText(baseContext,
                    "Please turn on your internet connection and restart the application",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }

    }
}