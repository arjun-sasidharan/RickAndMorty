package com.example.rickandmorty.episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ModelEpisodeListItemBinding
import com.example.rickandmorty.domain.models.Episode
import com.example.rickandmorty.epoxy.ViewBindingKotlinModel


class EpisodeListEpoxyController : PagingDataEpoxyController<Episode>() {

    override fun buildItemModel(currentPosition: Int, item: Episode?): EpoxyModel<*> {
        return EpisodeListItemEpoxyModel(
            episode = item!!,
            onClick = { episodeId ->
                // todo
            }
        ).id("episode_${item.id}")
    }

    data class EpisodeListItemEpoxyModel(
        val episode: Episode,
        val onClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item) {

        override fun ModelEpisodeListItemBinding.bind() {
            episodeNameTextView.text = episode.name
            episodeAirDateTextView.text = episode.airDate
            episodeNumberTextView.text = episode.episode

            root.setOnClickListener { onClick(episode.id) }
        }

    }
}