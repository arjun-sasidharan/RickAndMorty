package com.example.rickandmorty.network.response

data class GetCharacterPageResponse(
    val info: PageInfo = PageInfo(),
    val results: List<GetCharacterByIdResponse> = emptyList()
)
