package com.tanimi.kmptestapp

import androidx.compose.ui.window.ComposeUIViewController
import com.tanimi.kmptestapp.viewmodel.LineApiViewModel

fun MainViewController() = ComposeUIViewController { App(viewModel = LineApiViewModel()) }