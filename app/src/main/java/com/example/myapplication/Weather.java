package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather extends AppCompatActivity {
    EditText city;
    Button changeCity, weatherToFirebase, weatherToSQLite;
    TextView weatherCondition, temperature, humid;
    ImageView img;
    String citySwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        changeCity = (Button) findViewById(R.id.changeCityBttn);
        weatherToFirebase = (Button) findViewById(R.id.weatherToFirebase);
        weatherToSQLite = (Button) findViewById(R.id.weatherToSQLite);

        city = (EditText) findViewById(R.id.fieldCity);

        weatherCondition = (TextView) findViewById(R.id.weatherText);
        temperature = (TextView) findViewById(R.id.tempText);
        humid = (TextView) findViewById(R.id.humidText);

        img = (ImageView) findViewById(R.id.iconWeather);

//        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        citySwitch = sharedPreferences.getString("city", "");
//        if(citySwitch.equalsIgnoreCase(""))
            citySwitch = "Berlin";

            String weatherWebserviceURL = "https://api.openweathermap.org/data/2.5/weather?q=" +
                    citySwitch + "&appid=57566f8a85334da085d3142a74aab807&units=metric";
            weather(weatherWebserviceURL);


        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                citySwitch = city.getText().toString();

                String weatherWebserviceURL = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        citySwitch +
                        "&appid=57566f8a85334da085d3142a74aab807&units=metric";

                weather(weatherWebserviceURL);
            }
        });

        weatherToFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Weather.this, MainActivity.class));

            }
        });
        weatherToSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Weather.this, SQLiteDatabase.class));
            }
        });
    }
    public void weather(String url) {
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Rayan", "Response Received");
                Log.d("Rayan", response.toString());

                try {
                    JSONObject jsonMain = response.getJSONObject("main");

                    String temp = String.valueOf(jsonMain.getDouble("temp"));
                    String humidity = String.valueOf(jsonMain.getDouble("humidity"));

                    Log.d("Rayan", "Temp = " + String.valueOf(temp));
                    Log.d("Rayan", "humidity = " + String.valueOf(humidity));

                    temperature.setText("Temperature: " + temp + "°C");
                    humid.setText("Humidity: " + humidity);


                    JSONArray jsonArray = response.getJSONArray("weather");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("Rayan", jsonArray.getString(i));

                        JSONObject oneObject = jsonArray.getJSONObject(i);

                        String weather = oneObject.getString("main");
                        String icon = oneObject.getString("icon");

                        Log.d("Rayan", weather);
                        weatherCondition.setText("Weather Condition in "+citySwitch +": "+weather);
                        Log.d("Rayan", icon);
                        String iconLink = "https://openweathermap.org/img/w/" + icon + ".png";

                        Glide.with(Weather.this).load(iconLink).into(img);

                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("link", iconLink);
                        myEdit.putString("city", citySwitch);
                        myEdit.commit();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Rayan", "Error Retrieving URL");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);

    }
}