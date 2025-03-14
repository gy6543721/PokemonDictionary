package levilin.pokemon.dictionary.model.remote.chat

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    var text: String = "",
    val messageType: MessageType = MessageType.USER,
    var isPending: Boolean = false
)
