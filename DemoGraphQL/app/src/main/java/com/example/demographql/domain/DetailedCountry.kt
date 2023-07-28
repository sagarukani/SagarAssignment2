package com.example.demographql.domain

import android.icu.util.Currency

data class DetailedCountry (
    val code:String,
    val name:String,
    val emoji:String,
    val capital:String,
    val currency: String,
    val languages :List<String>,
    val continent :String
)