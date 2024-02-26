package levilin.pokemon.dictionary.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import levilin.pokemon.dictionary.data.local.LocalDataSource
import levilin.pokemon.dictionary.data.local.LocalDataSourceDAO
import levilin.pokemon.dictionary.data.model.PokemonListEntry
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        LocalDataSource.getInstance(context = context)

    @Singleton
    @Provides
    fun provideDAO(localDataSource: LocalDataSource): LocalDataSourceDAO = localDataSource.getDAO()

    @Singleton
    @Provides
    fun provideEntity() = PokemonListEntry(
        id = 0,
        pokemonName = "",
        imageUrl = "",
        isFavorite = false
    )
}