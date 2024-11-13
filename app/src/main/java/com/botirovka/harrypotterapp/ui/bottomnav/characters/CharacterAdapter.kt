package com.botirovka.harrypotterapp.ui.bottomnav.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat


import androidx.recyclerview.widget.RecyclerView
import com.botirovka.harrypotterapp.R
import com.botirovka.harrypotterapp.data.models.CharacterModel
import com.bumptech.glide.Glide

class CharacterAdapter (
    private val characters: List<CharacterModel>,
    private val onItemClick: (CharacterModel) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.character_item_username_tv)
        val houseTextView: TextView = itemView.findViewById(R.id.character_item_house_textview)
        val characterImageView: ImageView = itemView.findViewById(R.id.character_item_profile_iv)
        val houseImageView: ImageView = itemView.findViewById(R.id.character_item_house_iv)
        val characterLayout: ConstraintLayout = itemView.findViewById(R.id.character_item_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_rv_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]

        holder.nameTextView.text = character.name
        holder.houseTextView.text = character.house

        Glide.with(holder.itemView.context)
            .load(character.image)
            .into(holder.characterImageView)


        when (character.house) {
            "Gryffindor" -> {
                holder.houseImageView.setImageResource(R.drawable.lion_icon)
                holder.characterLayout.setBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.gryffindor_red)
                )
            }
            "Slytherin" -> {
                holder.houseImageView.setImageResource(R.drawable.snake_icon)
                holder.characterLayout.setBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.slytherin_green)
                )
            }
            "Hufflepuff" -> {
                holder.houseImageView.setImageResource(R.drawable.badger_icon)
                holder.characterLayout.setBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.hufflepuff_canary)
                )
            }
            "Ravenclaw" -> {
                holder.houseImageView.setImageResource(R.drawable.raven_icon)
                holder.characterLayout.setBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.ravenclaw_dark_blue)
                )
            }
            else -> {
                holder.houseImageView.setImageResource(R.drawable.cross_icon)
                holder.houseTextView.setText(holder.itemView.context.getString(R.string.not_a_student_text))
                holder.characterLayout.setBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.slytherin_dark_silver)
                )
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(character)
        }
    }

}