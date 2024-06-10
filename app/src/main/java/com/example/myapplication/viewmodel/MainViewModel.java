package com.example.myapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Folder;
import com.example.myapplication.repository.FolderRepository;
import com.example.myapplication.repository.OnGetDataListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private FolderRepository folderRepository;
    FirebaseDatabase rootRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String uid = currentUser.getUid();
    private MutableLiveData<ArrayList<Folder>> allFolders;

    public MainViewModel() {
        folderRepository = new FolderRepository();
        allFolders = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Folder>> getAllFolders() {
        return allFolders;
    }

    public void fetchAllFolders() {
        folderRepository.getAllFolders(uid, new OnGetDataListener() {
            @Override
            public void onSuccess(ArrayList<Folder> folders) {
                allFolders.setValue(folders);
            }

            @Override
            public void onFailure() {
                Log.d("debug", "Error");
            }
        });

    }
}