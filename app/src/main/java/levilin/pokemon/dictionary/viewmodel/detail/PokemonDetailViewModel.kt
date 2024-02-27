package levilin.pokemon.dictionary.viewmodel.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import levilin.pokemon.dictionary.repository.remote.RemoteRepository
import levilin.pokemon.dictionary.utility.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import levilin.pokemon.dictionary.model.local.PokemonDetail
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    private val _pokemonDetail = mutableStateOf<NetworkResult<PokemonDetail>>(NetworkResult.Loading())
    val pokemonDetail: MutableState<NetworkResult<PokemonDetail>> = _pokemonDetail

    fun loadPokemonDetail(pokemonID: Int) {
        viewModelScope.launch {
            _pokemonDetail.value = NetworkResult.Loading()
            val pokemonInfoResult = remoteRepository.getPokemonInfo(id = pokemonID)
            val speciesResult = remoteRepository.getPokemonSpecies(id = pokemonID)

            val pokemonInfo = pokemonInfoResult.data
            val pokemonSpecies = speciesResult.data

            when {
                pokemonInfoResult is NetworkResult.Success && speciesResult is NetworkResult.Success && pokemonInfo != null && pokemonSpecies != null -> {
                    _pokemonDetail.value = NetworkResult.Success(
                        PokemonDetail(
                            pokemonInfo = pokemonInfo,
                            pokemonSpecies = pokemonSpecies
                        )
                    )
                }

                pokemonInfoResult is NetworkResult.Error -> {
                    _pokemonDetail.value = NetworkResult.Error(
                        pokemonInfoResult.message ?: "An error occurred fetching Pokemon info."
                    )
                }

                speciesResult is NetworkResult.Error -> {
                    _pokemonDetail.value = NetworkResult.Error(
                        speciesResult.message ?: "An error occurred fetching Pokemon species."
                    )
                }

                else -> {
                    _pokemonDetail.value = NetworkResult.Error("An unexpected error occurred.")
                }
            }
        }
    }
}