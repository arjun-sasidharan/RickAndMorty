package com.example.rickandmorty.characters

import com.example.rickandmorty.network.NetworkLayer
import com.example.rickandmorty.network.response.GetCharacterPageResponse

class CharactersRepository {

    suspend fun getCharactersPage(pageIndex: Int): GetCharacterPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }

}