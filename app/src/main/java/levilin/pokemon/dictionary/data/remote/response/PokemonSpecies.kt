package levilin.pokemon.dictionary.data.remote.response

data class PokemonSpecies(
    val id: Int,
    val name: String,
    val names: List<Name>
)