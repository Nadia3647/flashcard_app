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
import com.example.myapplication.model.Folder;
import com.example.myapplication.viewmodel.EditFolderViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditFolder extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDone, tvAdd;
    private EditText edtTitle;
    private ImageView ivSave, ivAdd;
    private Folder thisFolder;
    private EditFolderViewModel viewModel;

    private ListView lvCard;
    private CardListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_folder);
        viewModel = new ViewModelProvider(this).get(EditFolderViewModel.class);
        lvCard = (ListView) findViewById(R.id.lvCard);
        edtTitle = (EditText) findViewById(R.id.edtAddTitle);
        ivSave = (ImageView) findViewById(R.id.ivSave) ;
        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        thisFolder = (Folder) getIntent().getSerializableExtra("Folder");

        viewModel.initializeCards(thisFolder);
        viewModel.getCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> card) {
                adapter = new CardListAdapter(EditFolder.this, R.layout.card_lv_item, card);
                lvCard.setAdapter(adapter);
            }
        });


        ivAdd.setOnClickListener(this);
        ivSave.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivAdd) {
            viewModel.addCard();
        } else if (id == R.id.ivSave) {
            List<Card> currentCards = new ArrayList<>();
            for (int i = 0; i < lvCard.getCount(); i++) {
                View view = lvCard.getChildAt(i);
                EditText edtText = view.findViewById(R.id.edtBack);
                EditText edtText2 = view.findViewById(R.id.edtFront);
                String text = edtText.getText().toString();
                String text2 = edtText.getText().toString();
                Card card = (Card) adapter.getItem(i);
                card.setItem1(text);
                card.setItem1(text2);
                currentCards.add(card);
            }

            viewModel.updateCards(currentCards);

            Intent i = new Intent(EditFolder.this, MainActivity.class);
            startActivity(i);
        }
    }
}