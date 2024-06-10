package com.example.myapplication.viewmodel;

import android.util.Patterns;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistrationViewModel extends ViewModel {
    private FirebaseAuth mAuth;

    public RegistrationViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(String username, String email, String password1, String password2, OnRegistrationListener listener) {
        if (username.isEmpty()) {
            listener.onValidationError("Введите имя пользователя.");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            listener.onValidationError("Введите корректный пароль.");
            return;
        } else if (password1.isEmpty() || password1.length() < 6) {
            listener.onValidationError("Пароль должен содерержать минимум 6 знаков.");
            return;
        } else if (!password1.equals(password2)) {
            listener.onValidationError("Пароли не совпадают.");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        user.updateProfile(profileUpdates);

                        User newUser = new User(username, email, new ArrayList<>());
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(newUser)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        listener.onRegistrationSuccess("Регистрация прошла успешно " + username);
                                    } else {
                                        listener.onRegistrationFailure("Ошибка. Попробуйте повторно.");
                                    }
                                });
                    } else {
                        listener.onRegistrationFailure("Ошибка. Попробуйте повторно.");
                    }
                });
    }

    public interface OnRegistrationListener {
        void onValidationError(String message);

        void onRegistrationSuccess(String message);

        void onRegistrationFailure(String message);
    }
}