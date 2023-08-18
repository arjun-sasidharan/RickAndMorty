package com.example.rickandmorty.characters.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.characters.CharactersRepository
import com.example.rickandmorty.domain.models.Character
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {

    private val repository = CharactersRepository()

    // region comment: why two mutable live data
    // reason for having mutable live data and regular live data is bcs, we do not want whatever layer is listening
    // to this live data also have the ability to change it, we just want them to be able to consume the information.
    // so that only from the inside of the shared view model we should be able to modify the underlining data in the _characterByIdLiveData
    // endregion

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
            val character = repository.getCharacterById(characterId)
            _characterByIdLiveData.postValue(character)
        }
}