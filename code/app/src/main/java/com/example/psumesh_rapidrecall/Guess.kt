package com.example.psumesh_rapidrecall
/**
 * Purpose: This class stores one completed attempt
 * Design Rationale: Holds the data
 * Outstanding issues: None**/
class Guess(
    val sequenceLength: Int,
    val userInput: String,
    val target: String,
    val correct: Boolean,
    val time: String,
)