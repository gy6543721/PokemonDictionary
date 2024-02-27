package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class GenerationI(
    @SerializedName("red-blue")
    val redBlue: levilin.pokemon.dictionary.model.remote.pokemon.RedBlue,
    val yellow: levilin.pokemon.dictionary.model.remote.pokemon.Yellow
)