package com.example.myapplication.view;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    private TextInputEditText editTextEmail, editTextPassword;
    private Button buttonLog;
    private TextView linkReg;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        editTextEmail = findViewById(R.id.edtLoginEmail);
        editTextPassword = findViewById(R.id.edtPass);
        buttonLog = findViewById(R.id.btnLogin);
        linkReg = findViewById(R.id.tvEnter);

        loginViewModel.getIsUserLoggedIn().observe(this, isLoggedIn -> {
            if (isLoggedIn) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Login.this, "Возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        });

        linkReg.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Registration.class);
            startActivity(intent);
            finish();
        });

        buttonLog.setOnClickListener(view -> {
            String email = String.valueOf(editTextEmail.getText());
            String password = String.valueOf(editTextPassword.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Login.this, "Введите почту", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(Login.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            loginViewModel.signInWithEmailAndPassword(email, password);
        });
    }
}