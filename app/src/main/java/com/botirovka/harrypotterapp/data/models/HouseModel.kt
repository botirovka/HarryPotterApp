package com.botirovka.harrypotterapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "houses")
data class HouseModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val founder: String,
    val animal: String,
    val element: String,
    val commonRoom: String
)