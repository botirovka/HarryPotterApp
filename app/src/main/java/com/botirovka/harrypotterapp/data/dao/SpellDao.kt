package com.botirovka.harrypotterapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.botirovka.harrypotterapp.data.models.CharacterModel
import com.botirovka.harrypotterapp.data.models.SpellModel

@Dao
interface SpellDao {
    @Insert
    suspend fun insert(spell: SpellModel)

    @Insert
    suspend fun insertAll(spell: List<SpellModel>)

    @Update
    suspend fun update(spell: SpellModel)

    @Query("SELECT * FROM spells")
    suspend fun getAllSpells(): List<SpellModel>

    @Query("SELECT * FROM spells WHERE id = :spellId")
    suspend fun getSpellById(spellId: String): SpellModel
}