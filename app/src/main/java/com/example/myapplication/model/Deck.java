package com.example.myapplication.model;
import com.example.myapplication.model.Card;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Deck  implements Serializable {
    private String title, author, Uid, deckId;
    private List<Card> cards;

    public Deck(){

    }

    public Deck (String deckId, String Uid, String title, String author, List<Card> cards){
        this.deckId = deckId;
        this.title = title;
        this.author = author;
        this.cards = cards;
        this.Uid = Uid;
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getDeckId() {return deckId;}
    public String getUid(){
        return Uid;
    }
    public List<Card> getCards(){
        return cards;
    }
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }
}