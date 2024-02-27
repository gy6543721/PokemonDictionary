package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class GenerationVi(
    @SerializedName("omegaruby-alphasapphire")
    val omegarubyAlphasapphire: levilin.pokemon.dictionary.model.remote.pokemon.OmegarubyAlphasapphire,
    @SerializedName("x-y")
    val xY: levilin.pokemon.dictionary.model.remote.pokemon.XY
)