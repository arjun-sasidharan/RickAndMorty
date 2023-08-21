package com.example.rickandmorty.domain.mappers

import com.example.rickandmorty.domain.models.Episode
import com.example.rickandmorty.network.response.GetCharacterByIdResponse
import com.example.rickandmorty.network.response.GetEpisodeByIdResponse

object EpisodeMapper {

    fun buildFrom(
        networkEpisode: GetEpisodeByIdResponse,
        networkCharacters: List<GetCharacterByIdResponse> = emptyList()
    ): Episode {
        return Episode(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.air_date,
            seasonNumber = getSeasonFromEpisodeString(networkEpisode.episode),
            episodeNumber = getEpisodeFromEpisodeString(networkEpisode.episode),
            characters = networkCharacters.map {
                CharacterMapper.buildFrom(it)
            }
        )
    }

    private fun getSeasonFromEpisodeString(episode: String): Int {
        // S01E01
        val endIndex = episode.indexOfFirst { it.equals('e', ignoreCase = true) }
        if (endIndex == -1) {
            return 0
        }
        return episode.substring(1, endIndex).toIntOrNull() ?: 0
    }

    private fun getEpisodeFromEpisodeString(episode: String): Int {
        val startIndex = episode.indexOfFirst { it.equals('e', ignoreCase = true) }
        if (startIndex == -1) {
            return 0
        }
        return episode.substring(startIndex + 1, episode.length).toIntOrNull() ?: 0
    }
}