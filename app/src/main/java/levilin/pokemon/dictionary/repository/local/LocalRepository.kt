package levilin.pokemon.dictionary.repository.local

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import levilin.pokemon.dictionary.data.local.LocalDataSourceDAO
import levilin.pokemon.dictionary.data.model.PokemonListEntry
import javax.inject.Inject

@ViewModelScoped
class LocalRepository @Inject constructor(private val localDataSourceDAO: LocalDataSourceDAO) {
    val getAllItems: Flow<List<PokemonListEntry>> = localDataSourceDAO.getAllItems()

    suspend fun insertItem(pokemonListEntry: PokemonListEntry) {
        localDataSourceDAO.insertItem(pokemonListEntry = pokemonListEntry)
    }
    suspend fun updateItem(pokemonListEntry: PokemonListEntry) {
        localDataSourceDAO.updateItem(pokemonListEntry = pokemonListEntry)
    }
    suspend fun deleteItem(pokemonListEntry: PokemonListEntry) {
        localDataSourceDAO.deleteItem(pokemonListEntry = pokemonListEntry)
    }
}