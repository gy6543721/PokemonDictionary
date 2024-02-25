package levilin.pokemon.dictionary.data.remote.response.species

import com.google.gson.annotations.SerializedName

data class Variety(
    @SerializedName("is_default")
    val isDefault: Boolean,
    val pokemon: Pokemon
)