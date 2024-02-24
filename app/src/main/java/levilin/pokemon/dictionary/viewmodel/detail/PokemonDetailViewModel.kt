package levilin.pokemon.dictionary.viewmodel.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import levilin.pokemon.dictionary.repository.PokemonRepository
import levilin.pokemon.dictionary.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import levilin.pokemon.dictionary.data.model.PokemonDetail
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    private val _pokemonDetail = mutableStateOf<Resource<PokemonDetail>>(Resource.Loading())
    val pokemonDetail: MutableState<Resource<PokemonDetail>> = _pokemonDetail

    fun loadPokemonDetail(pokemonID: String) {
        viewModelScope.launch {
            _pokemonDetail.value = Resource.Loading()
            val pokemonInfoResult = repository.getPokemonInfo(pokemonID)
            val speciesResult = repository.getPokemonSpecies(pokemonID)

            if (pokemonInfoResult is Resource.Success && speciesResult is Resource.Success) {
                _pokemonDetail.value = Resource.Success(
                    PokemonDetail(
                        pokemonInfo = pokemonInfoResult.data!!,
                        pokemonSpecies = speciesResult.data!!
                    )
                )
            } else if (pokemonInfoResult is Resource.Error) {
                _pokemonDetail.value = Resource.Error(pokemonInfoResult.message ?: "An error occurred.")
            } else if (speciesResult is Resource.Error) {
                _pokemonDetail.value = Resource.Error(speciesResult.message ?: "An error occurred.")
            }
        }
    }
}