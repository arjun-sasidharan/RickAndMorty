package com.example.rickandmorty.episodes

import com.example.rickandmorty.network.NetworkLayer
import com.example.rickandmorty.network.response.GetEpisodesPageResponse

class EpisodeRepository {

    suspend fun fetchEpisodePage(pageIndex: Int): GetEpisodesPageResponse? {
        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageIndex)

        if (!pageRequest.isSuccessful) {
            return null
        }

        return pageRequest.body
    }
}