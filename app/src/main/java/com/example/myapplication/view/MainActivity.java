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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Folder;
import com.example.myapplication.viewmodel.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private MainViewModel viewModel;
    ArrayList<Folder> allFolders = new ArrayList<>();
    private ListView lvFolders;
    FirebaseAuth mAuth;

    private TextView addFolder, publiFolders, logout;
    private SearchView svFolders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_folders);
        publiFolders = (TextView) findViewById(R.id.tvPublicDecks);
        logout = (TextView) findViewById(R.id.tvLogout);
        addFolder = (TextView)findViewById(R.id.textView6);
        lvFolders = (ListView) findViewById(R.id.lvDecksPublic);
        logout.setTextColor(Color.parseColor("#ff0000"));
        addFolder.setOnClickListener(this);
        logout.setOnClickListener(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.fetchAllFolders();
        viewModel.getAllFolders().observe(this, new Observer<ArrayList<Folder>>() {
            @Override
            public void onChanged(ArrayList<Folder> folders) {

                FolderListAdapter adapter = new FolderListAdapter(MainActivity.this, R.layout.folder_lv_item, folders);
                lvFolders.setAdapter(adapter);
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
        i.putExtra("allFolders", allFolders);
        startActivity(i);
    }
    public void addCards(){
        Intent i = new Intent (MainActivity.this, AddNewFolder.class);
        startActivity(i);
    }
}
