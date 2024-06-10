package com.example.myapplication.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean> isUserLoggedIn;

    public LoginViewModel() {
        mAuth = FirebaseAuth.getInstance();
        isUserLoggedIn = new MutableLiveData<>();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        isUserLoggedIn.setValue(currentUser != null);
    }
    public LiveData<Boolean> isUserLoggedIn() {
        return isUserLoggedIn;
    }


    public void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        isUserLoggedIn.setValue(true);
                    } else {
                        isUserLoggedIn.setValue(false);
                    }
                });
    }
}


