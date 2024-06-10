package com.example.myapplication.repository;

import com.example.myapplication.model.Folder;

import java.util.ArrayList;

public interface OnGetDataListener {
    //this is for callbacks
    void onSuccess(ArrayList<Folder> folders);

    void onFailure();
}
