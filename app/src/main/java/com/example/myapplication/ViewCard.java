package com.example.myapplication;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Deck;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.awt.font.NumericShaper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

public class ViewCard extends AppCompatActivity implements View.OnClickListener {
    private List<Card> cards = new ArrayList<>();

    private TextView tvShuffle, tvOrder, tvInfo, tvTitle, tvCard;
    private ImageView ivShuffle, ivOrder, ivInfo;

    private Deck thisDeck;


    // Layout
    private Button mSeeAnswerButton;
    private Button Button1;
    private Button Button2;
    private Button Button3;
    private Button Button4;
    private Button Button5;

    private TextView mTextViewQuestion;
    private TextView mTextViewItem2;
    private TextView cardsLeftText;

    private ImageView speaker;



    // Variables
    private Card currCard;
    private Queue<Card> processedCardsQueue;
    private Card currentCard;
    private static Queue<Card> trainingCardsQueue;

    private int langDirection;
    private int inc;


    // Debug
    Card memoryCard;

    // Thread



    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);


        tvTitle = (TextView) findViewById(R.id.tvCardTitle);
        tvCard = (TextView) findViewById(R.id.tvCard);
        try {
            thisDeck = (Deck) getIntent().getSerializableExtra("Deck");
            cards = thisDeck.getCards();


        }
        catch(Exception e){
            Log.d("debug", "Empty deck");
        }
        trainingCardsQueue = getTrainingQueue(cards);
        if (trainingCardsQueue.isEmpty()) {
            // No cards to train
            showEndOfRevision();

        } else {
            // Start cards scroll
            NextOrEndForTraining();
        }

    }
    public Queue<Card> getTrainingQueue(List<Card> cards) {
        Queue<Card> revision_queue = new LinkedList<>();


        // Проходим по списку cards, а не по revision_queue
        for (Card card : cards) {
            Log.d("debug", " card " +card.getDayNextPractice());
            Log.d("debug", " now " +giveCurrentDate().getDayOfMonth());
            Log.d("debug", " card " +card.getDayNextPractice());
            Log.d("debug", " now " +giveCurrentDate().getDayOfMonth());
            if (card.getDayNextPractice() == giveCurrentDate().getDayOfMonth()
                    && card.getMonthNextPractice().equalsIgnoreCase(giveCurrentDate().getMonth().toString())
                    && card.getYearNextPractice() == giveCurrentDate().getYear()) {
                revision_queue.add(card);
            }
        }

        // Перемешиваем очередь
        List<Card> list_to_shuffle = new LinkedList<>(revision_queue);
        Collections.shuffle(list_to_shuffle);
        revision_queue = new LinkedList<>(list_to_shuffle);

        return revision_queue;
    }
    private void setCardParam(int quality) {
        // If the quality is not 1, card is not repeated so it is considered processed
        if (quality != 1){
            // Change card values
//            currentCard.setState(Param.ACTIVE);
            memoryCard = currentCard;
            MemoAlgo.SuperMemo2(currentCard, quality);

            processedCardsQueue.add(currentCard);

        }
    }



    public static LocalDate giveCurrentDate() {
        return LocalDate.now();
    }



    /**
     * Check if there is another card in the queue to be trained, show the question side if so.
     * Otherwise, save training data in global deck and in datafile and show end of training screen.
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void NextOrEndForTraining() {

        if(cards != null){
            currCard = cards.get(inc);
        }

        if (currCard != null) {

            showQuestionSide();
        }
        else {
            showEndOfRevision();
        }
    }

    /**
     * Manage the clicks on the seeAnswerButton and the 4 answerButtons, as well as the
     * backToMenuButton
     *
     * @param v view pressed by the user
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onClick(View v) {

        if (v == tvCard) {
            showAnswerSide();

        } else if (v == Button1){
            trainingCardsQueue.add(currCard);
            NextOrEndForTraining();

        } else if (v == Button2) {
            setCardParam(2);
            inc++;
            NextOrEndForTraining();

        } else if (v == Button3) {
            setCardParam(3);
            NextOrEndForTraining();

        } else if (v == Button4) {
            setCardParam(4);
            NextOrEndForTraining();

        }
        else if (v == Button5) {
        setCardParam(5);
        NextOrEndForTraining();
        }
        else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }
    }


    /**
     * Set the end of revision layout when the user is done with the training
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void showEndOfRevision() {


        Intent i = new Intent(ViewCard.this, MainActivity2.class);
        startActivity(i);

    }

    /**
     * Set the layout for the question side for the current card
     */
    private void showQuestionSide() {


        tvTitle.setText(thisDeck.title);
        tvCard.setText(this.currCard.getItem1().toString());
        tvCard.setTypeface(Typeface.DEFAULT_BOLD);
    }

    /**
     * Update cards remaining progress bar
     */

    /**
     * Set the layout for the answer side for the current card
     */
    private void showAnswerSide() {

        tvTitle.setText(thisDeck.title);
        tvCard.setText(currCard.getItem2().toString());
        tvCard.setTypeface(Typeface.DEFAULT_BOLD);
    }

//    /**
//     * Set the saving layout when the user is done with the training, before end of revision layout
//     */
//    @RequiresApi(api = Build.VERSION_CODES.R)
//    private void showSavingLayout() {
//        setContentView(R.layout.saving_layout);
//
//        ProgressBar progressBar = findViewById(R.id.saving_progress_bar);
//        progressBar.setProgress(0);
//
////        Param.GLOBAL_DECK.updateDeckAndDatabaseFromQueue(processedCardsQueue, progressBar);
//        showEndOfRevision();
//    }


}