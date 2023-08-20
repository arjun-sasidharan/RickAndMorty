package com.example.rickandmorty.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.rickandmorty.Constants
import kotlinx.coroutines.flow.map

class EpisodesViewModel : ViewModel() {

    private val repository = EpisodeRepository()
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        EpisodePagingSource(repository)
    }.flow.cachedIn(viewModelScope).map {
        it.insertSeparators { model: EpisodeUiModel?, model2: EpisodeUiModel? ->

            // Initial separator for the first season header (before the whole list)
            if (model == null) {
                return@insertSeparators EpisodeUiModel.Header("Season 1")
            }

            // No footer
            if ( model2 == null) {
                return@insertSeparators null
            }

            // Make sure we only care about the items (episodes)
            if (model is EpisodeUiModel.Header || model2 is EpisodeUiModel.Header) {
                return@insertSeparators null
            }

            // Logic to determine if a separator is necessary
            val episode1 = (model as EpisodeUiModel.Item).episode
            val episode2 = (model2 as EpisodeUiModel.Item).episode
            return@insertSeparators if (episode2.seasonNumber != episode1.seasonNumber) {
                EpisodeUiModel.Header("Season ${episode2.seasonNumber}")
            } else {
                null
            }
        }
    }
}