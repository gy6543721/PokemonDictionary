package levilin.pokemon.dictionary.repository.remote.chat

import com.google.ai.client.generativeai.GenerativeModel

interface ChatRoomRepository {
    fun getGeminiProModel(): GenerativeModel
    fun getGeminiProVisionModel(): GenerativeModel
}
