package com.botirovka.harrypotterapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.botirovka.harrypotterapp.data.models.CharacterSpellModel

@Dao
interface CharacterSpellDao {
    @Insert
    suspend fun insert(characterSpell: CharacterSpellModel)

    @Query("SELECT * FROM character_spells WHERE characterId = :characterId")
    suspend fun getSpellsByCharacterId(characterId: String): List<CharacterSpellModel>

    @Query("SELECT * FROM character_spells WHERE spellId = :spellId")
    suspend fun getCharactersBySpellsId(spellId: String): List<CharacterSpellModel>
}