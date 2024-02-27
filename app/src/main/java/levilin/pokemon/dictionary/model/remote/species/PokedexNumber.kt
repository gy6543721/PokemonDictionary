package levilin.pokemon.dictionary.model.remote.species

import com.google.gson.annotations.SerializedName

data class PokedexNumber(
    @SerializedName("entry_number")
    val entryNumber: Int,
    val pokedex: levilin.pokemon.dictionary.model.remote.species.Pokedex
)