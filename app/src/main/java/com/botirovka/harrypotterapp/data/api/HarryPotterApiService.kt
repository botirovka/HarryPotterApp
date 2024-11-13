package com.botirovka.harrypotterapp.data.api
import com.botirovka.harrypotterapp.data.models.CharacterModel
import com.botirovka.harrypotterapp.data.models.SpellModel
import retrofit2.http.GET

interface HarryPotterApiService {
        @GET("characters")
        suspend fun getCharacters(): List<CharacterModel>

        @GET("spells")
        suspend fun getSpells(): List<SpellModel>
}