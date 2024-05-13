package com.example.myapplication.view;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Deck;
import com.example.myapplication.viewmodel.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private MainViewModel viewModel;
    ArrayList<Deck> allDecks = new ArrayList<>();
    private ListView lvDecks;
    FirebaseAuth mAuth;

    private TextView addDeck, publicDecks, logout;
    private SearchView svDecks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_folders);
        publicDecks = (TextView) findViewById(R.id.tvPublicDecks);
        logout = (TextView) findViewById(R.id.tvLogout);
        addDeck = (TextView)findViewById(R.id.textView6);
        lvDecks = (ListView) findViewById(R.id.lvDecksPublic);
        //set logout stuff:
        logout.setTextColor(Color.parseColor("#ff0000"));
        addDeck.setOnClickListener(this);
        publicDecks.setOnClickListener(this);
        logout.setOnClickListener(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.fetchAllDecks();
        viewModel.getAllDecks().observe(this, new Observer<ArrayList<Deck>>() {
            @Override
            public void onChanged(ArrayList<Deck> decks) {

                DeckListAdapter adapter = new DeckListAdapter(MainActivity.this, R.layout.folder_lv_item, decks);
                lvDecks.setAdapter(adapter);
            }
        });

        lvDecks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this, ViewCard.class);
                i.putExtra("Deck", allDecks.get(position));
                startActivity(i);
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v){
        int id = v.getId();

        if (id == R.id.textView6) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            addCards();
        } else if (id == R.id.tvLogout) {
            logout();
        }
    }

    public void logout(){
        mAuth.signOut();
        Intent i = new Intent(MainActivity.this, Login.class);
        i.putExtra("allDecks", allDecks);
        startActivity(i);
    }
    public void addCards(){
        Intent i = new Intent (MainActivity.this, AddNewFolder.class);
        startActivity(i);
    }
}
