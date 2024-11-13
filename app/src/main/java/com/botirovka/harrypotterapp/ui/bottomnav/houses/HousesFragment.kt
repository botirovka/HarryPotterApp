package com.botirovka.harrypotterapp.ui.bottomnav.houses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.botirovka.harrypotterapp.R
import com.botirovka.harrypotterapp.databinding.FragmentHousesBinding
import com.botirovka.harrypotterapp.ui.bottomnav.characters.CharactersFragment


class HousesFragment : Fragment() {
    private lateinit var binding: FragmentHousesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHousesBinding.inflate(inflater, container, false)

        binding.buttonGryffindor.setOnClickListener {
            openCharacterFragment("Gryffindor")
        }

        binding.buttonSlytherin.setOnClickListener {
            openCharacterFragment("Slytherin")
        }

        binding.buttonHufflepuff.setOnClickListener {
            openCharacterFragment("Hufflepuff")
        }

        binding.buttonRavenclaw.setOnClickListener {
            openCharacterFragment("Ravenclaw")
        }

        return binding.root
    }

    private fun openCharacterFragment(house: String) {
        val fragment = CharactersFragment.newInstance(house)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}