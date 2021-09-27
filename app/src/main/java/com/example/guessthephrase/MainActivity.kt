package com.example.guessthephrase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var main_constraintLayout: ConstraintLayout
    private lateinit var phrase_editText : EditText
    private lateinit var main_recycleView: RecyclerView
    private lateinit var phrase_message: ArrayList<String>
    private lateinit var guess_button: Button
    private lateinit var phrase_textView: TextView
    private lateinit var letter_textView: TextView

    private val goal_phrase = "welcome to coding dojo"
    private var guessed_letter = ""
    private var myAnswer = ""

    private var selection = 1 //for whole phrase
    private var count = 0

    private val myAnswerDictionary = mutableMapOf<Int, Char>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for(i in goal_phrase.indices){
            if(goal_phrase[i] == ' '){
                myAnswerDictionary[i] = ' '
                myAnswer += ' '
            }else{
                myAnswerDictionary[i] = '*'
                myAnswer += '*'
            }
        }

        phrase_message = ArrayList()
        main_constraintLayout = findViewById(R.id.main_constraintLayout)

        main_recycleView = findViewById(R.id.main_recycleView)
        main_recycleView.adapter = PhraseAdapter(this,phrase_message)
        main_recycleView.layoutManager = LinearLayoutManager(this)

        phrase_editText = findViewById(R.id.phrase_editText)
        phrase_textView = findViewById(R.id.phrase_textView)
        letter_textView = findViewById(R.id.letter_textView)

        guess_button = findViewById<Button>(R.id.guss_button)
        guess_button.setOnClickListener { Guss_the_Phrase() }

        update()
    }

    fun Guss_the_Phrase(){
        val input = phrase_editText.text.toString()
        if(selection == 1){
            if(input == goal_phrase){
                phrase_message.add("You Win!!")
                guess_button.isEnabled = false
                phrase_editText.isEnabled = false
            } else {
                phrase_message.add("Incorrect :(")
                selection = 2
                update()
            }
        } else{
            if(input != ""){
                if(input.length == 1){
                    selection = 1
                    myAnswer = ""
                    checkLetters(input[0])
                } else{
                    Snackbar.make(main_constraintLayout, "Invalid input, you should enter only one letter", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        phrase_editText.text.clear()
        phrase_editText.clearFocus()
    }

    fun update(){
        phrase_textView.text = "Phrase:  " + myAnswer.toUpperCase()
        letter_textView.text = "Guessed Letters:  " + guessed_letter
        if(selection == 1){
            phrase_editText.hint = "Guess the full phrase"
        }else{
            phrase_editText.hint = "Guess a letter"
        }
    }

    fun checkLetters(guessedLetter: Char){
        var found = 0
        for(i in goal_phrase.indices){
            if(goal_phrase[i] == guessedLetter){
                myAnswerDictionary[i] = guessedLetter
                found++
            }
        }
        for(i in myAnswerDictionary){myAnswer += myAnswerDictionary[i.key]}
        if(myAnswer==goal_phrase){
            phrase_message.add("You Win!!")
            guess_button.isEnabled = false
            phrase_editText.isEnabled = false
        }
        if(guessed_letter.isEmpty()){guessed_letter+=guessedLetter}else{guessed_letter+=", "+guessedLetter}
        if(found>0){
            phrase_message.add("Found $found ${guessedLetter.toUpperCase()}(s)")
        }else{
            phrase_message.add("No ${guessedLetter.toUpperCase()}s found")
        }
        count++
        val guessesLeft = 10 - count
        if(count<10){phrase_message.add("$guessesLeft guesses remaining")}
        update()
        main_recycleView.scrollToPosition(phrase_message.size - 1)
    }
}