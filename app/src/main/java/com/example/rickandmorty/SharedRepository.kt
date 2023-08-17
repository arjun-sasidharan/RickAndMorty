package com.example.rickandmorty

import com.example.rickandmorty.domain.mappers.CharacterMapper
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.network.NetworkLayer
import com.example.rickandmorty.network.RickAndMortyCache
import com.example.rickandmorty.network.response.GetCharacterByIdResponse
import com.example.rickandmorty.network.response.GetEpisodeByIdResponse

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): Character? {

        // Check the cache for character
        val cachedCharacter = RickAndMortyCache.characterMap[characterId]
        if (cachedCharacter != null) {
            return cachedCharacter
        }

        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        // fetching episode
        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)
        val character = CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisodes
        )

        // Update cache
        RickAndMortyCache.characterMap[characterId] = character
        return character
    }

    private suspend fun getEpisodesFromCharacterResponse(
        characterResponse: GetCharacterByIdResponse
    ): List<GetEpisodeByIdResponse> {
        val episodeRange = characterResponse.episode.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()

        val request = NetworkLayer.apiClient.getEpisodeByRange(episodeRange)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body

    }
}