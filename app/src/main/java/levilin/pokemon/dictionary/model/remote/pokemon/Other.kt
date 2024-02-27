package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: levilin.pokemon.dictionary.model.remote.pokemon.DreamWorld,
    @SerializedName("official-artwork")
    val officialArtwork: levilin.pokemon.dictionary.model.remote.pokemon.OfficialArtwork
)