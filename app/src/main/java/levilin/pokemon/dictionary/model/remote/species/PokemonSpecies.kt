package levilin.pokemon.dictionary.model.remote.species

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class PokemonSpecies(
    @SerializedName("base_happiness")
    val baseHappiness: Int,
    @SerializedName("capture_rate")
    val captureRate: Int,
    val color: Color,
    @SerializedName("egg_groups")
    val eggGroups: List<EggGroup>,
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChain,
    @SerializedName("evolves_from_species")
    val evolvesFromSpecies: EvolvesFromSpecies,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    @SerializedName("form_descriptions")
    val formDescriptions: List<FormDescription>,
    @SerializedName("forms_switchable")
    val formsSwitchable: Boolean,
    @SerializedName("gender_rate")
    val genderRate: Int,
    val genera: List<Genera>,
    val generation: Generation,
    @SerializedName("growth_rate")
    val growthRate: GrowthRate,
    val habitat: Any,
    @SerializedName("has_gender_differences")
    val hasGenderDifferences: Boolean,
    @SerializedName("hatch_counter")
    val hatchCounter: Int,
    val id: Int,
    @SerializedName("is_baby")
    val isBaby: Boolean,
    @SerializedName("is_legendary")
    val isLegendary: Boolean,
    @SerializedName("is_mythical")
    val isMythical: Boolean,
    val name: String,
    val names: List<Name>,
    val order: Int,
    @SerializedName("pokedex_numbers")
    val pokedexNumbers: List<PokedexNumber>,
    val shape: Shape,
    val varieties: List<Variety>
)