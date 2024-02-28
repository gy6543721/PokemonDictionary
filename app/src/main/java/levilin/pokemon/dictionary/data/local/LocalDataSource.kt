package levilin.pokemon.dictionary.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import levilin.pokemon.dictionary.model.local.PokemonListEntry
import levilin.pokemon.dictionary.utility.ConstantValue.DATABASE_FILE_NAME

@Database(entities = [PokemonListEntry::class], version = 1, exportSchema = false)
abstract class LocalDataSource : RoomDatabase() {
    abstract fun localDataSourceDAO(): LocalDataSourceDAO

    companion object {
        @Volatile
        private lateinit var instance: LocalDataSource
        private const val name = DATABASE_FILE_NAME

        fun getInstance(context: Context): LocalDataSource {
            if (!::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDataSource::class.java,
                    name
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }

    fun getDAO(): LocalDataSourceDAO {
        return instance.localDataSourceDAO()
    }
}