package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class GameIndice(
    @SerializedName("game_index")
    val gameIndex: Int,
    val version: levilin.pokemon.dictionary.model.remote.pokemon.Version
)