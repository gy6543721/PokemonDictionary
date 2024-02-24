package levilin.pokemon.dictionary.viewmodel.detail

import androidx.lifecycle.ViewModel
import levilin.pokemon.dictionary.data.remote.response.Pokemon
import levilin.pokemon.dictionary.repository.PokemonRepository
import levilin.pokemon.dictionary.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import levilin.pokemon.dictionary.data.remote.response.PokemonSpecies
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    suspend fun getPokemonInfo(id: String): Resource<Pokemon> {
        return repository.getPokemonInfo(id = id)
    }

    suspend fun getPokemonSpecies(id: String): Resource<PokemonSpecies> {
        return repository.getPokemonSpecies(id = id)
    }
}