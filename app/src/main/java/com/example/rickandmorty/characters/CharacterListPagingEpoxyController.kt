package com.example.rickandmorty.characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ModelCharacterListItemBinding
import com.example.rickandmorty.databinding.ModelCharacterListTitleBinding
import com.example.rickandmorty.epoxy.LoadingEpoxyModel
import com.example.rickandmorty.epoxy.ViewBindingKotlinModel
import com.example.rickandmorty.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharacterListPagingEpoxyController(
    private val onCharacterSelected: (Int) -> Unit
) : PagedListEpoxyController<GetCharacterByIdResponse>() {

    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(
            characterId = item!!.id,
            imageUrl = item.image,
            name = item.name,
            onCharacterSelected = onCharacterSelected
        ).id(item.id)
    }

    // used to add or remove models from the existing epoxy models
    override fun addModels(models: List<EpoxyModel<*>>) {

        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        CharacterGridTitleEpoxyModel("Main Family")
            .id("main_family_header")
            .addTo(this)

        // extracting first 5 character which are main character
        super.addModels(models.subList(0, 5))

        // taking the rest of the list and grouping them based on the item name's first letter
        (models.subList(5, models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
            it.name[0].uppercase()
        }.forEach { mapEntry ->
            val character = mapEntry.key.uppercase()
            CharacterGridTitleEpoxyModel(title = character)
                .id(character)
                .addTo(this)
            // below will be a list of GridItemModel
            super.addModels(mapEntry.value)
        }


    }

    data class CharacterGridItemEpoxyModel(
        val characterId: Int,
        val imageUrl: String,
        val name: String,
        val onCharacterSelected: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {

        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name

            root.setOnClickListener {
                onCharacterSelected(characterId)
            }
        }
    }

    data class CharacterGridTitleEpoxyModel(
        val title: String
    ) : ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {

        override fun ModelCharacterListTitleBinding.bind() {
            textView.text = title
        }

        // overriding, so title view will take up the whole width of the screen, or total span count
        // epoxy model span size default value is 1
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

}

