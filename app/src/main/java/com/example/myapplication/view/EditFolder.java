package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Card;
import com.example.myapplication.model.Deck;
import com.example.myapplication.viewmodel.EditFolderViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditFolder extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDone, tvAdd;
    private EditText edtTitle;
    private ImageView ivSave, ivAdd;
    private Deck thisDeck;
    private EditFolderViewModel viewModel;

    private ListView lvCard;
    private CardListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_folder);
        viewModel = new ViewModelProvider(this).get(EditFolderViewModel.class);
        lvCard = (ListView) findViewById(R.id.lvCard);
        tvDone = (TextView) findViewById(R.id.tvDone);
        tvAdd = (TextView) findViewById(R.id.tvAddAnother);
        edtTitle = (EditText) findViewById(R.id.edtAddTitle);
        ivSave = (ImageView) findViewById(R.id.ivSave) ;
        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        thisDeck = (Deck) getIntent().getSerializableExtra("Deck");

        viewModel.initializeCards(thisDeck);
        viewModel.getCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> card) {
                adapter = new CardListAdapter(EditFolder.this, R.layout.card_lv_item, card);
                lvCard.setAdapter(adapter);
            }
        });


        ivAdd.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        tvDone.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivAdd || id == R.id.tvAddAnother) {
            viewModel.addCard();

        } else if (id == R.id.ivSave || id == R.id.tvDone) {
            viewModel.save();
            Intent i = new Intent(EditFolder.this, MainActivity.class);
            startActivity(i);
        }
    }
}