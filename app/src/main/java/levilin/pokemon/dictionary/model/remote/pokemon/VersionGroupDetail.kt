package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class VersionGroupDetail(
    @SerializedName("level_learned_at")
    val levelLearnedAt: Int,
    @SerializedName("move_learn_method")
    val moveLearnMethod: levilin.pokemon.dictionary.model.remote.pokemon.MoveLearnMethod,
    @SerializedName("version_group")
    val versionGroup: levilin.pokemon.dictionary.model.remote.pokemon.VersionGroup
)