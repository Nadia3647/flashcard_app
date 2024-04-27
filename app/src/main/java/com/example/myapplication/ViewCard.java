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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
    private static Queue<Card> trainingCardsQueue;

    private int langDirection;
    private int i;


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
            Serializable serializable = getIntent().getSerializableExtra("Deck");
            if (serializable instanceof Deck) {
                thisDeck = (Deck) serializable;
                Log.d("debug", "1" + thisDeck.cards);
                cards = thisDeck.getCards();
                Log.d("debug", "2" + cards.toString());
                currCard = cards.get(0);
                if (currCard != null) {
                    Log.d("debug", "3" + currCard);
                    tvTitle.setText(thisDeck.title);
                    tvCard.setText(currCard.getItem1());
                } else {
                    Log.d("debug", "Error: cards is null or empty");
                }
            } else {
                Log.d("debug", "Error: Object is not of type Deck");
            }
        } catch (ClassCastException e) {
            Log.e("debug", "Error while getting Deck from Intent", e);
        }


    }


    public static LocalDate toDate(long nextPracticeTime) {
        LocalDate nextPracticeDate = new java.util.Date(nextPracticeTime).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (nextPracticeDate);
    }

    private void setCardParam(int quality) {
        // If the quality is not 1, card is not repeated so it is considered processed
        if (quality != 1){
            // Change card values
//            currentCard.setState(Param.ACTIVE);
            memoryCard = currCard;
            MemoAlgo.SuperMemo2(currCard, quality);


        }
    }

    /**
     * Check if there is another card in the queue to be trained, show the question side if so.
     * Otherwise, save training data in global deck and in datafile and show end of training screen.
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void NextOrEndForTraining() {

        if(cards != null){
            currCard = cards.get(i);
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
            i++;
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