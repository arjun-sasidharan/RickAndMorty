package com.example.rickandmorty.characters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmorty.Constants
import com.example.rickandmorty.CharacterDetailActivity
import com.example.rickandmorty.R

class CharacterListActivity: AppCompatActivity() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    private val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        // connecting page list live data with epoxy controller to show in the UI
        viewModel.charactersPagedListLiveData.observe(this) {pagedList ->
            epoxyController.submitList(pagedList)
        }

        findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)

    }

    private fun onCharacterSelected(characterId: Int) {
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra(Constants.INTENT_EXTRA_CHARACTER_ID, characterId)
        startActivity(intent)
    }

}