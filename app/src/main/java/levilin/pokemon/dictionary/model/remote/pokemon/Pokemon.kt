package levilin.pokemon.dictionary.model.remote.pokemon


import com.google.gson.annotations.SerializedName

data class Pokemon(
    val abilities: List<levilin.pokemon.dictionary.model.remote.pokemon.Ability>,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val forms: List<levilin.pokemon.dictionary.model.remote.pokemon.Form>,
    @SerializedName("game_indices")
    val gameIndices: List<levilin.pokemon.dictionary.model.remote.pokemon.GameIndice>,
    val height: Int,
    @SerializedName("held_items")
    val heldItems: List<Any>,
    val id: Int,
    @SerializedName("is_default")
    val isDefault: Boolean,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String,
    val moves: List<levilin.pokemon.dictionary.model.remote.pokemon.Move>,
    val name: String,
    val order: Int,
    @SerializedName("past_types")
    val pastTypes: List<Any>,
    val species: levilin.pokemon.dictionary.model.remote.pokemon.Species,
    val sprites: levilin.pokemon.dictionary.model.remote.pokemon.Sprites,
    val stats: List<levilin.pokemon.dictionary.model.remote.pokemon.Stat>,
    val types: List<levilin.pokemon.dictionary.model.remote.pokemon.Type>,
    val weight: Int
)