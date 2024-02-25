package levilin.pokemon.dictionary.data.remote.response.pokemon


import com.google.gson.annotations.SerializedName

data class GameIndice(
    @SerializedName("game_index")
    val gameIndex: Int,
    val version: Version
)