package com.example.myapplication.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Card;
import com.example.myapplication.model.Folder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FolderRepository {
    private ArrayList<Folder> allFolders = new ArrayList<>();
    public ArrayList<Folder> getAllFolders(String th, OnGetDataListener listener){
        DatabaseReference rr = FirebaseDatabase.getInstance().getReference("Folders");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.child("uid").getValue(String.class);
                    String author = ds.child("author").getValue(String.class);
                    String title = ds.child("title").getValue(String.class);
                    List<Card> cards = new ArrayList<>();
                    for (DataSnapshot cardSnapshot: ds.child("cards").getChildren()) {
                        Card card = cardSnapshot.getValue(Card.class);
                        cards.add(card);
                    }
                    String did = ds.child("FolderId").getValue(String.class);

                    Folder thisFolder = new Folder(did, uid, title, author, cards);
                    if (th.equals(uid)) {

                        allFolders.add(thisFolder);

                    }
                }
                listener.onSuccess(allFolders);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MainActivity:", "Can't fetch all folders");
            }
        };
        rr.addListenerForSingleValueEvent(eventListener);
        return allFolders;
    }
    public void addToFirebase(String folderId, Folder folder){
        FirebaseDatabase.getInstance().getReference("Folders").child(folderId)
                .setValue(folder).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("Users").child(folder.getUid()).child("MyFolders").child(folderId).setValue(folderId);
;
                        }
                        else{
                            Log.d("FolderRepository:", "Error");
                        }
                    }
                });
    }
    public void updateCardInDatabase(String folderId, Integer cardUuid, Card card, DatabaseReference ref) {
        ref.child(folderId).child("cards").child(cardUuid.toString()).setValue(card);
    }

}

