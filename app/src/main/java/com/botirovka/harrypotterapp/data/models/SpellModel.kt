package com.botirovka.harrypotterapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "spells")
data class SpellModel (
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String
)