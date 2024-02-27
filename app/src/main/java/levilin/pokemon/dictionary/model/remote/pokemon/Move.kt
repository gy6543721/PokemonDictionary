package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class Move(
    val move: levilin.pokemon.dictionary.model.remote.pokemon.MoveX,
    @SerializedName("version_group_details")
    val versionGroupDetails: List<levilin.pokemon.dictionary.model.remote.pokemon.VersionGroupDetail>
)