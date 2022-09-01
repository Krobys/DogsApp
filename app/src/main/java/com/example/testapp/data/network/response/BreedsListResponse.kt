package com.example.testapp.data.network.response


import com.google.gson.annotations.SerializedName

data class BreedsListResponse(
    @SerializedName("message")
    var message: HashMap<String, List<String>> = hashMapOf()
) {
    fun getBreeds(): List<Breed> {
        return message.map { breed ->
            Breed(
                breed.key,
            ).apply {
                subBreeds = breed.value.map { subBreed -> Breed.SubBreed(subBreed) }
            }
        }
    }

    fun setBreeds(breeds: List<Breed>) {
        message.clear()
        breeds.forEach { breed ->
            message[breed.value] = breed.subBreeds.map { subBreed -> subBreed.value }
        }
    }
}