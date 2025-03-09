package levilin.pokemon.dictionary.ui.screen.list

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import levilin.pokemon.dictionary.model.local.PokemonListEntry
import levilin.pokemon.dictionary.repository.remote.RemoteRepository
import levilin.pokemon.dictionary.utility.ConstantValue.PAGE_SIZE
import levilin.pokemon.dictionary.utility.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import levilin.pokemon.dictionary.repository.local.LocalRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    private var currentPage = 0

    private val _pokemonList = MutableStateFlow<List<PokemonListEntry>>(listOf())
    val pokemonList: StateFlow<List<PokemonListEntry>> = _pokemonList

    val loadError = MutableStateFlow("")
    val isLoading = MutableStateFlow(false)
    val endReached = MutableStateFlow(false)

    // Search Properties
    private val cachedPokemonList = MutableStateFlow<List<PokemonListEntry>>(listOf())
    private val _searchList = MutableStateFlow<List<PokemonListEntry>>(listOf())
    val searchList: StateFlow<List<PokemonListEntry>> = _searchList
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText
    val isSearching = MutableStateFlow(false)

    init {
        getAllItems()
        loadPokemonList()
    }

    fun loadPokemonList() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = remoteRepository.getPokemonList(
                limit = PAGE_SIZE,
                offset = currentPage * PAGE_SIZE
            )) {
                is NetworkResult.Success -> {
                    val count = result.data?.count ?: 0
                    endReached.value = currentPage * PAGE_SIZE >= count

                    val newEntries = result.data?.results?.mapNotNull { entry ->
                        withContext(Dispatchers.IO) {
                            val id = entry.url.trimEnd('/').takeLastWhile { it.isDigit() }.toInt()

                            if (cachedPokemonList.value.any { it.id == id }) return@withContext null

                            val pokemonName = entry.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                            }

                            val pokemonNameLocalized = when (val speciesResult =
                                remoteRepository.getPokemonSpecies(id = id)) {
                                is NetworkResult.Success -> speciesResult.data?.names?.find {
                                    it.language.name.contains(Locale.getDefault().language)
                                }?.name ?: pokemonName
                                else -> pokemonName
                            }

                            PokemonListEntry(
                                id = id,
                                pokemonName = pokemonNameLocalized,
                                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
                                isFavorite = false
                            )
                        }
                    } ?: emptyList()

                    if (newEntries.isNotEmpty()) {
                        val updatedList = cachedPokemonList.value.toMutableList()
                        updatedList.addAll(newEntries)
                        cachedPokemonList.value = updatedList
                        _pokemonList.value = cachedPokemonList.value

                        newEntries.forEach { item ->
                            insertItem(pokemonListEntry = item)
                        }
                    }

                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                }

                is NetworkResult.Error -> {
                    loadError.value = result.message ?: "An unknown error occurred"
                    isLoading.value = false
                    endReached.value = true
                }

                else -> {}
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun searchPokemonList(query: String) {
        isSearching.value = true
        _inputText.value = query
        if (_inputText.value.isEmpty()) {
            _pokemonList.value = cachedPokemonList.value
            isSearching.value = false
            return
        }

        viewModelScope.launch(Dispatchers.Default) {
            val localResults = cachedPokemonList.value.filter { pokemonListEntry ->
                pokemonListEntry.pokemonName.contains(_inputText.value.trim(), ignoreCase = true) ||
                        String.format("%03d", pokemonListEntry.id).contains(_inputText.value.trim())
            }.toMutableList()

            if (localResults.isEmpty()) {
                val apiResult = withContext(Dispatchers.IO) {
                    try {
                        if (_inputText.value.isDigitsOnly()) {
                            remoteRepository.getPokemonInfoById(
                                id = _inputText.value.trim().toInt()
                            )
                        } else if (Locale.getDefault().language.contains("en")) {
                            remoteRepository.getPokemonInfoByName(name = _inputText.value.trim())
                        } else {
                            null
                        }
                    } catch (e: Exception) {
                        null
                    }
                }

                when (apiResult) {
                    is NetworkResult.Success -> {
                        val pokemonListEntry = apiResult.data?.let { pokemon ->
                            val pokemonNameLocalized = when (val speciesResult =
                                remoteRepository.getPokemonSpecies(id = pokemon.id)) {
                                is NetworkResult.Success -> speciesResult.data?.names?.find {
                                    it.language.name.contains(Locale.getDefault().language)
                                }?.name ?: pokemon.name
                                else -> pokemon.name
                            }

                            PokemonListEntry(
                                id = pokemon.id,
                                pokemonName = pokemonNameLocalized,
                                imageUrl = pokemon.sprites.frontDefault,
                                isFavorite = false
                            )
                        }

                        if (pokemonListEntry != null && cachedPokemonList.value.none { it.id == pokemonListEntry.id }) {
                            cachedPokemonList.value += pokemonListEntry
                            insertItem(pokemonListEntry = pokemonListEntry)
                        }
                    }
                    is NetworkResult.Error -> {
                        loadError.value = apiResult.message ?: "No Result Found."
                    }
                    else -> {}
                }
            }

            _searchList.value = localResults.sortedBy { it.id }
            _pokemonList.value = _searchList.value
        }
    }

    fun setInputText(inputValue: String) {
        if (inputValue.isBlank()) {
            clearInputText()
        } else {
            _inputText.value = inputValue
            searchPokemonList(_inputText.value)
        }
    }

    fun clearInputText() {
        _inputText.value = ""
        _pokemonList.value = cachedPokemonList.value
    }

    fun checkFavorite(input: PokemonListEntry): Boolean {
        return input.isFavorite
    }

    fun changeFavorite(isFavorite: Boolean, entry: PokemonListEntry) {
        if (isFavorite != checkFavorite(input = entry)) {
            val updatedEntry = entry.copy(isFavorite = isFavorite)
            updateItem(pokemonListEntry = updatedEntry)

            _searchList.value = _searchList.value.map { pokemonListEntry ->
                if (pokemonListEntry.id == entry.id) updatedEntry else pokemonListEntry
            }

            _pokemonList.value = _pokemonList.value.map { pokemonListEntry ->
                if (pokemonListEntry.id == entry.id) updatedEntry else pokemonListEntry
            }

            cachedPokemonList.value = cachedPokemonList.value.map { pokemonListEntry ->
                if (pokemonListEntry.id == entry.id) updatedEntry else pokemonListEntry
            }

            if (isSearching.value) {
                searchPokemonList(query = _inputText.value)
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

    private fun getAllItems() {
        viewModelScope.launch {
            localRepository.getAllItems.collect { itemList ->
                _pokemonList.value = itemList
                cachedPokemonList.value = _pokemonList.value
            }
        }
    }

    private fun getItemById(id: Int) {
        viewModelScope.launch {
            localRepository.getItemById(id = id)
        }
    }

    private fun getItemByName(name: String) {
        viewModelScope.launch {
            localRepository.getItemByName(name = name)
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