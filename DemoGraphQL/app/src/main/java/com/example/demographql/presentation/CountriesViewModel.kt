package com.example.demographql.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demographql.domain.DetailedCountry
import com.example.demographql.domain.GetCountriesUseCase
import com.example.demographql.domain.GetCountryUseCase
import com.example.demographql.domain.SingleCountry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCountryUseCase: GetCountryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CountriesState())
    val state = _state.asStateFlow()

    data class CountriesState(
        val countries: List<SingleCountry> = emptyList(),
        val selectedCountry: DetailedCountry? = null,
        val isLoading: Boolean = false
    )

    fun selectCountry(code:String){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedCountry = getCountryUseCase.execute(code = code)
                )
            }
        }
    }

    fun dismissCountryDialog(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedCountry = null
                )
            }
        }
    }

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            _state.update { it.copy(
                countries = getCountriesUseCase.execute(),
                isLoading = false
            ) }
        }
    }
}