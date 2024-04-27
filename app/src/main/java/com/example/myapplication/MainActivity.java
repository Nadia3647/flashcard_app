package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Deck> allDecks = new ArrayList<>();
    private FirebaseAuth mAuth;
    private boolean isGuest = false;
    FirebaseDatabase rootRef;
    private ProgressBar pbMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        rootRef = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        pbMain = (ProgressBar) findViewById(R.id.pbLoadDecks);
        pbMain.setVisibility(View.VISIBLE) ;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();

        isGuest = checkGuest();
        readAllDecks(uid,rootRef.getReference("Decks"), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onFailure() {
            }
        });

    }

    public FirebaseAuth getmAuth(){
        mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }
    public FirebaseUser getCurrUser(){
        mAuth = getmAuth();
        FirebaseUser user = mAuth.getCurrentUser();
        return user;
    }

    //get all decks from firebase database:
    public void readAllDecks(String th,DatabaseReference rr, final OnGetDataListener listener){

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.child("uid").getValue(String.class);
                    String author = ds.child("author").getValue(String.class);
                    String title = ds.child("title").getValue(String.class);
                    List<Card> cards = (List<Card>) ds.child("cards").getValue();
                    String did = ds.child("deckId").getValue(String.class);

                    Deck thisDeck = new Deck(did, uid, title, author, cards);
                    if (th.equals(uid)) {
                        allDecks.add(thisDeck);
                    }
                }
                listener.onSuccess(dataSnapshot);
                Intent i;
                if(isGuest){
                    i = new Intent(MainActivity.this, Login.class);
                }
                else{
                    i = new Intent(MainActivity.this, AllFolders.class);

                }
                i.putExtra("allDecks", allDecks);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MainActivity:", "Can't fetch all decks");
            }
        };
        rr.addListenerForSingleValueEvent(eventListener);
    }

    public boolean checkGuest(){
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user==null){
            return true;
        }
        return false;
    }

    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(DataSnapshot dataSnapshot);
        void onFailure();
    }

    public ArrayList<Deck> getAllDecks(){
        return allDecks;
    }


}