package levilin.pokemon.dictionary.model.local

import levilin.pokemon.dictionary.model.remote.pokemon.Pokemon
import levilin.pokemon.dictionary.model.remote.species.PokemonSpecies

data class PokemonDetail(
    val pokemonInfo: Pokemon,
    val pokemonSpecies: PokemonSpecies
)