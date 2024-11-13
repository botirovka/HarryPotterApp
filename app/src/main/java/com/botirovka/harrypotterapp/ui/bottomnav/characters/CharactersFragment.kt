package com.botirovka.harrypotterapp.ui.bottomnav.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.botirovka.harrypotterapp.R
import com.botirovka.harrypotterapp.data.database.HarryPotterDatabase
import com.botirovka.harrypotterapp.data.models.CharacterModel
import com.botirovka.harrypotterapp.data.repository.HarryPotterRepository
import com.botirovka.harrypotterapp.databinding.FragmentCharactersBinding
import com.botirovka.harrypotterapp.ui.character.CharacterDetailsFragment
import kotlinx.coroutines.launch


class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var repository: HarryPotterRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterAdapter
    private var house: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            house = it.getString(ARG_HOUSE).orEmpty()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        repository = HarryPotterRepository(HarryPotterDatabase.getDatabase(requireContext()))

        getAllCharactersFromDB()
        return binding.root
    }

    private fun getAllCharactersFromDB() {
        recyclerView = binding.charactersRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        lifecycleScope.launch {
            val characters = if (house.isEmpty()) {
                repository.getAllCharacters()
            } else {
                repository.getAllCharacters(house)
            }
            adapter = CharacterAdapter(characters){ character ->
                showCharacterDetails(character)
        }
            recyclerView.adapter = adapter
    }


}
    private fun showCharacterDetails(character: CharacterModel) {
        val detailsFragment = CharacterDetailsFragment.newInstance(character)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val ARG_HOUSE = "house"

        fun newInstance(house: String) =
            CharactersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_HOUSE, house)
                }
            }
    }
}

