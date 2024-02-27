package levilin.pokemon.dictionary.model.local

data class PokemonDetail(
    val pokemonInfo: levilin.pokemon.dictionary.model.remote.pokemon.Pokemon,
    val pokemonSpecies: levilin.pokemon.dictionary.model.remote.species.PokemonSpecies
)