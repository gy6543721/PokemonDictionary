package levilin.pokemon.dictionary.repository.local

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import levilin.pokemon.dictionary.data.local.LocalDataSourceDAO
import levilin.pokemon.dictionary.model.local.PokemonListEntry
import javax.inject.Inject

@ViewModelScoped
class LocalRepository @Inject constructor(private val localDataSourceDAO: LocalDataSourceDAO) {
    val getAllItems: Flow<List<PokemonListEntry>> = localDataSourceDAO.getAllItems()

    fun getItemById(id: Int): Flow<PokemonListEntry> {
        return localDataSourceDAO.getItemById(id = id)
    }

    fun getItemByName(name: String): Flow<PokemonListEntry> {
        return localDataSourceDAO.getItemByName(name = name)
    }

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