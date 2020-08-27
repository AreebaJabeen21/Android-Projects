package com.example.complin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

public class myComplainActivity extends AppCompatActivity {
    private MyDatabaseHelper myDatabaseHelper;
    private RecyclerView objectRecyclerView;

    private complainAdapter objectComplainAdpter;

    Context context;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_complain);

        try {
            objectRecyclerView=findViewById(R.id.show_img);
            myDatabaseHelper= new MyDatabaseHelper(this);
        } catch (Exception e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public Void getData(View view){
        try {
            objectComplainAdpter = new complainAdapter(myDatabaseHelper.getAllData());
       objectRecyclerView.setHasFixedSize(true);

       objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        objectRecyclerView.setAdapter(objectComplainAdpter);
        } catch (Exception e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
        return null;
    }
}