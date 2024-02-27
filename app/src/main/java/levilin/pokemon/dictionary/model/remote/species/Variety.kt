package levilin.pokemon.dictionary.model.remote.species

import com.google.gson.annotations.SerializedName

data class Variety(
    @SerializedName("is_default")
    val isDefault: Boolean,
    val pokemon: levilin.pokemon.dictionary.model.remote.species.Pokemon
)