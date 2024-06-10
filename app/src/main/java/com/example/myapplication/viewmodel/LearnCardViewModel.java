package com.example.myapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.MemoAlgo;
import com.example.myapplication.model.Card;
import com.example.myapplication.model.Folder;
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

    public void initializeFolder(Folder thisFolder) {
        try {
            Queue<Card> q = getTrainingQueue(thisFolder.getCards());
            trainingCardsQueueLiveData.setValue(q);
            currentCard=q.poll();
            Log.d("debug", "Empty folder"+getTrainingQueue(thisFolder.getCards()).size());

        }
        catch(Exception e){
            Log.d("debug", "Empty folder");
        }
    }

    public LiveData<Queue<Card>> getTrainingCardsQueueLiveData() { return trainingCardsQueueLiveData; }

    public void NextOrEndForTraining() {
        Queue<Card> queue = trainingCardsQueueLiveData.getValue();
        currentCard = queue.poll();
        trainingCardsQueueLiveData.setValue(queue);
    }

    public String getFolderTitle(Folder thisFolder){
        return thisFolder.getTitle();
    }
    public String getItem2(){
        return currentCard.getItem2();
    }
    public String getItem1(){
        return currentCard.getItem1();
    }
    public void setCardParamAndProceed(int quality, Folder thisFolder) {
        setCardParam(quality, thisFolder);
        NextOrEndForTraining();
    }
    public void addToQueue(){
        Queue<Card> queue = trainingCardsQueueLiveData.getValue();
        queue.add(currentCard);
        trainingCardsQueueLiveData.setValue(queue);
    }

    private void setCardParam(int quality, Folder thisFolder) {
        if (quality != 1){
            MemoAlgo.SuperMemo2(currentCard, quality);
            updateCardInDatabase(thisFolder.getFolderId().toString(),currentCard.getUuid(), currentCard, FirebaseDatabase.getInstance().getReference("Folders"));
        }
    }
    public boolean check (){
        if (currentCard != null){
            Log.d("debug", "Pfikj");
            return true;
        }
        return false;
    }

    public void updateCardInDatabase(String folderId, Integer cardUuid, Card card, DatabaseReference ref) {
        ref.child(folderId).child("cards").child(cardUuid.toString()).setValue(card);
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