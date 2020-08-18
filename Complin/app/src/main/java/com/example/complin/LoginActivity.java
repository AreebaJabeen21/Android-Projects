package com.example.complin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText et_email,et_password;
    Button buttonLogin;
    MyDatabaseHelper myDatabaseHelper;
    String email,password;
    String filename="login";
    TextView tv_register,tv_login;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        buttonLogin=findViewById(R.id.button_login);
        tv_register=findViewById(R.id.tv_register);
        tv_login=findViewById(R.id.tv_login);


        sharedPreferences=getSharedPreferences(filename,Context.MODE_PRIVATE);
        if (sharedPreferences.contains(email)){
            Intent i =new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }
        myDatabaseHelper= new MyDatabaseHelper(this);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=et_email.getText().toString();
                password=et_password.getText().toString();
                if (email.equals("") && password.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please Insert Email and password",Toast.LENGTH_LONG).show();
                } else {
                    int status =Integer.parseInt( myDatabaseHelper.getLoginData(email, password));
                    if (status>0) {
                        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "You are not Registerd!", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Registration_Activity.class);
                startActivity(i);

            }
        });
//
    }
}