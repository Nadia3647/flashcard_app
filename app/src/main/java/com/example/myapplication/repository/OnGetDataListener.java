package com.example.myapplication.repository;

import com.example.myapplication.model.Deck;

import java.util.ArrayList;

public interface OnGetDataListener {
    //this is for callbacks
    void onSuccess(ArrayList<Deck> decks);

    void onFailure();
}
