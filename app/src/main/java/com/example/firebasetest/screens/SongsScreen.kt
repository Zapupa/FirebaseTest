package com.example.firebasetest.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.firebasetest.models.Song
import com.example.firebasetest.network.api.ApiRepository
import kotlinx.coroutines.launch

@Composable
fun SongsScreen(){
    var songs by remember { mutableStateOf<List<Song>>(emptyList()) }
    var showLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val apiRepository = ApiRepository()

    fun loadSongs(){
        showLoading = true
        errorMessage = null

        scope.launch {
            try {
                val response = apiRepository.getSongs()
                songs = response
            } catch (e: Exception){
                errorMessage = "Error loading songs ${e.message}"
            } finally {
                showLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        loadSongs()
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ){
        Text(
            text = "Songs",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if(showLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        errorMessage?. let { message ->
            Text(
                text = message,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(songs) { song ->
                SongItem(song = song)
            }
        }
    }
}

@Composable
fun SongItem(song: Song){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = song.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = song.author, style = MaterialTheme.typography.bodyMedium)
        }
    }
}