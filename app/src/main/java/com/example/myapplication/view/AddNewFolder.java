package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.viewmodel.AddNewFolderViewModel;


public class AddNewFolder extends AppCompatActivity implements View.OnClickListener {
    private TextView tvFront, tvBack, tvDone, tvAdd;
    private AddNewFolderViewModel viewModel;
    private EditText edtTitle, edtFront, edtBack;
    private ImageView ivSave, ivAdd;
    String  username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        viewModel = new ViewModelProvider(this).get(AddNewFolderViewModel.class);
    }
    @Override
    public void onClick(View v){
        int id = v.getId();
        if (id == R.id.ivAdd || id == R.id.tvAddAnother) {
            //добавление карты в приложении
            if (!edtFront.getText().toString().equals("") && !edtBack.getText().toString().equals("")){
                viewModel.addCards(edtFront.getText().toString(), edtBack.getText().toString());
                edtFront.setText("");
                edtBack.setText("");
                edtFront.requestFocus();
            }
                else{
                Toast.makeText(this, "Incomplete card." ,Toast.LENGTH_SHORT);
            }

        } else if (id == R.id.ivSave || id == R.id.tvDone) {
            if (!edtFront.getText().toString().equals("") && !edtBack.getText().toString().equals("")){
                viewModel.addCards(edtFront.getText().toString(), edtBack.getText().toString());
            }
            viewModel.createNewFolder(edtTitle.getText().toString(), username);
            Intent i = new Intent(AddNewFolder.this, MainActivity.class);
            startActivity(i);

        }
    }
}