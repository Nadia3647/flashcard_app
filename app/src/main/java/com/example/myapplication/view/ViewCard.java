package com.example.myapplication.view;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Card;
import com.example.myapplication.model.Folder;
import com.example.myapplication.viewmodel.LearnCardViewModel;

import java.util.Queue;

public class ViewCard extends AppCompatActivity implements View.OnClickListener {
    private LearnCardViewModel viewModel;
    private TextView  tvTitle, tvCard;
    private TextView o,t,tr,f,fi;
    private ImageView iv;
    private EditText et;
    private ImageView check, see;
    private TextView result;
    Folder thisFolder;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        viewModel = new ViewModelProvider(this).get(LearnCardViewModel.class);
        tvCard = (TextView) findViewById(R.id.lvCard);
        o = (TextView) findViewById(R.id.textView1);
        t = (TextView) findViewById(R.id.textView2);
        tr = (TextView) findViewById(R.id.textView3);
        f = (TextView) findViewById(R.id.textView4);
        fi = (TextView) findViewById(R.id.textView5);
        iv = (ImageView) findViewById(R.id.imgIn);
        see = (ImageView) findViewById(R.id.imageView6);
        result = (TextView) findViewById(R.id.res);
        result.setVisibility(View.INVISIBLE);
        et = (EditText) findViewById(R.id.edt);
        et.setVisibility(View.INVISIBLE);
        check = (ImageView) findViewById(R.id.imgCheck);
        check.setVisibility(View.INVISIBLE);
        iv.setOnClickListener(this);
        o.setOnClickListener(this);
        t.setOnClickListener(this);
        tr.setOnClickListener(this);
        f.setOnClickListener(this);
        fi.setOnClickListener(this);
        see.setOnClickListener(this);
        tvCard.setOnClickListener(this);
        thisFolder = (Folder)getIntent().getSerializableExtra("Deck");
        viewModel.initializeFolder(thisFolder);
        viewModel.getTrainingCardsQueueLiveData().observe(this, new Observer<Queue<Card>>() {
            @Override
            public void onChanged(Queue<Card> queue) {

                if (viewModel.check() == true) {
                    tvCard.setText(viewModel.getItem1());

                } else {
                    Intent i = new Intent(ViewCard.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int id = v.getId();
         if (id == R.id.imgIn){


            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    result.setVisibility(View.VISIBLE);
                    if(et.getText().toString().equals(viewModel.getItem2())){
                        result.setText("Всё верно, оцените сложность карточки");
                    } else {
                        result.setText("Неверно, истинное значение: " + viewModel.getItem2());
                    }

                }
            });
            et.setVisibility(View.VISIBLE);
            check.setVisibility(View.VISIBLE);
        }

        else if (id == R.id.imageView6) {

            tvCard.setText(viewModel.getItem2());
            tvCard.setTypeface(Typeface.DEFAULT_BOLD);

        } else if (id == R.id.textView1){
            viewModel.addToQueue();
            viewModel.NextOrEndForTraining();
             result.setVisibility(View.INVISIBLE);
             et.setVisibility(View.INVISIBLE);
             check.setVisibility(View.INVISIBLE);

        } else if (id == R.id.textView2) {
            viewModel.setCardParamAndProceed(2, thisFolder);
             result.setVisibility(View.INVISIBLE);
             et.setVisibility(View.INVISIBLE);
             check.setVisibility(View.INVISIBLE);

        } else if (id == R.id.textView3) {
            viewModel.setCardParamAndProceed(3, thisFolder);
             result.setVisibility(View.INVISIBLE);
             et.setVisibility(View.INVISIBLE);
             check.setVisibility(View.INVISIBLE);

        } else if (id == R.id.textView4) {
            viewModel.setCardParamAndProceed(4, thisFolder);
             result.setVisibility(View.INVISIBLE);
             et.setVisibility(View.INVISIBLE);
             check.setVisibility(View.INVISIBLE);

        }
        else if (id == R.id.textView5) {
            viewModel.setCardParamAndProceed(5, thisFolder);
            result.setVisibility(View.INVISIBLE);
             et.setVisibility(View.INVISIBLE);
             check.setVisibility(View.INVISIBLE);

        }
        else {
            throw new IllegalStateException("Ошибка");
        }
    }
}
