package levilin.pokemon.dictionary.ui.screen.detail

import androidx.lifecycle.ViewModel
import levilin.pokemon.dictionary.data.remote.response.Pokemon
import levilin.pokemon.dictionary.repository.PokemonRepository
import levilin.pokemon.dictionary.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }
}