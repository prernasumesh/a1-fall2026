package com.example.psumesh_rapidrecall
/**
 * Purpose: This class stores one completed attempt
 * Design Rationale: Holds the data for an attempt. This helps keep the data seperate from the game logic overall. Fields are val so a Guess cant be changed after being created.
 * Outstanding issues: None**/
class Guess(
    val sequenceLength: Int,
    val userInput: String,
    val target: String,
    val correct: Boolean,
    val time: String,
)