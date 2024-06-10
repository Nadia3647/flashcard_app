package com.example.myapplication.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    public String username, email;
    public ArrayList<String> myFolders = new ArrayList<>();
    public User(){
    }
    public User(String username, String email, ArrayList<String> myFolders){
        this.username = username;
        this.email = email;
        this.myFolders = myFolders;

    }
}