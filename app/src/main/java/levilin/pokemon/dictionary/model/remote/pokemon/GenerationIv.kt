package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class GenerationIv(
    @SerializedName("diamond-pearl")
    val diamondPearl: levilin.pokemon.dictionary.model.remote.pokemon.DiamondPearl,
    @SerializedName("heartgold-soulsilver")
    val heartgoldSoulsilver: levilin.pokemon.dictionary.model.remote.pokemon.HeartgoldSoulsilver,
    val platinum: levilin.pokemon.dictionary.model.remote.pokemon.Platinum
)