package com.project.androidbingo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.datepicker.MaterialDatePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class activity_register extends AppCompatActivity {

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

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        editBirthdate.setOnClickListener(v -> {
            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener(selection -> {
                long selectedDateInMillis = selection;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = new Date(selectedDateInMillis);
                editBirthdate.setText(dateFormat.format(date));
            });
        });

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
