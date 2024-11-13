package com.botirovka.harrypotterapp.ui.character

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
import com.botirovka.harrypotterapp.data.models.CharacterSpellModel
import com.botirovka.harrypotterapp.data.models.SpellModel
import com.botirovka.harrypotterapp.data.repository.HarryPotterRepository
import com.botirovka.harrypotterapp.databinding.FragmentCharacterDetailsBinding
import com.botirovka.harrypotterapp.ui.bottomnav.spells.SpellsAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class CharacterDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCharacterDetailsBinding
    private var character: CharacterModel? = null
    private lateinit var characterSpellList: MutableList<SpellModel>
    private lateinit var repository: HarryPotterRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SpellsAdapter

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
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        characterSpellList = mutableListOf()

        character?.let { character ->
            loadSpellListFromDb(character)

            binding.characterNameTv.text = character.name
            binding.characterActorNameTv.text = character.actor
            binding.characterHouseTv.text = character.house

            Glide.with(requireContext())
                .load(character.image)
                .into(binding.characterItemProfileIv)

        }

        binding.buttonCharacterChangeHouse.setOnClickListener {
            showChangeHouseFragment(character)
        }
        binding.buttonCharacterTeachNewSpell.setOnClickListener {
            showTeachNewSpellsFragment(character)
        }

        return binding.root
    }

    private fun loadSpellListFromDb(character: CharacterModel) {
        recyclerView = binding.characterSpellsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        var characterSpellListForLoad = listOf<CharacterSpellModel>()
        repository = HarryPotterRepository(HarryPotterDatabase.getDatabase(requireContext()))
        lifecycleScope.launch {
            characterSpellListForLoad = repository.getCharacterSpells(character)
            characterSpellListForLoad.forEach {
                characterSpellList.add(repository.getSpellbyId(it.spellId))
            }
            adapter = SpellsAdapter(characterSpellList){ spell ->
                deleteSpellFromDb(character, spell)
            }
            recyclerView.adapter = adapter
        }
    }

    private fun deleteSpellFromDb(character: CharacterModel, spell: SpellModel) {
        val characterSpellModel = CharacterSpellModel(character.id, spell.id)
        lifecycleScope.launch {
            val index = characterSpellList.indexOf(spell)
            repository.deleteCharaterSpell(characterSpellModel)
            characterSpellList.remove(spell)
            adapter.notifyItemRemoved(index)
            adapter.notifyItemRangeChanged(index, characterSpellList.size)
        }

    }

    private fun showTeachNewSpellsFragment(character: CharacterModel?) {
        character?.let { characterNotNull ->
            val teachNewSpellsFragment = TeachNewSpellsFragment.newInstance(characterNotNull)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, teachNewSpellsFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showChangeHouseFragment(character: CharacterModel?) {
        character?.let { characterNotNull ->
            val changeHouseFragment = ChangeHouseFragment.newInstance(characterNotNull)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, changeHouseFragment)
                .addToBackStack(null)
                .commit()
        }

    }

    companion object {
        private const val ARG_CHARACTER = "character"

        fun newInstance(character: CharacterModel) =
            CharacterDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, character)
                }
            }
    }
}