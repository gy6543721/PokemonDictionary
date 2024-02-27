package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class GenerationVii(
    val icons: levilin.pokemon.dictionary.model.remote.pokemon.Icons,
    @SerializedName("ultra-sun-ultra-moon")
    val ultraSunUltraMoon: levilin.pokemon.dictionary.model.remote.pokemon.UltraSunUltraMoon
)