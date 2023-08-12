package com.example.rickandmorty.characters

import androidx.paging.DataSource
import com.example.rickandmorty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope

class CharactersDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
) : DataSource.Factory<Int, GetCharacterByIdResponse>() {

    override fun create(): DataSource<Int, GetCharacterByIdResponse> {
        return CharacterDataSource(coroutineScope, repository)
    }
}