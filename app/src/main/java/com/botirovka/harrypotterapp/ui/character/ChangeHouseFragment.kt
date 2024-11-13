package com.botirovka.harrypotterapp.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.botirovka.harrypotterapp.data.database.HarryPotterDatabase
import com.botirovka.harrypotterapp.data.models.CharacterModel
import com.botirovka.harrypotterapp.data.repository.HarryPotterRepository
import com.botirovka.harrypotterapp.databinding.FragmentChangeHouseBinding
import kotlinx.coroutines.launch


class ChangeHouseFragment : Fragment() {

    private lateinit var binding: FragmentChangeHouseBinding
    private var character: CharacterModel? = null
    private lateinit var repository: HarryPotterRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            character = it.getParcelable(ARG_CHARACTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangeHouseBinding.inflate(inflater, container, false)
        repository = HarryPotterRepository(HarryPotterDatabase.getDatabase(requireContext()))

        character?.let { character ->
            binding.changeHouseCharacterNameTv.text = character.name
            binding.changeHouseCurrentHouseTv.text = character.house

            binding.buttonChangeHouseGryffindor.setOnClickListener {
                changeHouse(character, "Gryffindor")
            }
            binding.buttonChangeHouseSlytherin.setOnClickListener {
                changeHouse(character, "Slytherin")
            }
            binding.buttonChangeHouseHufflepuff.setOnClickListener {
                changeHouse(character, "Hufflepuff")
            }
            binding.buttonChangeHouseRavenclaw.setOnClickListener {
                changeHouse(character, "Ravenclaw")
            }
            binding.buttonBackChangeHouse.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }


        return binding.root
    }

    private fun changeHouse(character: CharacterModel, house: String) {
        val newCharacter = character
        newCharacter.house = house
        lifecycleScope.launch {
            repository.updateCharacter(newCharacter)
            Toast.makeText(requireContext(),"House Successfully changed", Toast.LENGTH_SHORT).show()
            binding.changeHouseCurrentHouseTv.text = house
        }
    }

    companion object {
        private const val ARG_CHARACTER = "character"

        fun newInstance(character: CharacterModel) =
            ChangeHouseFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, character)
                }
            }
    }
}