package levilin.pokemon.dictionary.model.remote.species

import com.google.gson.annotations.SerializedName

data class FlavorTextEntry(
    @SerializedName("flavor_text")
    val flavorText: String,
    val language: levilin.pokemon.dictionary.model.remote.species.Language,
    val version: levilin.pokemon.dictionary.model.remote.species.Version
)