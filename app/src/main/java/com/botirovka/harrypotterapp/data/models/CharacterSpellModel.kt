package com.botirovka.harrypotterapp.data.models

import androidx.room.Entity

@Entity(tableName = "character_spells",
    primaryKeys = ["characterId", "spellId"])
data class CharacterSpellModel (
    val characterId: String,
    val spellId: String
)