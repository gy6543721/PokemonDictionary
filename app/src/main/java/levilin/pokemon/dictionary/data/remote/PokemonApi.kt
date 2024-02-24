package levilin.pokemon.dictionary.data.remote

import levilin.pokemon.dictionary.data.remote.response.Pokemon
import levilin.pokemon.dictionary.data.remote.response.PokemonList
import levilin.pokemon.dictionary.data.remote.response.PokemonSpecies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: String
    ): Pokemon

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(
        @Path("id") id: String
    ): PokemonSpecies
}