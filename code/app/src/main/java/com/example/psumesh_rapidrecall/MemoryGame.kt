package com.example.psumesh_rapidrecall

import android.health.connect.datatypes.units.Length
import kotlin.random.Random
import java.util.Date
/**
 * Purpose : This class holds the game rules and the history for each round played.
 * This class also creates the sequences that the user guesses.
 * Records each guess and calculate the total, correct attempts and accuracy.
 * Design Rationale: Keeps all game logic seperate from the frontend UI
 * Outstanding issues: None
 * **/
class MemoryGame {
    private val guesses = mutableListOf<Guess>()

    fun generateSequence(length: Int): String { //returns random sequence
        var sequence = ""
        for(i in 1..length){
            sequence += Random.nextInt(10)
        }
        return sequence
    }

    fun recordGuess(target: String, input: String): Guess{ //creates, saves and returns guess
        val guess = Guess(target.length, input, target, input == target, Date().toString())
        guesses.add(guess)
        return guess
    }

    fun getGuesses(): List<Guess> { //returns copy
        return guesses.toList()
    }

    fun totalGuesses(): Int { //total guesses made
        return guesses.size
    }

    fun correctGuesses(): Int{ //no. of correct guesses
        var count = 0
        for(guess in guesses){
            if(guess.correct){
                count++
            }
        }
        return count
    }

    fun accuracy(): Double{ //% of correct guesses
        if(totalGuesses() == 0){
            return 0.0
        }
        return correctGuesses() * 100.0/ totalGuesses()
    }
}

