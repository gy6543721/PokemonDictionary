package levilin.pokemon.dictionary.di.remote

import android.app.Application
import android.content.Context
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import levilin.pokemon.dictionary.BuildConfig
import levilin.pokemon.dictionary.repository.remote.chat.ChatRoomRepository
import levilin.pokemon.dictionary.repository.remote.chat.ChatRoomRepositoryImpl
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatRoomModule {
    private val harassment = SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH)
    private val hateSpeech =
        SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE)

    private val config = generationConfig {
        temperature = 0.99f
        topK = 50
        topP = 0.99f
    }

    @Provides
    @Singleton
    @GeminiPro
    fun provideGemini(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.apiKey,
            safetySettings = listOf(
                harassment, hateSpeech
            ),
            generationConfig = config
        )
    }

    @Provides
    @Singleton
    @GeminiProVision
    fun provideGeminiVision(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-pro-vision",
            apiKey = BuildConfig.apiKey,
            safetySettings = listOf(
                harassment,
                hateSpeech,
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.ONLY_HIGH),
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE)
            ),
        )
    }

    @Provides
    @Singleton
    fun provideChatModelRepository(
        @GeminiPro geminiProModel: GenerativeModel,
        @GeminiProVision geminiProVisionModel: GenerativeModel
    ): ChatRoomRepository = ChatRoomRepositoryImpl(geminiProModel, geminiProVisionModel)

    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeminiPro

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeminiProVision