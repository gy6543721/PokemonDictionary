package levilin.pokemon.dictionary.model.remote.chat

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    var text: String = "",
    val messageType: MessageType = MessageType.USER,
    var isPending: Boolean = false
)
