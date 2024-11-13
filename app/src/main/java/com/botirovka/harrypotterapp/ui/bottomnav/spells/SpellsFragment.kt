package com.botirovka.harrypotterapp.ui.bottomnav.spells

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.botirovka.harrypotterapp.R

import com.botirovka.harrypotterapp.data.database.HarryPotterDatabase
import com.botirovka.harrypotterapp.data.models.SpellModel
import com.botirovka.harrypotterapp.data.repository.HarryPotterRepository
import com.botirovka.harrypotterapp.databinding.FragmentSpellsBinding
import com.botirovka.harrypotterapp.ui.character.TeachNewSpellsFragment
import kotlinx.coroutines.launch


class SpellsFragment : Fragment() {

    private lateinit var binding: FragmentSpellsBinding
    private lateinit var repository: HarryPotterRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SpellsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpellsBinding.inflate(inflater, container, false)
        repository = HarryPotterRepository(HarryPotterDatabase.getDatabase(requireContext()))
        getAllSpellsFromDB()
        return binding.root
    }

    private fun getAllSpellsFromDB() {
        recyclerView = binding.spellsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        lifecycleScope.launch {
            val spells = repository.getAllSpells()
            adapter = SpellsAdapter(spells){ spell ->
                showSpellDetails(spell)
            }
            recyclerView.adapter = adapter
        }
    }
    private fun showSpellDetails(spell: SpellModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Teach " + spell.name + " spell?")
            .setMessage("Want to teach all characters this spell?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            lifecycleScope.launch {
                val allCharacterList = repository.getAllCharacters()
                val charactersThatAlreadyKnowSpell = repository.getCharactersbySpellId(spell.id)

                val characterListToLearn = allCharacterList.filter { character ->
                    !charactersThatAlreadyKnowSpell.any { it.characterId == character.id }
                }
                val characterIdsToLearn = characterListToLearn.map { it.id }

                repository.addSpellToSelectedCharacters(spell.id,characterIdsToLearn,)
                Toast.makeText(requireContext(), "All characters are now trained in this spell", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}
