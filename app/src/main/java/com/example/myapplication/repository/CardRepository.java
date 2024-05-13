package com.example.myapplication.repository;

import com.example.myapplication.model.Card;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//public class CardRepository {

//    public ArrayList<Card> getAllCard(String deckId){
  //      FirebaseDatabase.getInstance().getReference("Decks").child(deckId).child("cards");
    //    List<Card> cards = new ArrayList<>();
      //  for (DataSnapshot cardSnapshot: ds.child("cards").getChildren()) {
        //    Card card = cardSnapshot.getValue(Card.class);
          //  cards.add(card);
        //}
    //}
//    public void updateCardInDatabase(String deckId, Integer cardUuid, Card card) {
     //   FirebaseDatabase.getInstance().getReference("Decks").child(deckId).child("cards").child(cardUuid.toString()).setValue(card);
  //  }
//}
