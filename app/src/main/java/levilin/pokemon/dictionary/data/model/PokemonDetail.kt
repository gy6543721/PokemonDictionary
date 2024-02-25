package levilin.pokemon.dictionary.data.model

import levilin.pokemon.dictionary.data.remote.response.pokemon.Pokemon
import levilin.pokemon.dictionary.data.remote.response.species.PokemonSpecies

data class PokemonDetail(
    val pokemonInfo: Pokemon,
    val pokemonSpecies: PokemonSpecies
)