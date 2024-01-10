package com.example.pinapphomework

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

val CORRECT_PIN = "1567"
val KEY_COUNTER = "key_pin"

class MainActivity : AppCompatActivity() {

    var enteredText = ""
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNumButton()
        initDeleteButton()
        initAcceptButton()
    }

    fun initDeleteButton() {
        val btnDelete: Button = findViewById(R.id.btn_back)
        btnDelete.setOnClickListener {
            if (enteredText.isNotEmpty()) {
                enteredText = enteredText.dropLast(1)
                updateEnteredText()
            }
        }

        btnDelete.setOnLongClickListener {
            enteredText = ""
            updateEnteredText()
            true
        }
    }


    fun initAcceptButton() {
        val btnOK: Button = findViewById(R.id.btn_ok)
        btnOK.setOnClickListener { checkPin() }
    }

    fun checkPin() {
        if (
            enteredText == CORRECT_PIN) {
            Toast.makeText(this, R.string.correct_pin, Toast.LENGTH_SHORT).show()
            val intet = Intent(this, ResultActivity::class.java)
            startActivity(intet)
        } else {
            initColor(this)
            Toast.makeText(this, R.string.incorrect_pin, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initColor(context: Context) {
        val errorColor = ContextCompat.getColor(context, R.color.red)
        val pinDigits = listOf(
            findViewById<TextView>(R.id.pin_digit_one),
            findViewById<TextView>(R.id.pin_digit_two),
            findViewById<TextView>(R.id.pin_digit_three),
            findViewById<TextView>(R.id.pin_digit_four)
        )

        pinDigits.forEach { it.setTextColor(errorColor) }

        handler.postDelayed({
            val defaultColor = ContextCompat.getColor(context, R.color.primary_text)
            pinDigits.forEach { it.setTextColor(defaultColor) }
        }, 1500)
    }


    fun updateEnteredText() {
        val pinDigits = listOf(
            findViewById<TextView>(R.id.pin_digit_one),
            findViewById<TextView>(R.id.pin_digit_two),
            findViewById<TextView>(R.id.pin_digit_three),
            findViewById<TextView>(R.id.pin_digit_four)
        )

        pinDigits.forEachIndexed { index, textView ->
            textView.text = if (index < enteredText.length) enteredText[index].toString() else "•"
        }
    }

    fun initNumButton() {
        val btnNums: List<Int> = listOf(
            R.id.btn_numone,
            R.id.btn_numtwo,
            R.id.btn_numthree,
            R.id.btn_numfour,
            R.id.btn_numfive,
            R.id.btn_numsix,
            R.id.btn_numseven,
            R.id.btn_numeight,
            R.id.btn_numnine,
            R.id.btn_numzero,
        )
        for (btnID in btnNums) {
            val btn: Button = findViewById(btnID)
            btn.setOnClickListener(this::someNumClicked)
        }
    }

    fun someNumClicked(view: View) {
        if (view !is Button) {
            return
        } else {
            if (enteredText.length < 4) {
                val clickedNum = view.text.toString()
                enteredText += clickedNum
                updateEnteredText()
            } else {
                val messageText: TextView = findViewById(R.id.message_text)
                messageText.text = "Pin is limited to 4 characters"
                enteredText = "" // Optionally, clear the enteredText after showing the error
                updateEnteredText() // Update the PIN view to reflect the cleared text
                handler.postDelayed({
                    messageText.text = "Enter a pin"
                }, 2200)
            }
        }
    }




    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d("MainActivity", "onSaveInstanceState() is called")

        outState.putString(KEY_COUNTER, enteredText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        Log.d("MainActivity", "onRestoreInstanceState() is called")

        enteredText = savedInstanceState.getString(KEY_COUNTER).toString()
    }
}


