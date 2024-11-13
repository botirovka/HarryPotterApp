package com.botirovka.harrypotterapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.botirovka.harrypotterapp.data.dao.CharacterDao
import com.botirovka.harrypotterapp.data.dao.CharacterSpellDao
import com.botirovka.harrypotterapp.data.dao.HouseDao
import com.botirovka.harrypotterapp.data.dao.SpellDao
import com.botirovka.harrypotterapp.data.models.CharacterModel
import com.botirovka.harrypotterapp.data.models.CharacterSpellModel
import com.botirovka.harrypotterapp.data.models.HouseModel
import com.botirovka.harrypotterapp.data.models.SpellModel

@Database(
    entities = [
        CharacterModel::class,
        CharacterSpellModel::class,
        HouseModel::class,
        SpellModel::class
    ],
    version = 1
)
abstract class HarryPotterDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun spellDao(): SpellDao
    abstract fun houseDao(): HouseDao
    abstract fun characterSpellDao(): CharacterSpellDao

    companion object {
        @Volatile
        private var INSTANCE: HarryPotterDatabase? = null

        fun getDatabase(context: Context): HarryPotterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HarryPotterDatabase::class.java,
                    "harry_potter_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}