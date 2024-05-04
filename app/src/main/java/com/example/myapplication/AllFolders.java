package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AllFolders extends AppCompatActivity implements View.OnClickListener{

    ArrayList<Deck> allDecks = new ArrayList<>();
    ArrayList<String> myDeckKeys = new ArrayList<>();
    Map<String, Deck> keyedDecks = new HashMap<>();
    private ListView lvDecks;

    private boolean isGuest = false;
    private boolean inPublic = true;

    FirebaseDatabase rootRef;
    FirebaseAuth mAuth;


    private ImageView addDeck;
    private TextView myDecks, publicDecks, logout;
    private SearchView svDecks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_folders);

        rootRef = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //check if current user is a guest:
        isGuest = checkGuest();

        if ((ArrayList<Deck>) getIntent().getSerializableExtra("allDecks") != null){
            allDecks = (ArrayList<Deck>) getIntent().getSerializableExtra("allDecks");
        }

        if (!isGuest) {
            DatabaseReference thisUser = rootRef.getReference("Users").child(mAuth.getCurrentUser().getUid())
                    .child("MyDecks");
            getUserData(thisUser, new OnGetDataListener() {

                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onFailure() {
                }
            });
        }


        //get the users decks
        if (!isGuest) {
            Log.d("chk_guest", "not guest");
        }

        svDecks = (SearchView) findViewById(R.id.svSearchPublic);
        myDecks = (TextView) findViewById(R.id.tvMyDecks);
        publicDecks = (TextView) findViewById(R.id.tvPublicDecks);
        logout = (TextView) findViewById(R.id.tvLogout);
        addDeck = (ImageView)findViewById(R.id.abPlusPublic);
        lvDecks = (ListView) findViewById(R.id.lvDecksPublic);

        //set logout stuff:
        logout.setTextColor(Color.parseColor("#ff0000"));

        myDecks.setOnClickListener(this);
        addDeck.setOnClickListener(this);
        publicDecks.setOnClickListener(this);
        logout.setOnClickListener(this);


            DeckListAdapter adapter = new DeckListAdapter(isGuest,this, R.layout.folder_lv_item, allDecks);
            lvDecks.setAdapter(adapter);



        lvDecks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Deck> sendDeck = new ArrayList<>();

                    sendDeck = allDecks;

                Intent i = new Intent(AllFolders.this, ViewCard.class);
                i.putExtra("Deck", sendDeck.get(position));
                startActivity(i);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v){
        int id = v.getId();

        if (id == R.id.abPlusPublic) {
            if (isGuest) {
                Toast.makeText(AllFolders.this, "You must be signed in.", Toast.LENGTH_LONG).show();
            } else {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                addCards();

            }
        } else if (id == R.id.tvLogout) {

            logout();


        }
    }

    public boolean checkGuest(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user==null){
            return true;
        }
        return false;
    }


    public void logout(){
        mAuth.signOut();
        Intent i = new Intent(AllFolders.this, Login.class);
        i.putExtra("allDecks", allDecks);
        startActivity(i);
    }


    public void addCards(){
        Intent i = new Intent (AllFolders.this, AllCards.class);
        startActivity(i);
    }


    public void getUserData(DatabaseReference thisUser, final OnGetDataListener listener){


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot){
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    //usr = snapshot.getValue(User.class);


                }




                listener.onSuccess(datasnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure();
            }
        };
        thisUser.addListenerForSingleValueEvent(valueEventListener);
    }

    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(DataSnapshot dataSnapshot);
        void onFailure();
    }
}