package com.example.wheather_app_project;

import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    TextView search_text,temperature ,city,feelslike,atmos,humidity,des;
    ImageButton search_btn;
    TextView result;
    GifImageView background;




    public void search(View view) {
        feelslike=findViewById(R.id.feels_like);
        atmos=findViewById(R.id.atmos);
        humidity=findViewById(R.id.humidity);
        des=findViewById(R.id.description);


        background = findViewById(R.id.background);
        city=findViewById(R.id.city);
        search_text=findViewById(R.id.location);
        search_btn=findViewById(R.id.serachbtn);
//        result = findViewById(R.id.temperature);
        temperature=findViewById(R.id.temperature);
          String cityname = search_text.getText().toString();

        String content;
        Weather weather= new Weather();

        try {
            content= weather.execute("https://openweathermap.org/data/2.5/weather?q="+cityname+"&appid=439d4b804bc8187953eb36d2a8c26a02").get();
            Log.i("contentData",content);

            JSONObject jsonObject = new JSONObject(content);
            String weatherdata = jsonObject.getString("weather");
            String maintemp = jsonObject.getString("main");
            String mainlocation= jsonObject.getString("sys");

            JSONArray jsonArray = new JSONArray(weatherdata);
            String main ="";
            String description="";
            String temp;
            String country;
            String feels_like;
            String r_humidity;

            for (int i=0 ; i< jsonArray.length();i++){
                JSONObject weatherpart = jsonArray.getJSONObject(i);
                description = weatherpart.getString("description");
                main = weatherpart.getString("main");


            }

            JSONObject mainpart = new JSONObject(maintemp);
            temp= mainpart.getString("temp");
            feels_like=mainpart.getString("feels_like");
            r_humidity=mainpart.getString("humidity");
            JSONObject syspart = new JSONObject(mainlocation);
            country=syspart.getString("country");


            Log.i("main",main);
            Log.i("description",description);
            Log.i("temp",temp);
            Log.i("feels_like",feels_like);
            Log.i("country",country);

//            result.setText("Country : "+country+"\nDescription : "+description+"\nTemperature : "+temp+"\nFeels Like : "
//                    +feels_like+"\nMain: "+main );
            city.setText(cityname);
            temperature.setText(temp);
            feelslike.setText(feels_like);
            atmos.setText(main);
            humidity.setText(r_humidity);
            des.setText(description);

//            if(main =="Clouds"){
//                background.setImageResource(R.drawable.clouds);
//
//            }
//            else if(main == "Smoke"){
//                background.setImageResource(R.drawable.sunny);
//
//            }
//            else if(main == "clear"){
//                background.setImageResource(R.drawable.clearsky);
//
//            }
//            else{
//                background.setImageResource(R.drawable.rain);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class Weather extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... adress) {
            try {
                URL url= new URL(adress[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
              connection.connect();

                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                String content="";
                char ch;
                while (data != -1){
                    ch= (char) data;
                    content = content + ch;
                    data = inputStreamReader.read();
                }
                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }












//    TextView city_textView;
//    TextView temp_textView;
//    EditText location_search;
//    ImageView wheather_image;
//
//    ImageButton searchbtn;
//
//
//    public class Weather extends AsyncTask<String,Void,String>{
//
//
//        @Override
//        protected String doInBackground(String...address) {
//            try {
//                URL url = new URL(address[0]);
//                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
//                connection.connect();
//
//                InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//
//                int data = inputStreamReader.read();
//                String content="";
//                char ch;
//                while (data != -1){
//                    ch= (char) data;
//                    content = content + ch;
//                    data = inputStreamReader.read();
//                }
//                return content;
//
//            }
//            catch (MalformedURLException e)
//            {
//                e.printStackTrace();
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
//
//
//    public void search(View view){
//
//        String main="";
//        String desc ="";
//        String temperature;
//        String name;
//
//        city_textView = findViewById(R.id.city);
////        temp_textView = findViewById(R.id.temperature);
////        location_search = findViewById(R.id.location);
//
//   String cityname = city_textView.getText().toString();
////
////        wheather_image = findViewById(R.id.wheather_image);
////        searchbtn = findViewById(R.id.serachbtn);
//
//        String content;
//        Weather weather = new Weather();
//        try {
//            content= weather.execute("https://openweathermap.org/data/2.5/weather?q=dubai&appid=439d4b804bc8187953eb36d2a8c26a02").get();
//            Log.i("contentData",content);
//
//            JSONObject jsonObject = new JSONObject(content);
//            String weatherdata = jsonObject.getString("weather");
////            String mainTemperature = jsonObject.getString("main");
////
////            String namecity = jsonObject.getString("sys");
//
//            Log.i("weahtherData",weatherdata);
//
////            JSONArray jsonArray = new JSONArray(weatherdata);
////
////            for (int i=0 ; i< jsonArray.length();i++){
////                JSONObject weatherpart = jsonArray.getJSONObject(i);
////                desc = weatherpart.getString("desc");
////                main = weatherpart.getString("main");
////                name = weatherpart.getString("name");
////                temperature = weatherpart.getString("temperature");
////
////            }
//
////            JSONObject mainpart = new JSONObject(mainTemperature);
////            temperature = mainpart.getString("temp");
////
////            JSONObject syspart = new JSONObject(namecity);
////            name = syspart.getString("sys");
////
////            Log.i("main",main);
////            Log.i("description",desc);
////            Log.i("description",name);
////            Log.i("description",temperature);
//
////
////            String result_cityname = "Location : "+name;
////            String result_temp = "Temperature : "+temperature;
//
//
////            city_textView .setText(result_cityname);
////
////
////            temp_textView.setText(result_temp);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//
//
//    }


}



