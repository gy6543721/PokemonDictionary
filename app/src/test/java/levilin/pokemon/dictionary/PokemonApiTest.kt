package levilin.pokemon.dictionary

import kotlinx.coroutines.runBlocking
import levilin.pokemon.dictionary.data.remote.PokemonApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: PokemonApi

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }

    @Test
    fun testGetPokemonSpeciesReturnsCorrectName() = runBlocking {
        val mockResponseJson = """
        {
          "id": 35,
          "name": "clefairy",
          "names": [
            {
              "name": "Clefairy",
              "language": {
                "name": "en"
              }
            }
          ]
        }
    """.trimIndent()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(mockResponseJson)
        server.enqueue(mockResponse)

        val response = api.getPokemonSpecies("clefairy")

        assertEquals("clefairy", response.name)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}