package com.example.pinapphomework

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

val CORRECT_PIN="1567"

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

    fun initDeleteButton(){
        val btnDelete:Button=findViewById(R.id.btn_back)
        btnDelete.setOnClickListener{
            enteredText=enteredText.dropLast(1)
            updateEnteredText()
        }
    }

    fun initAcceptButton(){
        val btnOK:Button=findViewById(R.id.btn_ok)
        btnOK.setOnClickListener{checkPin()}
    }

    fun checkPin() {
        if(enteredText== CORRECT_PIN){Toast.makeText(this,R.string.correct_pin, Toast.LENGTH_SHORT).show()}
        else{Toast.makeText(this,R.string.incorrect_pin, Toast.LENGTH_SHORT).show()}
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
                val clickedNum = view.text
                enteredText += clickedNum
                updateEnteredText()
            } else {
                val pintext: TextView = findViewById(R.id.pin_text)
                pintext.text = "Pin is limited to 4 characters"
                enteredText = "" // Optionally, clear the enteredText after showing the error
                handler.postDelayed({
                    pintext.text = "Enter a pin"
                }, 2200)
            }
        }
    }

    fun updateEnteredText() {
        var pintext: TextView = findViewById(R.id.pin_text)
        pintext.text = enteredText
    }
}


