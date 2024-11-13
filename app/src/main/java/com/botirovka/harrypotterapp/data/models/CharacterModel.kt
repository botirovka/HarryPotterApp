package com.botirovka.harrypotterapp.data.models
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "characters")
data class CharacterModel (
    @PrimaryKey
    val id: String,
    val name: String,
    val house: String,
    val actor: String,
    val image: String
)