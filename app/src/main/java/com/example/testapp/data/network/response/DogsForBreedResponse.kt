package com.example.testapp.data.network.response


import com.google.gson.annotations.SerializedName

data class DogsForBreedResponse(
    @SerializedName("message")
    var dogsImages: List<String>,
    @SerializedName("status")
    var status: String
)