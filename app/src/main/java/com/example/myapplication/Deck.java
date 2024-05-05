package com.example.myapplication;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;

public class Deck  implements Serializable {
    String title, author, Uid, deckId;
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
    public List<Card> getCardsToLearnList() {
        List<Card> toLearnList = new ArrayList<Card>();

        Iterator<Card> iterator = cards.iterator();
        Predicate<Card> pred = x -> x.getState() == 2;

        while (iterator.hasNext()) {
            Card card = iterator.next();

            if (pred.test(card)) {
                toLearnList.add(card);
            }
        }

        return toLearnList;
    }
    public Queue<Card> getTrainingQueue() {
        List<Card> toLearnList = new ArrayList<Card>();

        Queue<Card> revision_queue = new LinkedList<>();
        Predicate<Card> pred = x -> (x.getDayNextPractice()==giveCurrentDate().getDayOfMonth()
                && x.getMonthNextPractice()==giveCurrentDate().getMonth().toString()
                && x.getYearNextPractice() == giveCurrentDate().getYear());

        Iterator<Card> iterator = revision_queue.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();

            if (pred.test(card)) {
                revision_queue.add(card);
            }
        }

        // Shuffle the queue
        List<Card> list_to_shuffle = new LinkedList<>(revision_queue);
        Collections.shuffle(list_to_shuffle);
        revision_queue = new LinkedList<>(list_to_shuffle);

        return revision_queue;
    }
    public static LocalDate toDate(long nextPracticeTime) {
        LocalDate nextPracticeDate = new java.util.Date(nextPracticeTime).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (nextPracticeDate);
    }

    public static LocalDate giveCurrentDate() {
        return LocalDate.now();
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
}