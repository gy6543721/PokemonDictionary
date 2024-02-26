package levilin.pokemon.dictionary.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import levilin.pokemon.dictionary.data.model.PokemonListEntry
import levilin.pokemon.dictionary.utility.ConstantValue

@Dao
interface LocalDataSourceDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(pokemonListEntry: PokemonListEntry)

    @Query("SELECT * FROM ${ConstantValue.LIST_TABLE_NAME}")
    fun getAllItems(): Flow<List<PokemonListEntry>>

    @Update
    suspend fun updateItem(pokemonListEntry: PokemonListEntry)

    @Delete
    suspend fun deleteItem(pokemonListEntry: PokemonListEntry)
}