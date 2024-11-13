package com.botirovka.harrypotterapp.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.botirovka.harrypotterapp.databinding.FragmentTeachNewSpellsBinding
import com.botirovka.harrypotterapp.ui.bottomnav.spells.SpellsAdapter
import kotlinx.coroutines.launch


class TeachNewSpellsFragment : Fragment() {
    private lateinit var binding: FragmentTeachNewSpellsBinding
    private var character: CharacterModel? = null
    private lateinit var allSpellList: MutableList<SpellModel>
    private lateinit var characterSpellList: MutableList<SpellModel>
    private lateinit var repository: HarryPotterRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SpellsAdapter
    private lateinit var newSpell: SpellModel
    private var isNewSkill = true

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
        binding = FragmentTeachNewSpellsBinding.inflate(inflater,container,false)


        allSpellList = mutableListOf()
        characterSpellList = mutableListOf()






        character?.let { character ->
            binding.teachSpellCharacterNameTv.text = character.name
            getAllSpellsFromDb()

        }

        binding.buttonNewRandomSpell.setOnClickListener {
            val newRandomSpell = chooseRandomSpell()
            if(newRandomSpell != null){
                newSpell = newRandomSpell
                isNewSkill = true
            }

        }
        binding.buttonAddSpellToList.setOnClickListener {
            if(isNewSkill && allSpellList.isNotEmpty()){
                addSpellToList(newSpell)
                val newRandomSpell = chooseRandomSpell()
                if(newRandomSpell != null){
                    newSpell = newRandomSpell
                    isNewSkill = true
                }
                else{
                    isNewSkill = false
                }
            }

        }

        binding.buttonTeachNewSpell.setOnClickListener {
            character?.let { character ->
                if(characterSpellList.isNotEmpty()){
                    val characterSpellListForInsert = createCharacterSpellListForInsert(character)
                    lifecycleScope.launch {
                    repository.insertSpells(characterSpellListForInsert)
                        Toast.makeText(requireContext(),"Successfully learned new spells",Toast.LENGTH_SHORT)
                    }
                    requireActivity().supportFragmentManager.popBackStack()
                }

            }
        }

        binding.buttonCancelTeachNewSpell.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    private fun createCharacterSpellListForInsert(character: CharacterModel): List<CharacterSpellModel>{
        val characterSpellListForInsert = mutableListOf<CharacterSpellModel>()
        characterSpellList.forEach { spell ->
            val newCharacterSpellModel = CharacterSpellModel(character.id, spell.id)
            characterSpellListForInsert.add(newCharacterSpellModel)

        }
        return characterSpellListForInsert
    }

    private fun addSpellToList(newRandomSpell: SpellModel) {
        characterSpellList.add(newRandomSpell)
        allSpellList.remove(newRandomSpell)
        adapter.notifyItemInserted(characterSpellList.size-1)
    }

    private fun getAllSpellsFromDb() {
        recyclerView = binding.teachNewSpellListRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))


        repository = HarryPotterRepository(HarryPotterDatabase.getDatabase(requireContext()))
        lifecycleScope.launch {
            val allSpellsFromDb = repository.getAllSpells()
            allSpellList.addAll(allSpellsFromDb)
            adapter = SpellsAdapter(characterSpellList){ spell ->
                removeSpellFromList(spell)
            }
            recyclerView.adapter = adapter

            character?.let { loadSpellListFromDb(it) }
        }

    }

    private fun loadSpellListFromDb(character: CharacterModel) {
        lifecycleScope.launch {
            var characterKnowSpellList: List<CharacterSpellModel>
            characterKnowSpellList = repository.getCharacterSpells(character)
            val knownSpells = mutableListOf<SpellModel>()
            characterKnowSpellList.forEach {
                knownSpells.add(repository.getSpellbyId(it.spellId))
            }

            allSpellList.removeAll(knownSpells)
            adapter.notifyDataSetChanged()

            val newRandomSpell = chooseRandomSpell()
            if(newRandomSpell != null){
                newSpell = newRandomSpell
            }

        }

    }

    private fun removeSpellFromList(spell: SpellModel) {
        val index = characterSpellList.indexOf(spell)
        characterSpellList.remove(spell)
        allSpellList.add(spell)
        adapter.notifyItemRemoved(index)
        adapter.notifyItemRangeChanged(index, characterSpellList.size)

    }

    private fun chooseRandomSpell(): SpellModel? {
        if(allSpellList.isEmpty()){
            binding.teachNewSpellRandomSpellTv.text =
                getString(R.string.you_already_added_all_spells)
            return null
        }
        else{
        val randomSpell = allSpellList.random()
        binding.teachNewSpellRandomSpellTv.text = randomSpell.name
            return randomSpell
        }


    }

    companion object {
        private const val ARG_CHARACTER = "character"

        fun newInstance(character: CharacterModel) =
            TeachNewSpellsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, character)
                }
            }

    }
}