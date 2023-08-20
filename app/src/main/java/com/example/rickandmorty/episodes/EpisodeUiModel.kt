package com.example.rickandmorty.episodes

import com.example.rickandmorty.domain.models.Episode

sealed class EpisodeUiModel {
    class Item(val episode: Episode): EpisodeUiModel()
    class Header(val text: String): EpisodeUiModel()
}