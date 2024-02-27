package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class GenerationIii(
    val emerald: levilin.pokemon.dictionary.model.remote.pokemon.Emerald,
    @SerializedName("firered-leafgreen")
    val fireredLeafgreen: levilin.pokemon.dictionary.model.remote.pokemon.FireredLeafgreen,
    @SerializedName("ruby-sapphire")
    val rubySapphire: levilin.pokemon.dictionary.model.remote.pokemon.RubySapphire
)