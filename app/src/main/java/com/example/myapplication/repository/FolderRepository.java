package com.example.myapplication.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Card;
import com.example.myapplication.model.Deck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FolderRepository {
    private ArrayList<Deck> allDecks = new ArrayList<>();
    public ArrayList<Deck> getAllDecks(String th, OnGetDataListener listener){
        DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Decks");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.child("uid").getValue(String.class);
                    String author = ds.child("author").getValue(String.class);
                    String title = ds.child("title").getValue(String.class);
                    List<Card> cards = new ArrayList<>();
                    for (DataSnapshot cardSnapshot: ds.child("cards").getChildren()) {
                        Card card = cardSnapshot.getValue(Card.class);
                        cards.add(card);
                    }
                    String did = ds.child("deckId").getValue(String.class);

                    Deck thisDeck = new Deck(did, uid, title, author, cards);
                    if (th.equals(uid)) {

                        allDecks.add(thisDeck);

                    }
                }
                listener.onSuccess(allDecks);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MainActivity:", "Can't fetch all decks");
            }
        };
        rr.addListenerForSingleValueEvent(eventListener);
        Log.d("Main:", "decks" + allDecks.size());
        return allDecks;
    }
    public void addToFirebase(String deckId, Deck deck){
        FirebaseDatabase.getInstance().getReference("Decks").child(deckId)
                .setValue(deck).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("Users").child(deck.getUid()).child("MyDecks").child(deckId).setValue(deckId);
;
                        }
                        else{
                        }
                    }
                });
    }
    public void updateCardInDatabase(String deckId, Integer cardUuid, Card card, DatabaseReference ref) {
        ref.child(deckId).child("cards").child(cardUuid.toString()).setValue(card);
    }

}

