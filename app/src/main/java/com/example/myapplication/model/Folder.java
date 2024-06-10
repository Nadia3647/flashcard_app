package com.example.myapplication.model;

import java.io.Serializable;
import java.util.List;

public class Folder implements Serializable {
    private String title, author, Uid, folderId;
    private List<Card> cards;

    public Folder(){

    }

    public Folder(String folderId, String Uid, String title, String author, List<Card> cards){
        this.folderId = folderId;
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
    public String getFolderId() {return folderId;}
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

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }
}