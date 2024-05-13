package com.example.myapplication.viewmodel;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.MemoAlgo;
import com.example.myapplication.model.Card;
import com.example.myapplication.model.Deck;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LearnCardViewModel  extends ViewModel {

    private MutableLiveData<Queue<Card>> trainingCardsQueueLiveData = new MutableLiveData<>();

    private Card currentCard;

    public void initializeDeck(Deck thisDeck) {
        try {
            Queue<Card> q = getTrainingQueue(thisDeck.getCards());
            trainingCardsQueueLiveData.setValue(q);
            currentCard=q.poll();
            Log.d("debug", "Empty deck"+getTrainingQueue(thisDeck.getCards()).size());

        }
        catch(Exception e){
            Log.d("debug", "Empty deck");
        }
    }

    public LiveData<Queue<Card>> getTrainingCardsQueueLiveData() { return trainingCardsQueueLiveData; }

    public void NextOrEndForTraining() {
        Queue<Card> queue = trainingCardsQueueLiveData.getValue();
            currentCard = queue.poll();
            trainingCardsQueueLiveData.setValue(queue);
    }

    public String getDeckTitle(Deck thisDeck){
        return thisDeck.getTitle();
    }
    public String getItem2(){
        return currentCard.getItem2();
    }
    public String getItem1(){
        return currentCard.getItem1();
    }
    public void setCardParamAndProceed(int quality, Deck thisDeck) {
        setCardParam(quality, thisDeck);
        NextOrEndForTraining();
    }
    public void addToQueue(){
        Queue<Card> queue = trainingCardsQueueLiveData.getValue();
        queue.add(currentCard);
        trainingCardsQueueLiveData.setValue(queue);
    }

    private void setCardParam(int quality, Deck thisDeck) {
        if (quality != 1){
            MemoAlgo.SuperMemo2(currentCard, quality);
            updateCardInDatabase(thisDeck.getDeckId().toString(),currentCard.getUuid(), currentCard, FirebaseDatabase.getInstance().getReference("Decks"));
        }
    }
    public boolean check (){
        if (currentCard != null){
            Log.d("debug", "Pfikj");
            return true;
        }
        return false;
    }

    public void updateCardInDatabase(String deckId, Integer cardUuid, Card card, DatabaseReference ref) {
        ref.child(deckId).child("cards").child(cardUuid.toString()).setValue(card);
    }

    private Queue<Card> getTrainingQueue(List<Card> cards) {
        Queue<Card> revision_queue = new LinkedList<>();
        for (Card card : cards) {
            Log.d("debug", " card " +card.getDayNextPractice());
            if (card.getDayNextPractice() <= giveCurrentDate().getDayOfMonth()
                    && card.getMonthNextPractice()<=(giveCurrentDate().getMonthValue())
                    && card.getYearNextPractice() <= giveCurrentDate().getYear()) {
                revision_queue.add(card);
            }
        }
        List<Card> list_to_shuffle = new LinkedList<>(revision_queue);
        Collections.shuffle(list_to_shuffle);
        revision_queue = new LinkedList<>(list_to_shuffle);
        return revision_queue;
    }

    public static LocalDate giveCurrentDate() {
        return LocalDate.now();
    }
}