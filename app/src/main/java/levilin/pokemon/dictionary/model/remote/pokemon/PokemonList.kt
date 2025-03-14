package levilin.pokemon.dictionary.model.remote.pokemon

import androidx.compose.runtime.Immutable

@Immutable
data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)