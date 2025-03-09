package levilin.pokemon.dictionary.ui.screen.chat

import androidx.compose.runtime.toMutableStateList
import levilin.pokemon.dictionary.model.remote.chat.ChatMessage

class ChatRoomState(
    messages: List<ChatMessage> = emptyList()
) {
    private val _chatMessages: MutableList<ChatMessage> = messages.toMutableStateList()
    val chatMessages: List<ChatMessage> = _chatMessages

    fun addMessage(message: ChatMessage) {
        _chatMessages.add(message)
    }

    fun replaceLastPendingMessage() {
        val lastMessage = _chatMessages.lastOrNull()
        lastMessage?.let {
            val newMessage = lastMessage.copy(isPending = false)
            _chatMessages.remove(lastMessage)
            _chatMessages.add(newMessage)
        }
    }
}