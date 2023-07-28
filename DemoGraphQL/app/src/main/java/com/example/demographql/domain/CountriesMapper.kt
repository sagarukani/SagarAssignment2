package com.example.demographql.domain

import com.example.CountriesQuery
import com.example.CountryQuery

fun CountryQuery.Country.toDetailedCountry() : DetailedCountry{
    return DetailedCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital ?: "No Capital",
        currency = currency ?: "No currency",
        continent = continent.name,
        languages = languages.mapNotNull { it.name }

    )
}

fun CountriesQuery.Country.toSimpleCountry() : SingleCountry{
    return SingleCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital ?: "No Capital"

    )
}