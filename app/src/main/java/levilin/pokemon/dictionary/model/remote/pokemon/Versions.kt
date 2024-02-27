package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class Versions(
    @SerializedName("generation-i")
    val generationI: levilin.pokemon.dictionary.model.remote.pokemon.GenerationI,
    @SerializedName("generation-ii")
    val generationIi: levilin.pokemon.dictionary.model.remote.pokemon.GenerationIi,
    @SerializedName("generation-iii")
    val generationIii: levilin.pokemon.dictionary.model.remote.pokemon.GenerationIii,
    @SerializedName("generation-iv")
    val generationIv: levilin.pokemon.dictionary.model.remote.pokemon.GenerationIv,
    @SerializedName("generation-v")
    val generationV: levilin.pokemon.dictionary.model.remote.pokemon.GenerationV,
    @SerializedName("generation-vi")
    val generationVi: levilin.pokemon.dictionary.model.remote.pokemon.GenerationVi,
    @SerializedName("generation-vii")
    val generationVii: levilin.pokemon.dictionary.model.remote.pokemon.GenerationVii,
    @SerializedName("generation-viii")
    val generationViii: levilin.pokemon.dictionary.model.remote.pokemon.GenerationViii
)