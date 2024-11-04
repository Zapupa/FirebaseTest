package com.example.firebasetest.network.api

import com.example.firebasetest.models.Song
import com.example.firebasetest.network.api.ApiClient.client
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiRepository {
    suspend fun getSongs():List<Song> = client.get(ApiRoutes.SONGS).body()
}