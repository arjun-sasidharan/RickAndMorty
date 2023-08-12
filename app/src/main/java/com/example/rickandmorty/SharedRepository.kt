package com.example.rickandmorty

import com.example.rickandmorty.domain.mappers.CharacterMapper
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.network.NetworkLayer

class SharedRepository {

    suspend fun getCharacterById(characterId: Int) : Character? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed) {
            return null
        }

        if (!request.isSuccessful) {
            return null
        }

        return CharacterMapper.buildFrom(response = request.body)
    }
}