package levilin.pokemon.dictionary.model.remote.pokemon


data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<levilin.pokemon.dictionary.model.remote.pokemon.Result>
)