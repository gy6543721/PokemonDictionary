package levilin.pokemon.dictionary.viewmodel.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import levilin.pokemon.dictionary.model.remote.chat.ChatMessage
import levilin.pokemon.dictionary.model.remote.chat.MessageType
import levilin.pokemon.dictionary.repository.remote.chat.ChatRoomState
import java.util.Locale

class ChatRoomViewModel (
    generativeModel: GenerativeModel
) : ViewModel() {
    private val chat = generativeModel.startChat(
        history = emptyList()
    )

    private val _chatState: MutableStateFlow<ChatRoomState> = MutableStateFlow(ChatRoomState(emptyList()))
    val chatState: StateFlow<ChatRoomState> = _chatState.asStateFlow()


    fun sendMessage(userMessage: String) {
        // Add pending message
        val deviceLanguage = Locale.getDefault().displayLanguage
        val inputContent = content(role = "user") {
            text(text = "$userMessage (you are オーキド博士 in Pokemon world, respond in $deviceLanguage)")
        }

        _chatState.value.addMessage(
            ChatMessage(
                text = userMessage,
                messageType = MessageType.USER,
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
                            messageType = MessageType.MODEL,
                            isPending = false
                        )
                    )
                }
            } catch (e: Exception) {
                _chatState.value.replaceLastPendingMessage()
                e.localizedMessage?.let { errorMessage ->
                    _chatState.value.addMessage(
                        ChatMessage(
                            text = errorMessage,
                            messageType = MessageType.ERROR,
                            isPending = false
                        )
                    )
                }
            }
        }
    }
}