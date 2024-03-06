package levilin.pokemon.dictionary.model.remote.chat

import java.util.UUID

enum class Participant {
    USER, PROFESSOR, ERROR
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    var text: String = "",
    val participant: Participant = Participant.USER,
    var isPending: Boolean = false
)
