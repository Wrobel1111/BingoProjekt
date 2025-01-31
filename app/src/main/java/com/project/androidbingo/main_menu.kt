package com.project.androidbingo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.project.androidbingo.databinding.MainMenuBinding

class MainMenu : Fragment() {

    private var _binding: MainMenuBinding? = null
    private val binding get() = _binding!!
    private val numOfButtons = 4
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

        val buttonNames = listOf("Ksiazki", "Podroze", "Filmy", "Kreatywne")

        for (i in 1..numOfButtons) {
            val tempButton: Button = view.findViewById(
                view.resources.getIdentifier("button_bingo_$i", "id", "com.project.androidbingo")
            )
            allButtons.add(tempButton)
            tempButton.text = buttonNames[i - 1]

            tempButton.setOnClickListener {
                tempButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple))
                Toast.makeText(activity, "Twoje bingo: ${tempButton.text}", Toast.LENGTH_SHORT).show()

                val intent = Intent(activity, BingoActivity::class.java)
                intent.putExtra("button_name", tempButton.text.toString())
                findNavController().navigate(R.id.bingoActivity)
            }
        }

        val startButton = view.findViewById<Button>(R.id.button)
        startButton.setOnClickListener {
            val intent = Intent(activity, BingoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
