package com.example.rickandmorty

import com.airbnb.epoxy.EpoxyController
import com.example.rickandmorty.databinding.ModelCharacterDetailsDataPointsBinding
import com.example.rickandmorty.databinding.ModelCharacterDetailsHeaderBinding
import com.example.rickandmorty.databinding.ModelCharacterDetailsImageBinding
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.epoxy.LoadingEpoxyModel
import com.example.rickandmorty.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController: EpoxyController() {

    // Managing loading, error, loaded states

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }


    var character: Character? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }


    override fun buildModels() {

        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if (character == null) {
            // todo error state
            return
        }

        // Header Model
        // here the id should ne unique
        HeaderEpoxyModel(
            name = character!!.name,
            gender = character!!.gender,
            status = character!!.status
        ).id("header").addTo(this)

        // Image Model
        ImageEpoxyModel(
            imageUrl = character!!.image
        ).id("image").addTo(this)

        // Data point models
        DataPointEpoxyModel(
            title = "Origin",
            description = character!!.origin.name
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            title = "Species",
            description = character!!.species
        ).id("data_point_2").addTo(this)
    }

    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
        val status: String
    ): ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {

        override fun ModelCharacterDetailsHeaderBinding.bind() {
            nameTextView.text = name
            aliveTextView.text = status

            if (gender.equals("male", ignoreCase = true)) {
                genderImageView.setImageResource(R.drawable.ic_male_24)
            } else {
                genderImageView.setImageResource(R.drawable.ic_female_24)
            }
        }

    }

    data class ImageEpoxyModel(
        val imageUrl: String
    ): ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {

        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(imageUrl).into(headerImageView)
        }
    }

    data class DataPointEpoxyModel(
        val title: String,
        val description: String
    ): ViewBindingKotlinModel<ModelCharacterDetailsDataPointsBinding>(R.layout.model_character_details_data_points) {

        override fun ModelCharacterDetailsDataPointsBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }
    }

}