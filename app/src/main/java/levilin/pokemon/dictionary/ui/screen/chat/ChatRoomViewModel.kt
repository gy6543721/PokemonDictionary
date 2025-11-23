package levilin.pokemon.dictionary.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import levilin.pokemon.dictionary.model.remote.chat.ChatMessage
import levilin.pokemon.dictionary.model.remote.chat.MessageType
import levilin.pokemon.dictionary.repository.remote.chat.ChatRoomRepository
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    chatRoomRepository: ChatRoomRepository
) : ViewModel() {
    private val geminiProModel = chatRoomRepository.getGeminiProModel()
    private val chat = geminiProModel.startChat(history = emptyList())

    private val _chatState: MutableStateFlow<ChatRoomState> =
        MutableStateFlow(ChatRoomState(emptyList()))
    val chatState: StateFlow<ChatRoomState> = _chatState.asStateFlow()

    fun sendMessage(userMessage: String) {
        // User message
        val deviceLanguage = Locale.getDefault().displayLanguage
        val inputContent = content(role = "user") {
            text(text = "$userMessage (you are Professor Oak in Pokemon world, respond in $deviceLanguage)")
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