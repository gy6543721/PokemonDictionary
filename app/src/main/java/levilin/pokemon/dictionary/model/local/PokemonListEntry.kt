package levilin.pokemon.dictionary.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import levilin.pokemon.dictionary.utility.ConstantValue

@Entity(tableName = ConstantValue.LIST_TABLE_NAME)
data class PokemonListEntry(
    @PrimaryKey
    @ColumnInfo(name = "ID")
    val id: Int,
    val pokemonName: String,
    val imageUrl: String,
    var isFavorite: Boolean
)
