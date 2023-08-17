package com.example.rickandmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.network.RickAndMortyCache
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private val repository = SharedRepository()

    // reason for having mutable live data and regular live data is bcs, we do not want whatever layer is listening
    // to this live data also have the ability to change it, we just want them to be able to consume the information.
    // so that only from the inside of the shared view model we should be able to modify the underlining data in the _characterByIdLiveData
    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun fetchCharacter(characterId: Int) {

        // Check the cache for character
        val cachedCharacter = RickAndMortyCache.characterMap[characterId]
        if (cachedCharacter != null) {
            _characterByIdLiveData.postValue(cachedCharacter)
            return
        }

        // Otherwise, fetch character from api
        viewModelScope.launch {
            val response = repository.getCharacterById(characterId)
            _characterByIdLiveData.postValue(response)

            // Update cache if non null character received
            response?.let {
                RickAndMortyCache.characterMap[characterId] = it
            }
        }
    }

}