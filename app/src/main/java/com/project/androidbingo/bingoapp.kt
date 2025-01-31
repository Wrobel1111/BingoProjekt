package com.project.androidbingo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class BingoActivity : AppCompatActivity() {

    private lateinit var gridCells: Array<Array<TextView>>
    private lateinit var selectedCells: Array<Array<Boolean>>
    private val gridSize = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bingo_layout)

        val inputWords = findViewById<EditText>(R.id.inputWords)
        val startButton = findViewById<Button>(R.id.startBingoButton)
        val bingoGrid = findViewById<GridLayout>(R.id.bingoGrid)

        gridCells = Array(gridSize) { Array(gridSize) { TextView(this) } }
        selectedCells = Array(gridSize) { Array(gridSize) { false } }

        startButton.setOnClickListener {
            val words = inputWords.text.toString().split(",").map { it.trim() }.filter { it.isNotEmpty() }
            if (words.size == 16) {
                bingoGrid.removeAllViews()
                bingoGrid.layoutParams = (bingoGrid.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                var index = 0
                for (i in 0 until gridSize) {
                    for (j in 0 until gridSize) {
                        val cell = TextView(this).apply {
                            text = words[index++]
                            setPadding(32, 32, 32, 32)
                            textSize = 22f
                            gravity = Gravity.CENTER
                            setTextColor(ContextCompat.getColor(this@BingoActivity, android.R.color.white))
                            val shape = GradientDrawable()
                            shape.shape = GradientDrawable.OVAL
                            shape.setColor(ContextCompat.getColor(this@BingoActivity, R.color.blue))
                            background = shape
                            layoutParams = ViewGroup.LayoutParams(300, 300)
                            setOnClickListener {
                                selectedCells[i][j] = !selectedCells[i][j]
                                val newColor = if (selectedCells[i][j]) {
                                    ContextCompat.getColor(this@BingoActivity, R.color.purple)
                                } else {
                                    ContextCompat.getColor(this@BingoActivity, R.color.blue)
                                }
                                shape.setColor(newColor)
                                background = shape
                                checkBingo()
                            }
                        }
                        gridCells[i][j] = cell
                        bingoGrid.addView(cell)
                    }
                }
            }
        }
    }

    private fun checkBingo() {
        for (i in 0 until gridSize) {
            if ((0 until gridSize).all { j -> selectedCells[i][j] }) {
                showBingoMessage()
                return
            }
        }
        for (j in 0 until gridSize) {
            if ((0 until gridSize).all { i -> selectedCells[i][j] }) {
                showBingoMessage()
                return
            }
        }
        if ((0 until gridSize).all { i -> selectedCells[i][i] }) {
            showBingoMessage()
            return
        }
        if ((0 until gridSize).all { i -> selectedCells[i][gridSize - 1 - i] }) {
            showBingoMessage()
            return
        }
    }

    private fun showBingoMessage() {
        Toast.makeText(this, "BINGO!", Toast.LENGTH_LONG).show()
    }
}
