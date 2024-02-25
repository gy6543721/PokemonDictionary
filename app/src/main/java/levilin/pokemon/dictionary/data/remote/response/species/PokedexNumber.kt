package levilin.pokemon.dictionary.data.remote.response.species

import com.google.gson.annotations.SerializedName

data class PokedexNumber(
    @SerializedName("entry_number")
    val entryNumber: Int,
    val pokedex: Pokedex
)