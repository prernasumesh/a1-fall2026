package com.example.psumesh_rapidrecall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
/**
 * Purpose: Every screen of the app is stored here. AppUI checks the gameState.currentScreen and shows the matching screen
 * Design Rationale: Screen only display data and calls game state functions when buttons are pressed
 * Outstanding issues: None**/
@Composable
fun AppUI(gameState: GameState){
    val screen = gameState.currentScreen
    if(screen == "start")
    {
        StartScreen(gameState)
    }
    else if(screen == "chooseLength")
    {
        ChooseLengthScreen(gameState)
    }
    else if(screen == "showSequence")
    {
        ShowSequenceScreen(gameState)
    }
    else if(screen == "enterGuess")
    {
        EnterGuessScreen(gameState)
    }
    else if(screen == "feedback")
    {
        FeedbackScreen(gameState)
    }
    else if(screen == "log")
    {
        LogScreen(gameState)
    }
    else if(screen == "summary")
    {
        SummaryScreen(gameState)
    }
}

@Composable
fun StartScreen(gameState: GameState) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
            Text("RapidRecall", fontSize = 36.sp)
            Button(onClick = { gameState.goToScreen("chooseLength") }) {Text("Start")}
            Button(onClick = { gameState.goToScreen("log") }) {Text("Log")}
            Button(onClick = { gameState.goToScreen("summary") }) {Text("Summary")}
    }
}

@Composable
fun ChooseLengthScreen(gameState: GameState){
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text("Choose a length(1 to 10)", fontSize = 22.sp)
        Text("${gameState.sequenceLength}", fontSize = 48.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                if(gameState.sequenceLength > 1)
                {
                    gameState.chooseLength(gameState.sequenceLength - 1)
                }
            }) {Text("-")}
            Button(onClick =
                {
                if (gameState.sequenceLength < 10){
                    gameState.chooseLength(gameState.sequenceLength + 1)
                }
            }) {Text("+")}
        }
        Button(onClick = {gameState.startGame()}) {Text("Begin") }
        Button(onClick = {gameState.goToScreen("start")}) {Text("Back") }
    }
}

/**The following function is from Claude, "Explain how I can do the show one digit at a time logic for the app", 2026-09-30 **/
@Composable
fun ShowSequenceScreen(gameState: GameState) {
    var digit by remember { mutableStateOf("") }
    // Runs when this screen opens: show each digit for 1 second,
    // then blank for 0.3 seconds, then go to the input screen.
    LaunchedEffect(Unit) {
        for (d in gameState.targetSequence) {
            digit = d.toString()
            delay(1000)
            digit = ""
            delay(300)
        }
        gameState.finishSequence()
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text("Memorize the sequence", fontSize = 22.sp)
        Text(digit, fontSize = 96.sp)
    }
}

@Composable
fun EnterGuessScreen(gameState: GameState) {
    var input by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ){
        Text("Enter the sequence", fontSize = 22.sp)
        OutlinedTextField(value = input, onValueChange = {input = it})
        Button(onClick = {gameState.submitGuess(input)}) {Text("Submit") }
    }
}

@Composable
fun FeedbackScreen(gameState: GameState) {
    val guess = gameState.lastGuess
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    )
    {
        if (guess != null)
        {
            if (guess.correct)
            {
                Text("Correct!", fontSize = 36.sp)
            }
            else
            {
                Text("Incorrect", fontSize = 36.sp)
            }
            Text("Correct sequence: ${guess.target}")
            Text("Your input: ${guess.userInput}")
        }
        Button(onClick = { gameState.goToScreen("chooseLength") }) { Text("Play Again") }
        Button(onClick = { gameState.goToScreen("start") }) { Text("Home") }
    }
}
// The following function is from Claude, "Jetpack Compose screen that shows a scrollable log of all attempts", 2026-09-30

@Composable
fun LogScreen(gameState: GameState) {
    // verticalScroll lets the list scroll when there are many attempts
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Attempt Log", fontSize = 28.sp)
        Button(onClick = { gameState.goToScreen("start") }) { Text("Back") }

        if (gameState.getGuesses().isEmpty())
        {
            Text("No attempts yet.")
        }
        for (guess in gameState.getGuesses()) {
            Text(
                "Length: ${guess.sequenceLength}\n" +
                        "Target: ${guess.target}\n" +
                        "Input: ${guess.userInput}\n" +
                        "Correct: ${guess.correct}\n" +
                        "Time: ${guess.time}"
            )
        }
    }
}

/** The following function is from Claude, "Jetpack Compose screen that shows total attempts, correct attempts and accuracy percentage", 2026-09-30**/

@Composable
    fun SummaryScreen(gameState: GameState) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text("Attempt Summary", fontSize = 28.sp)
            Text("Total attempts: ${gameState.totalGuesses()}")
            Text("Correct attempts: ${gameState.correctGuesses()}")
            Text("Accuracy: ${"%.1f".format(gameState.accuracy())}%")
            Button(onClick = { gameState.goToScreen("start") }) { Text("Back") }
        }
    }