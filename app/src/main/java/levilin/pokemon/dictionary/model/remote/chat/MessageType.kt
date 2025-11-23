package levilin.pokemon.dictionary.model.remote.chat

import androidx.compose.runtime.Immutable

@Immutable
enum class MessageType {
    USER, MODEL, ERROR
}