package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.ZoneId;


public class AllCards extends AppCompatActivity implements View.OnClickListener {

    private List<Card> cards = new ArrayList<>();
    private Deck deck = new Deck();
    private TextView tvFront, tvBack, tvDone, tvAdd;
    private EditText edtTitle, edtFront, edtBack;
    private ImageView ivSave, ivAdd;
    String deckId, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        username = user.getDisplayName();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards);

        tvFront = (TextView) findViewById(R.id.tvFront);
        tvBack = (TextView) findViewById(R.id.tvBack);
        tvDone = (TextView) findViewById(R.id.tvDone);
        tvAdd = (TextView) findViewById(R.id.tvAddAnother);
        edtTitle = (EditText) findViewById(R.id.edtAddTitle);
        ivSave = (ImageView) findViewById(R.id.ivSave) ;
        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        edtFront = (EditText) findViewById(R.id.edtFront);
        edtBack = (EditText) findViewById(R.id.edtBack);


        tvAdd.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        tvDone.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //set title name, deck contents
        }
        else{
            Date date = new Date();
            deckId = String.valueOf(date.getTime());
            deckId = deckId.substring(deckId.length()-9);
        }






    }
    @Override
    public void onClick(View v){
        int id = v.getId();
        if (id == R.id.ivAdd || id == R.id.tvAddAnother) {
            if (!edtFront.getText().toString().equals("") && !edtBack.getText().toString().equals("")){
                cards.add((new Card(edtFront.getText().toString(), edtBack.getText().toString(), 2,giveCurrentDay(), giveCurrentMonth(), giveCurrentYear(),  giveCurrentDay(), giveCurrentMonth(), giveCurrentYear(),  0, 0, 0, getNewUUID() )));
                edtFront.setText("");
                edtBack.setText("");
                edtFront.requestFocus();
            }
                else{
                Toast.makeText(this, "Incomplete card." ,Toast.LENGTH_SHORT);
            }

        } else if (id == R.id.ivSave || id == R.id.tvDone) {
            if (!edtFront.getText().toString().equals("") && !edtBack.getText().toString().equals("")){
                cards.add((new Card(edtFront.getText().toString(), edtBack.getText().toString(), 2, giveCurrentDay(), giveCurrentMonth(), giveCurrentYear(),  giveCurrentDay(), giveCurrentMonth(), giveCurrentYear(), 0, 0, 0, getNewUUID() )));

            }
            deck.setCards(cards);
            deck.title = edtTitle.getText().toString();
            deck.author = username;
            deck.Uid = FirebaseAuth.getInstance().getUid();
            deck.deckId = deckId;
            if (cards.size() == 0){

                Intent i = new Intent(AllCards.this, AllFolders.class);
                startActivity(i);

            }


            //add deck to firebase:

            FirebaseDatabase.getInstance().getReference("Decks").child(deckId)
                    .setValue(deck).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                FirebaseDatabase.getInstance().getReference("Users").child(deck.Uid).child("MyDecks").child(deckId).setValue(deckId);


                                Intent i = new Intent(AllCards.this, MainActivity.class);
                                startActivity(i);

                            }
                            else{
                                Toast.makeText(AllCards.this, "Failed to upload new deck.", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }
    }
    public static String getNewUUID() {
        return UUID.randomUUID().toString();
    }


    public static LocalDate toDate(long nextPracticeTime) {
        LocalDate nextPracticeDate = new java.util.Date(nextPracticeTime).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (nextPracticeDate);
    }

    public static int giveCurrentDay() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getDayOfMonth();
    }
    public static String giveCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonth().toString();
    }
    public static int giveCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear();
    }
}