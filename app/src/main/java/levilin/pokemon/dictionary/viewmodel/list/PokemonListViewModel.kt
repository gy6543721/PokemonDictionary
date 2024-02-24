package levilin.pokemon.dictionary.viewmodel.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import levilin.pokemon.dictionary.data.model.PokemonListEntry
import levilin.pokemon.dictionary.repository.PokemonRepository
import levilin.pokemon.dictionary.utility.ConstantValue.PAGE_SIZE
import levilin.pokemon.dictionary.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
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
                        String.format("%03d", pokemonListEntry.number).contains(query.trim())
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
            when (val result =
                repository.getPokemonList(limit = PAGE_SIZE, offset = currentPage * PAGE_SIZE)) {
                is Resource.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.count

                    val entriesDeferred = result.data.results.map { entry ->
                        async(Dispatchers.IO) {
                            val number = if (entry.url.endsWith(suffix = "/")) {
                                entry.url.dropLast(n = 1).takeLastWhile { character ->
                                    character.isDigit()
                                }
                            } else {
                                entry.url.takeLastWhile { character ->
                                    character.isDigit()
                                }
                            }

                            val pokemonName = entry.name.replaceFirstChar { character ->
                                if (character.isLowerCase()) character.titlecase(Locale.ROOT) else character.toString()
                            }

                            val pokemonNameLocalized =
                                when (val speciesResult =
                                    repository.getPokemonSpecies(id = number)) {
                                    is Resource.Success -> {
                                        speciesResult.data?.names?.find { names ->
                                            names.language.name.contains(Locale.getDefault().language)
                                        }?.name ?: pokemonName
                                    }

                                    else -> {
                                        pokemonName
                                    }
                                }

                            val imageUrl =
                                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"

                            PokemonListEntry(
                                pokemonName = pokemonNameLocalized,
                                imageUrl = imageUrl,
                                number = number.toInt()
                            )
                        }
                    }

                    val pokemonEntries = entriesDeferred.awaitAll()

                    currentPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokemonEntries
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
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
}