package com.botirovka.harrypotterapp.data.repository

import com.botirovka.harrypotterapp.data.api.HarryPotterApiClient
import com.botirovka.harrypotterapp.data.database.HarryPotterDatabase
import com.botirovka.harrypotterapp.data.datastore.DataStoreManager
import com.botirovka.harrypotterapp.data.models.CharacterModel
import com.botirovka.harrypotterapp.data.models.SpellModel

class HarryPotterRepository(
    private val database: HarryPotterDatabase
) {

    suspend fun fetchCharactersFromApiAndSave() {
        val characters = HarryPotterApiClient.retrofit.getCharacters()
        database.characterDao().insertAll(characters)
    }

    suspend fun fetchSpellsFromApiAndSave(){
        val spells = HarryPotterApiClient.retrofit.getSpells()
        database.spellDao().insertAll(spells)
    }

    suspend fun getAllCharacters(): List<CharacterModel> {
        return database.characterDao().getAllCharacters()
    }

    suspend fun getAllSpells(): List<SpellModel> {
        return database.spellDao().getAllSpells()
    }

}