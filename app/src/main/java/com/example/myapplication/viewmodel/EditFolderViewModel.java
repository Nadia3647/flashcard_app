package com.example.myapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Card;
import com.example.myapplication.model.Folder;
import com.example.myapplication.repository.FolderRepository;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EditFolderViewModel extends ViewModel {

    private MutableLiveData<List<Card>> cards = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<String> title = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isSaved = new MutableLiveData<>(false);
    private FolderRepository folderRepository;
    private Folder thisFolder;
    public void initializeCards(Folder thisFolder) {
        try {
            cards.setValue(thisFolder.getCards());
        }
        catch(Exception e){
            Log.d("debug", "Empty folder");
        }
    }

    public LiveData<List<Card>> getCards() {

        return cards;
    }


    public void addCard() {
        Card newCard = new Card("m", "m", giveCurrentDay(), giveCurrentMonth(), giveCurrentYear(), giveCurrentDay(), giveCurrentMonth(), giveCurrentYear(), 0, 0, 0, cards.getValue().size());
        List<Card> updatedCards = cards.getValue();
        updatedCards.add(newCard);
        cards.setValue(updatedCards);
    }

    public void save() {
        for (Card card : cards.getValue()) {
            folderRepository.updateCardInDatabase(thisFolder.getFolderId().toString(), card.getUuid(), card, FirebaseDatabase.getInstance().getReference("Folders"));
        }
        isSaved.setValue(true);
    }

    public void updateCards(List<Card> updatedCards) {
        cards.setValue(updatedCards);
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