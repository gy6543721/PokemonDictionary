package levilin.pokemon.dictionary.repository.remote.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import levilin.pokemon.dictionary.BuildConfig
import levilin.pokemon.dictionary.viewmodel.chat.ChatRoomViewModel

val GenerativeViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val config = generationConfig {
            temperature = 0.7f
        }

        @Suppress("UNCHECKED_CAST")
        return with(modelClass) {
            when {
                isAssignableFrom(ChatRoomViewModel::class.java) -> {
                    // Initialize GenerativeModel
                    val generativeModel = GenerativeModel(
                        modelName = "gemini-pro",
                        apiKey = BuildConfig.apiKey,
                        generationConfig = config
                    )
                    ChatRoomViewModel(
                        generativeModel = generativeModel
                    )
                }

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}