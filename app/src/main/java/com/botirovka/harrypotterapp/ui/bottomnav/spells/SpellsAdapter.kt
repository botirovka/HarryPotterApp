package com.botirovka.harrypotterapp.ui.bottomnav.spells

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.botirovka.harrypotterapp.R
import com.botirovka.harrypotterapp.data.models.SpellModel


class SpellsAdapter(
    private val spells: List<SpellModel>,
    private val onItemClick: (SpellModel) -> Unit
) : RecyclerView.Adapter<SpellsAdapter.SpellViewHolder>() {

    class SpellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.spell_title_tv)
        val descriptionTextView: TextView = itemView.findViewById(R.id.spell_description_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.spell_rv_item, parent, false)
        return SpellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return spells.size
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        val spell = spells[position]

        holder.titleTextView.text = spell.name
        holder.descriptionTextView.text = spell.description

        holder.itemView.setOnClickListener {
            onItemClick(spell)
        }
    }
}