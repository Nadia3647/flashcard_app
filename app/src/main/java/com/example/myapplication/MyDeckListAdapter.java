package com.example.myapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDeckListAdapter extends ArrayAdapter<Card> {
    Context ctx;
    List<Card> card;

    public MyDeckListAdapter(Context ctx, int resource, List<Card> card) {
        super(ctx, resource, card);
        this.ctx = ctx;
        this.card = card;
    }

    public MyDeckListAdapter(Context ctx, int resource) {
        super(ctx, resource);
        this.ctx = ctx;
        this.card = new ArrayList<>(); // Создаем пустой список card
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.card_lv_item, null);
        EditText edtFront = (EditText) v.findViewById(R.id.edtFront);
        EditText edtBack = (EditText) v.findViewById(R.id.edtBack);

        if (card != null && !card.isEmpty() && card.size() > position) {
            Card myCard = card.get(position);
            edtFront.setText(myCard.getItem1());
            edtBack.setText(myCard.getItem2());
        } else {
            // Если card не передан или пустой, оставляем EditText пустыми
            edtFront.setText("");
            edtBack.setText("");
        }
        edtFront.addTextChangedListener(new TextWatcher() {
            Card myCard = card.get(position);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myCard.setItem1(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        edtBack.addTextChangedListener(new TextWatcher() {
            Card myCard = card.get(position);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myCard.setItem2(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return v;
    }

}