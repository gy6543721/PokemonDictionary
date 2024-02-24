package levilin.pokemon.dictionary.repository

import levilin.pokemon.dictionary.data.remote.PokemonApi
import levilin.pokemon.dictionary.data.remote.response.Pokemon
import levilin.pokemon.dictionary.data.remote.response.PokemonList
import levilin.pokemon.dictionary.utility.Resource
import dagger.hilt.android.scopes.ActivityScoped
import levilin.pokemon.dictionary.data.remote.response.PokemonSpecies
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokemonApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(id: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(id = id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonSpecies(id: String): Resource<PokemonSpecies> {
        val response = try {
            api.getPokemonSpecies(id = id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }
}