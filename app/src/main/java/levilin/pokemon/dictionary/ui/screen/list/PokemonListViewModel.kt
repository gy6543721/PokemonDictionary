package levilin.pokemon.dictionary.ui.screen.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import levilin.pokemon.dictionary.data.model.PokemonListEntry
import levilin.pokemon.dictionary.repository.remote.RemoteRepository
import levilin.pokemon.dictionary.utility.ConstantValue.PAGE_SIZE
import levilin.pokemon.dictionary.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import levilin.pokemon.dictionary.repository.local.LocalRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private var currentPage = 0

    var pokemonList = mutableStateOf<List<PokemonListEntry>>(listOf())

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokemonListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        getAllItems()
        loadPokemonList()
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter { pokemonListEntry ->
                pokemonListEntry.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        String.format("%03d", pokemonListEntry.id).contains(query.trim())
            }
            if (isSearchStarting) {
                cachedPokemonList = pokemonList.value
                isSearchStarting = false
            }
            pokemonList.value = results
            isSearching.value = true
        }
    }

    fun loadPokemonList() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = remoteRepository.getPokemonList(
                limit = PAGE_SIZE,
                offset = currentPage * PAGE_SIZE
            )) {
                is Resource.Success -> {
                    val count = result.data?.count ?: 0
                    endReached.value = currentPage * PAGE_SIZE >= count

                    val resultListDeferred = result.data?.results?.map { entry ->
                        async(Dispatchers.IO) {
                            val id = if (entry.url.endsWith("/")) {
                                entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                            } else {
                                entry.url.takeLastWhile { it.isDigit() }
                            }.toInt()

                            if (pokemonList.value.any { it.id == id }) {
                                null
                            } else {
                                val pokemonName = entry.name.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                                }

                                val pokemonNameLocalized = when (val speciesResult = remoteRepository.getPokemonSpecies(id = id)) {
                                    is Resource.Success -> speciesResult.data?.names?.find {
                                        it.language.name.contains(
                                            Locale.getDefault().language
                                        )
                                    }?.name ?: pokemonName

                                    else -> pokemonName
                                }

                                val imageUrl =
                                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

                                PokemonListEntry(
                                    id = id,
                                    pokemonName = pokemonNameLocalized,
                                    imageUrl = imageUrl,
                                    isFavorite = false
                                ).also {
                                    insertItem(pokemonListEntry = it)
                                }
                            }
                        }
                    } ?: emptyList()

                    currentPage++

                    loadError.value = ""
                    isLoading.value = false

                    val resultList =
                        resultListDeferred.awaitAll().filterNotNull().sortedBy { it.id }
                    pokemonList.value += resultList
                }

                is Resource.Error -> {
                    loadError.value = result.message ?: "An unknown error occurred"
                    isLoading.value = false
                    endReached.value = true
                }

                else -> {}
            }
        }
    }

    fun getDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    fun checkFavorite(input: PokemonListEntry): Boolean {
        return input.isFavorite
    }

    fun changeFavorite(isFavorite: Boolean, entry: PokemonListEntry) {
        if (isFavorite != checkFavorite(input = entry)) {
            val updatedEntry = entry.copy(isFavorite = isFavorite)
            updateItem(pokemonListEntry = updatedEntry)
        }
    }

    fun loadFavoriteList() {
        getAllItems()
    }

    private fun getAllItems() {
        viewModelScope.launch {
            localRepository.getAllItems.collect { itemList ->
                pokemonList.value = itemList
            }
        }
    }

    private fun insertItem(pokemonListEntry: PokemonListEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.insertItem(pokemonListEntry = pokemonListEntry)
        }
    }

    private fun updateItem(pokemonListEntry: PokemonListEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.updateItem(pokemonListEntry = pokemonListEntry)
        }
    }

    private fun deleteItem(pokemonListEntry: PokemonListEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.deleteItem(pokemonListEntry = pokemonListEntry)
        }
    }
}