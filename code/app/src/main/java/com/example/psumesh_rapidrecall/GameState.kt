package com.example.psumesh_rapidrecall

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.DataInput
/**
 * Purpose: Keeps track of the state of the game. Mainly the current screen, chosen sequence length, sequence being played, last guess. When buttons get pressed the screen calls its functions.
 * Design Rationale: UI mainly communicates with this class. Passes the game work on to memory game, so the screen never uses the game logic directly
 * Outstanding issues: None**/
class GameState {
    private val game = MemoryGame()
    var currentScreen by mutableStateOf("start")
    var sequenceLength by mutableStateOf(1)
    var targetSequence by mutableStateOf("")
    var lastGuess by mutableStateOf<Guess?>(null)

    fun goToScreen(screen: String){
        currentScreen = screen
    }

    fun chooseLength(length: Int){
        sequenceLength = length
    }

    fun startGame(){
        targetSequence = game.generateSequence(sequenceLength)
        currentScreen = "showSequence"
    }

    fun finishSequence(){
        currentScreen = "enterGuess"
    }

    fun submitGuess(input: String){
        lastGuess = game.recordGuess(targetSequence, input)
        currentScreen = "feedback"
    }

    fun getGuesses(): List<Guess>{
        return game.getGuesses()
    }

    fun totalGuesses(): Int {
        return game.totalGuesses()
    }

    fun correctGuesses(): Int{
        return game.correctGuesses()
    }

    fun accuracy(): Double{
        return game.accuracy()
    }
}