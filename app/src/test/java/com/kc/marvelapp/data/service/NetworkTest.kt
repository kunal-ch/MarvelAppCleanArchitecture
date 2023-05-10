package com.kc.marvelapp.data.service

import com.google.common.truth.Truth.assertThat
import com.kc.marvelapp.util.Constants
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class NetworkTest {
    private var mockWebServer: MockWebServer = MockWebServer()
    private var api: MarvelApi
    init {
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApi::class.java)
    }

    @Before
    fun setUp() {
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `read success response json file`(){
        val response = FileUtils.readTestResourceFile("success_response.json")
        assertThat(response).isNotNull()
    }

    @Test
    fun `fetch character list correctly with 200 response`() {
        val body = FileUtils.readTestResourceFile("success_response.json")
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(body)
        mockWebServer.enqueue(response)
        runBlocking {
            val actualResponse = api.getAllCharacters()
            assertThat(actualResponse.code().toString().contains("200")).isEqualTo(response.toString().contains("200"))
        }
    }

    @Test
    fun `fetch character list with 400 bad request`() {
        val body = FileUtils.readTestResourceFile("success_response.json")
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(body)
        mockWebServer.enqueue(response)
        runBlocking {
            val actualResponse = api.getAllCharacters()
            assertThat(actualResponse.code().toString().contains("400")).isEqualTo(response.toString().contains("400"))
        }
    }

    @Test
    fun `fetch character list android count characters`() {
        val body = FileUtils.readTestResourceFile("success_response.json")
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(body)
        mockWebServer.enqueue(response)
        runBlocking {
            val actualResponse = api.getAllCharacters()
            assertThat(actualResponse.body()?.dataDto?.count).isEqualTo(20)
        }
    }

    @Test
    fun `fetch character correctly with 200 response`() {
        val body = FileUtils.readTestResourceFile("success_response.json")
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(body)
        mockWebServer.enqueue(response)
        runBlocking {
            val actualResponse = api.getCharacter(Constants.fakeId)
            assertThat(actualResponse.code().toString().contains("200")).isEqualTo(response.toString().contains("200"))
        }
    }

    @Test
    fun `fetch character with 400 bad request`() {
        val body = FileUtils.readTestResourceFile("success_response.json")
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(body)
        mockWebServer.enqueue(response)
        runBlocking {
            val actualResponse = api.getCharacter(Constants.fakeId)
            assertThat(actualResponse.code().toString().contains("400")).isEqualTo(response.toString().contains("400"))
        }
    }
}