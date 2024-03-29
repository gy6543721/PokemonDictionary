package levilin.pokemon.dictionary.di.remote

import levilin.pokemon.dictionary.repository.remote.RemoteRepository
import levilin.pokemon.dictionary.utility.ConstantValue.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import levilin.pokemon.dictionary.data.remote.PokemonApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokemonApi
    ) = RemoteRepository(api)

    @Singleton
    @Provides
    fun providePokeApi(): PokemonApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonApi::class.java)
    }
}