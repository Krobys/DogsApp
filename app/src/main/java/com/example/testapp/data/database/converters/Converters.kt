package com.example.testapp.data.database.converters

import androidx.room.TypeConverter
import com.example.testapp.data.network.response.Breed
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromSubBreedList(value: List<Breed.SubBreed>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Breed.SubBreed>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSubBreedList(value: String): List<Breed.SubBreed> {
        val gson = Gson()
        val type = object : TypeToken<List<Breed.SubBreed>>() {}.type
        return gson.fromJson(value, type)
    }

}