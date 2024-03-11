package levilin.pokemon.dictionary.repository.remote.chat

import com.google.ai.client.generativeai.GenerativeModel
import levilin.pokemon.dictionary.di.remote.GeminiPro
import levilin.pokemon.dictionary.di.remote.GeminiProVision
import javax.inject.Inject

class ChatRoomRepositoryImpl @Inject constructor(
    @GeminiPro private val geminiProModel: GenerativeModel,
    @GeminiProVision private val geminiProVisionModel: GenerativeModel
) : ChatRoomRepository {
    override fun getGeminiProModel(): GenerativeModel = geminiProModel
    override fun getGeminiProVisionModel(): GenerativeModel = geminiProVisionModel
}