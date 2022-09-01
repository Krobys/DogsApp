package com.example.testapp.data.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.testapp.data.database.converters.Converters

@Entity
class Breed(
    val value: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @TypeConverters(Converters::class)
    var subBreeds: List<SubBreed> = listOf()

    @Entity
    class SubBreed(val value: String) {
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
    }
}