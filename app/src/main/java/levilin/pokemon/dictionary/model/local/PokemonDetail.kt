package levilin.pokemon.dictionary.model.local

import androidx.compose.runtime.Immutable
import levilin.pokemon.dictionary.model.remote.pokemon.Pokemon
import levilin.pokemon.dictionary.model.remote.species.PokemonSpecies

@Immutable
data class PokemonDetail(
    val pokemonInfo: Pokemon,
    val pokemonSpecies: PokemonSpecies
)