package com.example.maxlasitsa_wordlegame

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    object FourLetterWordList {
        private val words = listOf("WORD", "PLAY", "GAME", "TREE")

        fun getRandomFourLetterWord(): String {
            return words.random()
        }
    }

    private lateinit var inputGuess: EditText
    private lateinit var submitButton: Button
    private lateinit var guessTextViews: Array<TextView>
    private lateinit var checkTextViews: Array<TextView>
    private lateinit var targetWordView: TextView

    private var targetWord = FourLetterWordList.getRandomFourLetterWord()
    private var numberOfGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputGuess = findViewById(R.id.inputGuess)
        submitButton = findViewById(R.id.submitGuess)
        targetWordView = findViewById(R.id.targetWord)

        guessTextViews = arrayOf(
            findViewById(R.id.guess1),
            findViewById(R.id.guess2),
            findViewById(R.id.guess3)
        )

        checkTextViews = arrayOf(
            findViewById(R.id.check1),
            findViewById(R.id.check2),
            findViewById(R.id.check3)
        )

        submitButton.setOnClickListener {
            val guess = inputGuess.text.toString().uppercase()
            if (guess.length == 4) {
                if (numberOfGuesses < 3) {
                    val check = checkGuess(guess, targetWord)
                    guessTextViews[numberOfGuesses].text = "Guess #${numberOfGuesses + 1}: $guess"
                    checkTextViews[numberOfGuesses].text = "Check: $check"
                    numberOfGuesses++
                    inputGuess.text.clear()
                }

                if (numberOfGuesses == 3) {
                    endGame()
                }
            } else {
                Toast.makeText(this, "Please enter a 4 letter word", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun endGame() {
        inputGuess.isEnabled = false
        submitButton.isEnabled = false
        targetWordView.text = "The word was: $targetWord"
        targetWordView.visibility = TextView.VISIBLE
    }

    private fun checkGuess(guess: String, target: String): String {
        var result = ""
        for (i in guess.indices) {
            result += when {
                guess[i] == target[i] -> 'O'
                target.contains(guess[i]) -> '+'
                else -> 'X'
            }
        }
        return result
    }
}