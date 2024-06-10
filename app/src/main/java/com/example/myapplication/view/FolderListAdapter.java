package com.example.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Folder;

import java.util.List;

public class FolderListAdapter extends ArrayAdapter<Folder> {

    Context ctx;

    List<Folder> folder;

    public FolderListAdapter(Context ctx, int resource, List<Folder> folder) {
        super( ctx, resource, folder);
        this.ctx = ctx;
        this.folder = folder;
    }

    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);

        View v = inflater.inflate(R.layout.folder_lv_item, null);
        TextView title = (TextView) v.findViewById(R.id.tvDeckName);
        ImageView learn = (ImageView) v.findViewById(R.id.learn);
        ImageView edit = (ImageView) v.findViewById(R.id.btnDelete);
        Folder myFolder = folder.get(position);
        title.setText(myFolder.getTitle());
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Folder", folder.get(position));
                Intent i = new Intent(getContext(), ViewCard.class);
                i.putExtras(bundle);
                ctx.startActivity(i);

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Folder", folder.get(position));
                Intent i = new Intent(getContext(), EditFolder.class);
                i.putExtras(bundle);
                ctx.startActivity(i);

            }
        });
        return v;
    }

}