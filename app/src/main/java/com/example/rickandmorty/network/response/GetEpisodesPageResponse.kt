package com.example.rickandmorty.network.response

data class GetEpisodesPageResponse(
    val info: PageInfo = PageInfo(),
    val results: List<GetEpisodeByIdResponse> = emptyList()
)