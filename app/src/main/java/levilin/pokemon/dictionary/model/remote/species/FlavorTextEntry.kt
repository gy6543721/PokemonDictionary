package levilin.pokemon.dictionary.model.remote.species

import com.google.gson.annotations.SerializedName

data class FlavorTextEntry(
    @SerializedName("flavor_text")
    val flavorText: String,
    val language: Language,
    val version: Version
)