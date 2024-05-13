package com.example.myapplication.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Card;
import com.example.myapplication.model.Deck;
import com.example.myapplication.repository.FolderRepository;
import com.example.myapplication.view.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EditFolderViewModel extends ViewModel {

    private MutableLiveData<List<Card>> cards = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<String> title = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isSaved = new MutableLiveData<>(false);
    private FolderRepository deckRepository;
    private Deck thisDeck;
    public void initializeCards(Deck thisDeck) {
        try {
            cards.setValue(thisDeck.getCards());
        }
        catch(Exception e){
            Log.d("debug", "Empty deck");
        }
    }

    public LiveData<List<Card>> getCards() {
        return cards;
    }


    public void addCard() {
        Card newCard = new Card("", "", giveCurrentDay(), giveCurrentMonth(), giveCurrentYear(), giveCurrentDay(), giveCurrentMonth(), giveCurrentYear(), 0, 0, 0, cards.getValue().size());
        List<Card> updatedCards = cards.getValue();
        updatedCards.add(newCard);
        cards.setValue(updatedCards);
    }

    public void save() {
        for (Card card : cards.getValue()) {
            deckRepository.updateCardInDatabase(thisDeck.getDeckId().toString(), card.getUuid(), card, FirebaseDatabase.getInstance().getReference("Decks"));
        }
        isSaved.setValue(true);
    }

    public static int giveCurrentDay() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getDayOfMonth();
    }

    public static int giveCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonthValue();
    }

    public static int giveCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear();
    }
}