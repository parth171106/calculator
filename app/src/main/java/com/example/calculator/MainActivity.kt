package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var workingsTV: TextView
    private lateinit var resultsTV: TextView

    private var currentNumber: String = ""
    private var previousNumber: String = ""
    private var operation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        workingsTV = findViewById(R.id.workingsTV)
        resultsTV = findViewById(R.id.resultsTV)
    }

    fun operatorAction(view: View) {
        val button = view as Button

        if (currentNumber.isNotEmpty()) {
            if (previousNumber.isEmpty()) {
                previousNumber = currentNumber
            }

            currentNumber = ""
            operation = button.text.toString()
            workingsTV.text = "$previousNumber $operation "
        } else {

        }
    }



    fun numberAction(view: View) {
        val button = view as Button
        currentNumber += button.text
        workingsTV.text = "$previousNumber $operation $currentNumber"
    }





    fun equalsAction(view: View) {
        if (previousNumber.isNotEmpty() && currentNumber.isNotEmpty() && operation != null) {
            val result = when (operation) {
                "+" -> (previousNumber.toDouble() + currentNumber.toDouble()).toString()
                "-" -> (previousNumber.toDouble() - currentNumber.toDouble()).toString()
                "x" -> (previousNumber.toDouble() * currentNumber.toDouble()).toString()
                "/" -> {
                    if (currentNumber.toDouble() != 0.0) {
                        (previousNumber.toDouble() / currentNumber.toDouble()).toString()
                    } else {
                        "Error"
                    }
                }
                else -> "Error"
            }

            resultsTV.text = result
            resetCalculator()
        }
    }


    fun allClearAction(view: View) {
        resetCalculator()
        workingsTV.text = ""
        resultsTV.text = ""
    }


    fun backSpaceAction(view: View) {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
            workingsTV.text = currentNumber
        }
    }


    private fun resetCalculator() {
        currentNumber = ""
        previousNumber = ""
        operation = null
    }
}
