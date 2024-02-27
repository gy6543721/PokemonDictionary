package levilin.pokemon.dictionary.model.remote.species

import com.google.gson.annotations.SerializedName

data class PokemonSpecies(
    @SerializedName("base_happiness")
    val baseHappiness: Int,
    @SerializedName("capture_rate")
    val captureRate: Int,
    val color: levilin.pokemon.dictionary.model.remote.species.Color,
    @SerializedName("egg_groups")
    val eggGroups: List<levilin.pokemon.dictionary.model.remote.species.EggGroup>,
    @SerializedName("evolution_chain")
    val evolutionChain: levilin.pokemon.dictionary.model.remote.species.EvolutionChain,
    @SerializedName("evolves_from_species")
    val evolvesFromSpecies: levilin.pokemon.dictionary.model.remote.species.EvolvesFromSpecies,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<levilin.pokemon.dictionary.model.remote.species.FlavorTextEntry>,
    @SerializedName("form_descriptions")
    val formDescriptions: List<levilin.pokemon.dictionary.model.remote.species.FormDescription>,
    @SerializedName("forms_switchable")
    val formsSwitchable: Boolean,
    @SerializedName("gender_rate")
    val genderRate: Int,
    val genera: List<levilin.pokemon.dictionary.model.remote.species.Genera>,
    val generation: levilin.pokemon.dictionary.model.remote.species.Generation,
    @SerializedName("growth_rate")
    val growthRate: levilin.pokemon.dictionary.model.remote.species.GrowthRate,
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
    val names: List<levilin.pokemon.dictionary.model.remote.species.Name>,
    val order: Int,
    @SerializedName("pokedex_numbers")
    val pokedexNumbers: List<levilin.pokemon.dictionary.model.remote.species.PokedexNumber>,
    val shape: levilin.pokemon.dictionary.model.remote.species.Shape,
    val varieties: List<levilin.pokemon.dictionary.model.remote.species.Variety>
)