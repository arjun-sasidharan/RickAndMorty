package com.example.rickandmorty.characters

import androidx.paging.PageKeyedDataSource
import com.example.rickandmorty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharacterDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
) : PageKeyedDataSource<Int, GetCharacterByIdResponse>() {

    // loadInitial will be invoked on the very first page that needs to be loaded.
    // after than every call loadAfter method will get invoked
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getCharactersPage(1)
            if (page == null) {
                callback.onResult(emptyList(), null, null)
                return@launch
            }

            callback.onResult(page.results, null, getPageIndexFromNext(page.info.next))
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getCharactersPage(params.key)

            if (page == null) {
                callback.onResult(emptyList(), null)
                return@launch
            }

            callback.onResult(page.results, getPageIndexFromNext(page.info.next))
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        // Nothing to do
    }

    // helper method to extract next page index from the next url
    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}