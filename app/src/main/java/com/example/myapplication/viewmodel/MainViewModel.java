package com.example.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Deck;
import com.example.myapplication.repository.FolderRepository;
import com.example.myapplication.repository.OnGetDataListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private FolderRepository deckRepository;
    FirebaseDatabase rootRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String uid = currentUser.getUid();
    private MutableLiveData<ArrayList<Deck>> allDecks;

    public MainViewModel() {
        deckRepository = new FolderRepository();
        allDecks = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Deck>> getAllDecks() {
        return allDecks;
    }

    public void fetchAllDecks() {
        deckRepository.getAllDecks(uid, new OnGetDataListener() {
            @Override
            public void onSuccess(ArrayList<Deck> decks) {
                allDecks.setValue(decks); // здесь произошли изменения
            }

            @Override
            public void onFailure() {
                // Обработайте ошибку
            }
        });

    }
}