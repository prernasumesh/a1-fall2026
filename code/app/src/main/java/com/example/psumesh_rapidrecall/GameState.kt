package com.example.psumesh_rapidrecall

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.DataInput

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