package com.project.androidbingo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.androidbingo.databinding.MainMenuBinding
import androidx.core.content.ContextCompat
import android.os.Handler
import android.widget.Toast





/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainMenu : Fragment() {

    private var _binding: MainMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val numOfButtons = 4 //liczba przycisków do bingo na ekranie głównym
                                 //Chwilowo© będzie na sztywno
    val allButtons = mutableListOf<Button>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainMenuBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lista nazw dla przycisków
        val buttonNames = listOf("Ksiazki","Podroze", "Filmy", "Kreatywne")

        for (i in 1..numOfButtons)
        {
            val tempButton : Button = view.findViewById(view.resources.getIdentifier("button_bingo_"+i,"id", "com.project.androidbingo"))
            allButtons.add(tempButton)
            // Ustaw nazwę dla przycisku z listy buttonNames
            tempButton.text = buttonNames[i - 1]



            tempButton.setOnClickListener {

                tempButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple))
                Toast.makeText(activity, "Twoje bingo: ${tempButton.text}", Toast.LENGTH_SHORT).show()

                val intent = Intent(activity, current_bingo::class.java)
                intent.putExtra("button_name", tempButton.toString())
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}