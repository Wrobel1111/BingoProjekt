package com.project.androidbingo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_register extends Activity {

    private EditText editName, editSurname, editEmail, editBirthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.editName);
        editSurname = findViewById(R.id.editSurname);
        editEmail = findViewById(R.id.editEmail);
        editBirthdate = findViewById(R.id.editBirthdate);
        Button registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String surname = editSurname.getText().toString();
            String email = editEmail.getText().toString();
            String birthdate = editBirthdate.getText().toString();

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || birthdate.isEmpty()) {
                Toast.makeText(activity_register.this, "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity_register.this, "Rejestracja zakończona pomyślnie!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
