package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.viewmodel.RegistrationViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText edtUser, edtEmail, edtPass1, edtPass2;
    TextView btnRegister;
    ProgressBar progressBar;
    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        btnRegister = findViewById(R.id.tvRegisterUser);
        edtUser = findViewById(R.id.edtLoginEmail);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass1 = findViewById(R.id.edtPassword1);
        edtPass2 = findViewById(R.id.edtPassword2);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvRegisterUser) {
            String username = edtUser.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password1 = edtPass1.getText().toString();
            String password2 = edtPass2.getText().toString();

            viewModel.registerUser(username, email, password1, password2, new RegistrationViewModel.OnRegistrationListener() {
                @Override
                public void onValidationError(String message) {
                    // Handle validation error
                    Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRegistrationSuccess(String message) {
                    // Handle successful registration
                    Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show();
                    // Navigate to next screen or perform necessary actions
                }

                @Override
                public void onRegistrationFailure(String message) {
                    // Handle registration failure
                    Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}