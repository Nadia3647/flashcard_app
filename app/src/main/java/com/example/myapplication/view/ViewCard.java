package com.example.myapplication.view;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;
import com.example.myapplication.model.Card;
import com.example.myapplication.model.Deck;
import com.example.myapplication.viewmodel.LearnCardViewModel;

import java.util.Queue;

public class ViewCard extends AppCompatActivity implements View.OnClickListener {
    private LearnCardViewModel viewModel;
    private TextView  tvTitle, tvCard;
    private TextView o,t,tr,f,fi;
    Deck thisDeck;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        viewModel = new ViewModelProvider(this).get(LearnCardViewModel.class);
        tvTitle = (TextView) findViewById(R.id.tvCardTitle);
        tvCard = (TextView) findViewById(R.id.lvCard);
        o = (TextView) findViewById(R.id.textView1);
        t = (TextView) findViewById(R.id.textView2);
        tr = (TextView) findViewById(R.id.textView3);
        f = (TextView) findViewById(R.id.textView4);
        fi = (TextView) findViewById(R.id.textView5);
        o.setOnClickListener(this);
        t.setOnClickListener(this);
        tr.setOnClickListener(this);
        f.setOnClickListener(this);
        fi.setOnClickListener(this);
        tvCard.setOnClickListener(this);
        thisDeck = (Deck)getIntent().getSerializableExtra("Deck");
        viewModel.initializeDeck(thisDeck);
        viewModel.getTrainingCardsQueueLiveData().observe(this, new Observer<Queue<Card>>() {
            @Override
            public void onChanged(Queue<Card> queue) {

                if (viewModel.check() == true) {
                    // No cards to train
                    tvTitle.setText(viewModel.getDeckTitle(thisDeck));
                    tvCard.setText(viewModel.getItem1());

                } else {
                    Intent i = new Intent(ViewCard.this, MainActivity2.class);
                    startActivity(i);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.lvCard) {
            tvTitle.setText(viewModel.getDeckTitle(thisDeck));
            tvCard.setText(viewModel.getItem2());
            tvCard.setTypeface(Typeface.DEFAULT_BOLD);

        } else if (id == R.id.textView1){
            viewModel.addToQueue();
            viewModel.NextOrEndForTraining();

        } else if (id == R.id.textView2) {
            viewModel.setCardParamAndProceed(2, thisDeck);

        } else if (id == R.id.textView3) {
            viewModel.setCardParamAndProceed(3, thisDeck);

        } else if (id == R.id.textView4) {
            viewModel.setCardParamAndProceed(4, thisDeck);

        }
        else if (id == R.id.textView5) {
            viewModel.setCardParamAndProceed(5, thisDeck);

        }
        else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }
    }
}
