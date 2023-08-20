package com.example.rickandmorty.domain.models

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val seasonNumber: Int = 0,
    val episodeNumber: Int = 0
) {
    fun getFormattedSeason(): String {
        return "Season $seasonNumber Episode $episodeNumber"
    }

    fun getFormattedSeasonTruncated(): String {
        return "S.$seasonNumber E.$episodeNumber"
    }
}
