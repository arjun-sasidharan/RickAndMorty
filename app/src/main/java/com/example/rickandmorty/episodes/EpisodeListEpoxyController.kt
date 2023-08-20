package com.example.rickandmorty.episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ModelEpisodeListItemBinding
import com.example.rickandmorty.databinding.ModelEpisodeListTitleBinding
import com.example.rickandmorty.domain.models.Episode
import com.example.rickandmorty.epoxy.ViewBindingKotlinModel


class EpisodeListEpoxyController : PagingDataEpoxyController<EpisodeUiModel>() {

    override fun buildItemModel(currentPosition: Int, item: EpisodeUiModel?): EpoxyModel<*> {
        return when (item!!) {
            is EpisodeUiModel.Item -> {
                val episode = (item as EpisodeUiModel.Item).episode
                EpisodeListItemEpoxyModel(
                    episode = episode,
                    onClick = { episodeId ->
                        // todo
                    }
                ).id("episode_${episode.id}")
            }

            is EpisodeUiModel.Header -> {
                val headerText = (item as EpisodeUiModel.Header).text
                EpisodeListTitleEpoxyModel(title = headerText)
                    .id("episode_header_$headerText")
            }
        }
    }

    data class EpisodeListItemEpoxyModel(
        val episode: Episode,
        val onClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item) {

        override fun ModelEpisodeListItemBinding.bind() {
            episodeNameTextView.text = episode.name
            episodeAirDateTextView.text = episode.airDate
            episodeNumberTextView.text = episode.getFormattedSeasonTruncated()

            root.setOnClickListener { onClick(episode.id) }
        }

    }

    data class EpisodeListTitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelEpisodeListTitleBinding>(R.layout.model_episode_list_title) {

        override fun ModelEpisodeListTitleBinding.bind() {
            textView.text = title
        }
    }
}