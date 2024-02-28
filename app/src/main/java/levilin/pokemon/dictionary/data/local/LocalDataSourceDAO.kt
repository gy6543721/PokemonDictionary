package levilin.pokemon.dictionary.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import levilin.pokemon.dictionary.model.local.PokemonListEntry
import levilin.pokemon.dictionary.utility.ConstantValue.LIST_TABLE_NAME

@Dao
interface LocalDataSourceDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(pokemonListEntry: PokemonListEntry)

    @Query("SELECT * FROM $LIST_TABLE_NAME ORDER BY id ASC")
    fun getAllItems(): Flow<List<PokemonListEntry>>

    @Query("SELECT * FROM $LIST_TABLE_NAME WHERE id = :id")
    fun getItemById(id: Int): Flow<PokemonListEntry>

    @Query("SELECT * FROM $LIST_TABLE_NAME WHERE pokemonName = :name")
    fun getItemByName(name: String): Flow<PokemonListEntry>

    @Update
    suspend fun updateItem(pokemonListEntry: PokemonListEntry)

    @Delete
    suspend fun deleteItem(pokemonListEntry: PokemonListEntry)
}