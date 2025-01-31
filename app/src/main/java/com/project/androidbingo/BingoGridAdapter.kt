package com.project.androidbingo.ui.addbingo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button

class BingoGridAdapter(
    private val context: Context,
    private val items: List<String>
) : BaseAdapter() {

    // Tablica do śledzenia stanu zaznaczenia każdego pola
    private val checkedItems = BooleanArray(items.size) { false }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button = if (convertView == null) {
            Button(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        } else {
            convertView as Button
        }

        // Ustaw tekst przycisku na podstawie pozycji
        button.text = items[position]

        // Ustaw kolor tła przycisku na podstawie stanu zaznaczenia
        button.setBackgroundColor(
            if (checkedItems[position]) android.graphics.Color.GREEN else android.graphics.Color.LTGRAY
        )

        return button
    }

    // Metoda do przełączania stanu zaznaczenia danego pola
    fun toggleItem(position: Int) {
        checkedItems[position] = !checkedItems[position]
        notifyDataSetChanged() // Odśwież widok
    }
}