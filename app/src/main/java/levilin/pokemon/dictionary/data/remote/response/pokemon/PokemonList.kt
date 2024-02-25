package levilin.pokemon.dictionary.data.remote.response.pokemon


data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)