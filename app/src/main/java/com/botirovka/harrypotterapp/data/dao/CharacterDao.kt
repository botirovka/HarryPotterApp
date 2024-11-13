package com.botirovka.harrypotterapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.botirovka.harrypotterapp.data.models.CharacterModel

@Dao
interface CharacterDao{
    @Insert
    suspend fun insert(character: CharacterModel)

    @Insert
    suspend fun insertAll(characters: List<CharacterModel>)

    @Update
    suspend fun update(character: CharacterModel)

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharacterModel>

    @Query("SELECT * FROM characters WHERE house = :house")
    suspend fun getAllCharacters(house: String): List<CharacterModel>

    @Query("SELECT * FROM characters WHERE id = :characterId")
    suspend fun getCharacterById(characterId: String): CharacterModel
}