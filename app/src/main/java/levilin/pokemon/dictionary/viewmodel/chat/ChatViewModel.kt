package levilin.pokemon.dictionary.viewmodel.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import levilin.pokemon.dictionary.model.remote.chat.ChatMessage
import levilin.pokemon.dictionary.model.remote.chat.Participant
import levilin.pokemon.dictionary.repository.remote.chat.ChatRoomState

class ChatViewModel(
    generativeModel: GenerativeModel
) : ViewModel() {
    private val chat = generativeModel.startChat(
        history = listOf(
            content(role = "model") { text(text = "Test") }
        )
    )

    private val _chatState: MutableStateFlow<ChatRoomState> =
        MutableStateFlow(ChatRoomState(chat.history.map { content ->
            // Initial Messages
            ChatMessage(
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.PROFESSOR,
                isPending = false
            )
        }))
    val chatState: StateFlow<ChatRoomState> = _chatState.asStateFlow()


    fun sendMessage(userMessage: String) {
        // Add pending message
        val inputContent = content(role = "user") { text(text = userMessage) }

        _chatState.value.addMessage(
            ChatMessage(
                text = userMessage,
                participant = Participant.USER,
                isPending = true
            )
        )

        viewModelScope.launch {
            try {
                val response = chat.sendMessage(prompt = inputContent)

                _chatState.value.replaceLastPendingMessage()

                response.text?.let { modelResponse ->
                    _chatState.value.addMessage(
                        ChatMessage(
                            text = modelResponse,
                            participant = Participant.PROFESSOR,
                            isPending = false
                        )
                    )
                }
            } catch (e: Exception) {
                _chatState.value.replaceLastPendingMessage()
                e.localizedMessage?.let { errorMessage ->
                    ChatMessage(
                        text = errorMessage,
                        participant = Participant.ERROR
                    )
                }?.let { chatMessage ->
                    _chatState.value.addMessage(chatMessage)
                }
            }
        }
    }
}