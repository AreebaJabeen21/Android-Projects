package com.example.complin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {
    private EditText category,severity,description;
Context context;
    Button btn_capture;
    private ImageView objectImageView;
    Button report;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    MyDatabaseHelper myDatabaseHelper= new MyDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        category=findViewById(R.id.category);
        severity=findViewById(R.id.severity);
        description=findViewById(R.id.description);
        objectImageView=findViewById(R.id.img);
        btn_capture = findViewById(R.id.capture);
        report=findViewById(R.id.report);


        if(ContextCompat.checkSelfPermission(ReportActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ReportActivity.this,
                    new String[]{
                        Manifest.permission.CAMERA
                    },100);
        }

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
             imageToStore= (Bitmap) data.getExtras().get("data");
            objectImageView.setImageBitmap(imageToStore);
        }
    }
    public void storeImage(View view)
    {
        try {
            if(!category.getText().toString().isEmpty() && !severity.getText().toString().isEmpty() && !description.getText().toString().isEmpty()&& objectImageView.getDrawable()!=null && imageToStore!=null){
                myDatabaseHelper.storecomplain(new ModelClass(
                        category.getText().toString(),
                        description.getText().toString(),
                        severity.getText().toString(),
                        imageToStore));
            }
            else {
                Toast.makeText(this,"Fill all fields carefully",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }




    }
}
