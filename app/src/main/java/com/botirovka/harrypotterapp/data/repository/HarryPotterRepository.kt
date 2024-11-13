package com.botirovka.harrypotterapp.data.repository

import com.botirovka.harrypotterapp.data.api.HarryPotterApiClient
import com.botirovka.harrypotterapp.data.database.HarryPotterDatabase
import com.botirovka.harrypotterapp.data.models.CharacterModel
import com.botirovka.harrypotterapp.data.models.CharacterSpellModel
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
    suspend fun getAllCharacters(house: String): List<CharacterModel>{
        return database.characterDao().getAllCharacters(house)
    }

    suspend fun getAllSpells(): List<SpellModel> {
        return database.spellDao().getAllSpells()
    }

    suspend fun updateCharacter(character: CharacterModel){
        return database.characterDao().update(character)
    }

    suspend fun getCharacterSpells(character: CharacterModel): List<CharacterSpellModel>{
        return database.characterSpellDao().getSpellsByCharacterId(character.id)
    }

    suspend fun insertSpells(characterSpells: List<CharacterSpellModel>){
        return database.characterSpellDao().insertAll(characterSpells)
    }

    suspend fun deleteCharaterSpell(charaterSpell: CharacterSpellModel){
        return database.characterSpellDao().deleteCharacterSpell(charaterSpell)
    }

    suspend fun getSpellbyId(spellId: String): SpellModel{
        return database.spellDao().getSpellById(spellId)
    }

    suspend fun getCharactersbySpellId(spellId: String): List<CharacterSpellModel>{
        return database.characterSpellDao().getCharactersBySpellsId(spellId)
    }

    suspend fun addSpellToSelectedCharacters(spellId: String, characterIds: List<String>) {
        database.characterSpellDao().addSpellToSelectedCharacters(spellId, characterIds)
    }

    suspend fun getAllCharacterSpells(): List<CharacterSpellModel> {
        return database.characterSpellDao().getAllCharacterSpells()
    }

}