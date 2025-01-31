package com.project.androidbingo.ui.addbingo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.project.androidbingo.R

class AddBingoFragment : Fragment() {

    private lateinit var categorySpinner: Spinner
    private lateinit var wordsInput: EditText
    private lateinit var generateButton: Button
    private lateinit var bingoGrid: GridView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_bingo, container, false)

        categorySpinner = view.findViewById(R.id.categorySpinner)
        wordsInput = view.findViewById(R.id.wordsInput)
        generateButton = view.findViewById(R.id.generateButton)
        bingoGrid = view.findViewById(R.id.bingoGrid)

        // Ustaw kategorie w Spinnerze
        val categories = resources.getStringArray(R.array.bingo_categories)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        // Obsługa kliknięcia przycisku generowania planszy
        generateButton.setOnClickListener {
            val category = categorySpinner.selectedItem.toString()
            val words = wordsInput.text.toString().split(",").map { it.trim() }

            if (words.size >= 16) {
                val shuffledWords = words.shuffled().take(16)
                val bingoAdapter = BingoGridAdapter(requireContext(), shuffledWords)
                bingoGrid.adapter = bingoAdapter
            } else {
                Toast.makeText(requireContext(), "Wprowadź co najmniej 16 słów/liczb", Toast.LENGTH_SHORT).show()
            }
        }

        // Obsługa kliknięcia pola w planszy
        bingoGrid.setOnItemClickListener { _, _, position, _ ->
            val adapter = bingoGrid.adapter as BingoGridAdapter
            adapter.toggleItem(position)
        }

        return view
    }
}