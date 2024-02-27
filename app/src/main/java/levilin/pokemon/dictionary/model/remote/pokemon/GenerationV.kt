package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white")
    val blackWhite: levilin.pokemon.dictionary.model.remote.pokemon.BlackWhite
)