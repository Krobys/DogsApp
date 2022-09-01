package com.example.testapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.data.database.dao.BreedDao
import com.example.testapp.data.network.response.Breed

@Database(
    entities = [
        Breed::class, Breed.SubBreed::class
    ], version = 1, exportSchema = false
)
abstract class TestDatabase: RoomDatabase()  {

    abstract fun breedDao(): BreedDao

}