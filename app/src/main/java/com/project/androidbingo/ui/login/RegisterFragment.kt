package com.project.androidbingo.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.project.androidbingo.R
import com.project.androidbingo.databinding.FragmentRegisterBinding
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        val loadingProgressBar = view.findViewById<ProgressBar>(R.id.loading)
        val editName = view.findViewById<EditText>(R.id.editName)
        val editSurname = view.findViewById<EditText>(R.id.editSurname)
        val editEmail = view.findViewById<EditText>(R.id.editEmail)
        val editBirthdate = view.findViewById<EditText>(R.id.editBirthdate)
        val registerButton = view.findViewById<Button>(R.id.register_button)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        editBirthdate.setOnClickListener {
            parentFragmentManager.let { datePicker.show(it, "DATE_PICKER") }
            datePicker.addOnPositiveButtonClickListener { selection: Long ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = Date(selection)
                editBirthdate.setText(dateFormat.format(date))
            }
        }

        registerButton.setOnClickListener {
            val name = editName.text.toString().trim()
            val surname = editSurname.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val birthdate = editBirthdate.text.toString().trim()

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || birthdate.isEmpty()) {
                Toast.makeText(context, "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_SHORT).show()
            } else {
                //Zapisanie do pliku
                saveUserProfile(requireContext(), name, surname, email, birthdate)

                //Zapisanie rejestracji w SharedPrefernces
                val sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("name", name)
                    putString("surname", surname)
                    putString("email", email)
                    putString("birthdate", birthdate)
                    putBoolean("isRegistered", true) //ustawienie rejestracji
                    apply()
                }

                Toast.makeText(context, "Rejestracja zakończona pomyślnie!", Toast.LENGTH_SHORT).show()

                // przejscie do ekranu glownego
                findNavController().navigate(R.id.action_registerFragment_to_mainMenu)
            }
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { loginResult ->
            loginResult ?: return@Observer
            loadingProgressBar.visibility = View.GONE
            loginResult.error?.let { showLoginFailed(it) }
            loginResult.success?.let { updateUiWithUser(it) }
        })
    }

    private fun saveUserProfile(context: Context, name: String, surname: String, email: String, birthdate: String) {
        val userProfile = JSONObject()
        userProfile.put("name", name)
        userProfile.put("surname", surname)
        userProfile.put("email", email)
        userProfile.put("birthdate", birthdate)

        val file = File(context.filesDir, "user_profile.json")
        try {
            FileWriter(file).use { writer -> writer.write(userProfile.toString()) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
