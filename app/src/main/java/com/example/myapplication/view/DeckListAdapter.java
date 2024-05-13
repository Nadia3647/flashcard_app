package com.example.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Deck;
import com.example.myapplication.view.EditFolder;
import com.example.myapplication.view.ViewCard;

import java.util.List;

public class DeckListAdapter extends ArrayAdapter<Deck> {

    Context ctx;

    List<Deck> deck;

    public DeckListAdapter( Context ctx, int resource, List<Deck> deck) {
        super( ctx, resource, deck);
        this.ctx = ctx;
        this.deck = deck;
    }

    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);

        View v = inflater.inflate(R.layout.folder_lv_item, null);
        TextView title = (TextView) v.findViewById(R.id.tvDeckName);
        ImageView learn = (ImageView) v.findViewById(R.id.learn);
        ImageView edit = (ImageView) v.findViewById(R.id.btnDelete);
        Deck myDeck = deck.get(position);
        title.setText(myDeck.getTitle());
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Deck", deck.get(position));
                Intent i = new Intent(getContext(), ViewCard.class);
                i.putExtras(bundle);
                ctx.startActivity(i);

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Deck", deck.get(position));
                Intent i = new Intent(getContext(), EditFolder.class);
                i.putExtras(bundle);
                ctx.startActivity(i);

            }
        });
        return v;
    }

}