package com.botirovka.harrypotterapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.botirovka.harrypotterapp.data.models.CharacterSpellModel

@Dao
interface CharacterSpellDao {
    @Insert
    suspend fun insert(characterSpell: CharacterSpellModel)
    @Insert
    suspend fun insertAll(characterSpells: List<CharacterSpellModel>)

    @Delete
    suspend fun deleteCharacterSpell(characterSpell: CharacterSpellModel)

    @Query("SELECT * FROM character_spells")
    suspend fun getAllCharacterSpells(): List<CharacterSpellModel>

    @Query("SELECT * FROM character_spells WHERE characterId = :characterId")
    suspend fun getSpellsByCharacterId(characterId: String): List<CharacterSpellModel>

    @Query("SELECT * FROM character_spells WHERE spellId = :spellId")
    suspend fun getCharactersBySpellsId(spellId: String): List<CharacterSpellModel>

    @Query("""
    INSERT INTO character_spells (characterId, spellId)
    SELECT id, :spellId 
    FROM characters
    WHERE id IN (:characterIds)
    """)
    suspend fun addSpellToSelectedCharacters(spellId: String, characterIds: List<String>)
}