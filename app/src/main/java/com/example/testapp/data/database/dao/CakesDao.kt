package com.example.testapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.data.network.response.Breed

@Dao
interface BreedDao {

    @Insert
    suspend fun setBreeds(breeds: List<Breed>)

    @Query("SELECT * FROM Breed")
    suspend fun getBreeds(): List<Breed>

    @Query("DELETE FROM Breed")
    suspend fun clearTable()
}