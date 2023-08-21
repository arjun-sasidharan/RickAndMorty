package com.example.rickandmorty.episodes.detail

import com.airbnb.epoxy.EpoxyController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ModelCharacterListItemSquareBinding
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class EpisodeDetailEpoxyController(
    private val characterList: List<Character>
) : EpoxyController() {

    override fun buildModels() {
        characterList.forEach { character->
            CharacterEpoxyModel(
                imageUrl = character.image,
                name = character.name
            ).id(character.id).addTo(this)
        }
    }

    data class CharacterEpoxyModel(
        val imageUrl: String,
        val name: String
    ): ViewBindingKotlinModel<ModelCharacterListItemSquareBinding>(R.layout.model_character_list_item_square) {

        override fun ModelCharacterListItemSquareBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name
        }
    }
}