package com.project.androidbingo.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.project.androidbingo.R
import org.json.JSONObject
import java.io.File
import java.io.IOException

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameTextView = view.findViewById<TextView>(R.id.profile_name)
        val surnameTextView = view.findViewById<TextView>(R.id.profile_surname)
        val emailTextView = view.findViewById<TextView>(R.id.profile_email)
        val birthdateTextView = view.findViewById<TextView>(R.id.profile_birthdate)

        val userData = loadUserProfile(requireContext())

        if (userData != null) {
            nameTextView.text = "Imię: ${userData.optString("name", "Brak danych")}"
            surnameTextView.text = "Nazwisko: ${userData.optString("surname", "Brak danych")}"
            emailTextView.text = "E-mail: ${userData.optString("email", "Brak danych")}"
            birthdateTextView.text = "Data urodzenia: ${userData.optString("birthdate", "Brak danych")}"
        }
    }

    private fun loadUserProfile(context: Context): JSONObject? {
        val file = File(context.filesDir, "user_profile.json")
        if (!file.exists()) return null

        return try {
            val json = file.readText()
            JSONObject(json)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
