package levilin.pokemon.dictionary.data.model

import levilin.pokemon.dictionary.data.remote.response.Pokemon
import levilin.pokemon.dictionary.data.remote.response.PokemonSpecies

data class PokemonDetail(
    val pokemonInfo: Pokemon,
    val pokemonSpecies: PokemonSpecies
)