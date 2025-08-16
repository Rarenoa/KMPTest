package com.tanimi.kmptestapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.tanimi.kmptestapp.viewmodel.LineApiViewModel

@Composable
@Preview
fun App(viewModel: LineApiViewModel) {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(value = message,
                onValueChange = { message = it },
                label = { Text("送信する文字を入力") },
                modifier = Modifier
                    .background(Color.Transparent)
            )
            Button(onClick = {
                showContent = !showContent
                viewModel.sendLineMessage(message)
            }) {
                Text("送信")
                viewModel.saveMessage(message)
            }

        }
    }
}