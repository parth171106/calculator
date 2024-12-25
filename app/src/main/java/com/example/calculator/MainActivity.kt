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

        // Set up padding for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize TextViews
        workingsTV = findViewById(R.id.workingsTV)
        resultsTV = findViewById(R.id.resultsTV)
    }

    fun operatorAction(view: View) {
        val button = view as Button

        if (currentNumber.isNotEmpty()) {
            if (previousNumber.isEmpty()) {
                previousNumber = currentNumber // Initialize previousNumber if it's empty
            }

            currentNumber = "" // Clear the current number after storing the previous one
            operation = button.text.toString() // Set the current operation
            workingsTV.text = "$previousNumber $operation" // Display the current operation in the workings TextView
        } else {
            // Optionally show an error message if no number is entered before the operation
            // e.g., Toast.makeText(this, "Enter a number first", Toast.LENGTH_SHORT).show()
        }
    }


    // Handles number button clicks
    fun numberAction(view: View) {
        val button = view as Button
        currentNumber += button.text
        workingsTV.text = currentNumber
    }




    // Handles the "=" button
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

    // Handles the "AC" button
    fun allClearAction(view: View) {
        resetCalculator()
        workingsTV.text = ""
        resultsTV.text = ""
    }

    // Handles the "Del" button
    fun backSpaceAction(view: View) {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
            workingsTV.text = currentNumber
        }
    }

    // Resets the calculator state
    private fun resetCalculator() {
        currentNumber = ""
        previousNumber = ""
        operation = null
    }
}
