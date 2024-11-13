package com.botirovka.harrypotterapp.data.models
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity (tableName = "characters")
data class CharacterModel (
    @PrimaryKey
    val id: String,
    val name: String,
    var house: String,
    val actor: String,
    val image: String
) : Parcelable