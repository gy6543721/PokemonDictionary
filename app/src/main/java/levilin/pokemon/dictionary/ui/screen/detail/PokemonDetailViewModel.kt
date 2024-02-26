package levilin.pokemon.dictionary.ui.screen.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import levilin.pokemon.dictionary.repository.remote.RemoteRepository
import levilin.pokemon.dictionary.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import levilin.pokemon.dictionary.data.model.PokemonDetail
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    private val _pokemonDetail = mutableStateOf<Resource<PokemonDetail>>(Resource.Loading())
    val pokemonDetail: MutableState<Resource<PokemonDetail>> = _pokemonDetail

    fun loadPokemonDetail(pokemonID: Int) {
        viewModelScope.launch {
            _pokemonDetail.value = Resource.Loading()
            val pokemonInfoResult = remoteRepository.getPokemonInfo(id = pokemonID)
            val speciesResult = remoteRepository.getPokemonSpecies(id = pokemonID)

            val pokemonInfo = pokemonInfoResult.data
            val pokemonSpecies = speciesResult.data

            when {
                pokemonInfoResult is Resource.Success && speciesResult is Resource.Success && pokemonInfo != null && pokemonSpecies != null -> {
                    _pokemonDetail.value = Resource.Success(
                        PokemonDetail(
                            pokemonInfo = pokemonInfo,
                            pokemonSpecies = pokemonSpecies
                        )
                    )
                }

                pokemonInfoResult is Resource.Error -> {
                    _pokemonDetail.value = Resource.Error(
                        pokemonInfoResult.message ?: "An error occurred fetching Pokemon info."
                    )
                }

                speciesResult is Resource.Error -> {
                    _pokemonDetail.value = Resource.Error(
                        speciesResult.message ?: "An error occurred fetching Pokemon species."
                    )
                }

                else -> {
                    _pokemonDetail.value = Resource.Error("An unexpected error occurred.")
                }
            }
        }
    }
}