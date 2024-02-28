package levilin.pokemon.dictionary.repository.remote

import levilin.pokemon.dictionary.data.remote.PokemonApi
import levilin.pokemon.dictionary.utility.NetworkResult
import dagger.hilt.android.scopes.ActivityScoped
import levilin.pokemon.dictionary.model.remote.pokemon.Pokemon
import levilin.pokemon.dictionary.model.remote.pokemon.PokemonList
import levilin.pokemon.dictionary.model.remote.species.PokemonSpecies
import javax.inject.Inject

@ActivityScoped
class RemoteRepository @Inject constructor(
    private val api: PokemonApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): NetworkResult<PokemonList> {
        val response = try {
            api.getPokemonList(limit = limit, offset = offset)
        } catch (e: Exception) {
            return NetworkResult.Error("An unknown error occurred.")
        }
        return NetworkResult.Success(response)
    }

    suspend fun getPokemonInfo(id: Int): NetworkResult<Pokemon> {
        val response = try {
            api.getPokemonInfo(id = id.toString())
        } catch (e: Exception) {
            return NetworkResult.Error("An unknown error occurred.")
        }
        return NetworkResult.Success(response)
    }

    suspend fun getPokemonSpecies(id: Int): NetworkResult<PokemonSpecies> {
        val response = try {
            api.getPokemonSpecies(id = id.toString())
        } catch (e: Exception) {
            return NetworkResult.Error("An unknown error occurred.")
        }
        return NetworkResult.Success(response)
    }
}