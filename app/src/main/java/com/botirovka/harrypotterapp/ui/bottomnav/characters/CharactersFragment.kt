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
import kotlinx.coroutines.launch


class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var repository: HarryPotterRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterAdapter

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
            val characters = repository.getAllCharacters()
            adapter = CharacterAdapter(characters){ character ->
                showCharacterDetails(character)
        }
            recyclerView.adapter = adapter
    }

}
    private fun showCharacterDetails(character: CharacterModel) {

    }
    }

