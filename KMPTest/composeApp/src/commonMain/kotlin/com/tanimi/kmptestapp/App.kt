package com.tanimi.kmptestapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tanimi.kmptestapp.data.entity.AnswerHistory
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.tanimi.kmptestapp.viewmodel.LineApiViewModel
import com.tanimi.kmptestapp.viewmodel.QuestionViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Line : Screen("line")
    data object Question : Screen("question")

    companion object {
        val startScreen = Home
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    AppNavHost(navController)
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.startScreen.route
    ) {
        composable(Screen.Home.route) {
            MainScreen(
                onNavigate = { id ->
                    navController.navigate(id)
                }
            )
        }
        composable(Screen.Line.route) {
            LineScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Question.route) {
            QuestionScreen(onBack = {navController.popBackStack()})
        }
    }
}

@Composable
@Preview
fun MainScreen(onNavigate: (String) -> Unit) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(onClick = {
                    onNavigate(Screen.Line.route)
                }) {
                    Text("Line送信")
                }
                Button(onClick = {
                    onNavigate(Screen.Question.route)
                }) {
                    Text("AIに質問")
                }
            }
        }
    }
}

@Composable
@Preview
fun LineScreen(onBack: () -> Unit, viewModel: LineApiViewModel = LineApiViewModel()) {
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
                viewModel.saveMessage(message)
            }) {
                Text("送信")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun QuestionScreen(onBack: () -> Unit, viewModel: QuestionViewModel = QuestionViewModel()) {
    var question by remember { mutableStateOf("") }
    val answerList by viewModel.answerHistory.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Answer History") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
                .padding(padding)
        ) {
            // 固定表示部分
            TextField(
                value = question,
                onValueChange = { question = it },
                label = { Text("質問内容を入力") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Button(
                onClick = { viewModel.sendQuestion(question) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text("送信")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // スクロール可能な LazyColumn
            if (answerList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("履歴はまだありません")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(answerList) { answer ->
                        answerItem(answer)
                    }
                }
            }
        }
    }
}

@Composable
fun answerItem(history: AnswerHistory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Q: ${history.question}", style = MaterialTheme.typography.bodyLarge)
            Text("A: ${history.answer}", style = MaterialTheme.typography.bodyMedium)
            Text("日時: ${history.created}", style = MaterialTheme.typography.labelSmall)
        }
    }
}
