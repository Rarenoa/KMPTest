package com.tanimi.kmptestapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tanimi.kmptestapp.App
import com.tanimi.kmptestapp.viewmodel.LineApiViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(viewModel = LineApiViewModel())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(viewModel = LineApiViewModel())
}