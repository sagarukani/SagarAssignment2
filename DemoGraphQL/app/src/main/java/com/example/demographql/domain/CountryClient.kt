package com.example.demographql.domain;


interface CountryClient {
     suspend fun getCountries() : List<SingleCountry>

     suspend fun getCountry(code:String) : DetailedCountry?
}
