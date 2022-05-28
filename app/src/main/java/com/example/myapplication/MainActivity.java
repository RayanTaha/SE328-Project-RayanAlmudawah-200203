package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    EditText id, name, surname, fatherName, nationalId, dateOfBirth, gender;
    Button insertFireBase, updateFireBase, deleteFireBase, fetchFireBase, studentListActivity, weatherActiviy, SQLiteActivity;
    ImageView img1;
    TextView weatherCond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://rayanalmudawah-200203-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Student");

        insertFireBase = (Button) findViewById(R.id.insertFireBase);
        updateFireBase = (Button) findViewById(R.id.updateFireBase);
        deleteFireBase = (Button) findViewById(R.id.deleteFireBase);
        fetchFireBase = (Button) findViewById(R.id.fetchFireBase);
        studentListActivity = (Button) findViewById(R.id.ListStudent);
        weatherActiviy = (Button) findViewById(R.id.goToWeatherActivity);
        SQLiteActivity = (Button) findViewById(R.id.goToSQLite);

        id = (EditText) findViewById(R.id.fieldID);
        name = (EditText) findViewById(R.id.fieldName);
        surname = (EditText) findViewById(R.id.fieldSurname);
        fatherName = (EditText) findViewById(R.id.fieldFatherName);
        nationalId = (EditText) findViewById(R.id.fieldNationalD);
        dateOfBirth = (EditText) findViewById(R.id.fieldDateOfBirth);
        gender = (EditText) findViewById(R.id.fieldGender);


        img1 = (ImageView) findViewById(R.id.weatherImage);
        weatherCond = (TextView)findViewById(R.id.cityWeather);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        String iconLink = sh.getString("link", "");
        String city = sh.getString("city", "");

        Glide.with(MainActivity.this).load(iconLink).into(img1);
        weatherCond.setText("Weather Conidition in: "+ city);




        insertFireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String insertID = id.getText().toString();
                String insertName = name.getText().toString();
                String insertSurname = surname.getText().toString();
                String insertFatherName = fatherName.getText().toString();
                String insertNationalID = nationalId.getText().toString();
                String insertDateOfBirth = dateOfBirth.getText().toString();
                String insertGender = gender.getText().toString();
                try {
                    if (insertID.equals("") || insertName.equals("") || insertSurname.equals("") || insertFatherName.equals("") || insertNationalID.equals("") || insertDateOfBirth.equals("") || insertGender.equals("")) {
                        Toasty.error(getBaseContext(), "Please fill all fields to insert data", Toast.LENGTH_LONG, true).show();
                        return;
                    } else if (insertGender.equalsIgnoreCase("male") || insertGender.equalsIgnoreCase("female")) {
                        Student st = new Student(insertID, insertName, insertSurname, insertFatherName, insertNationalID, insertDateOfBirth, insertGender);
                        myRef.child(insertID).setValue(st);
                        Toasty.success(getBaseContext(), "Insertion of "+insertID+" is Successful", Toast.LENGTH_LONG, true).show();

                    }else{
                        Toasty.info(getBaseContext(), "Please insert male or female", Toast.LENGTH_LONG, true).show();

                    }
                }catch (Exception e){
                    Toasty.error(getBaseContext(), "Insertion of "+ insertID+" has failed", Toast.LENGTH_LONG, true).show();

                }
            }
        });

        fetchFireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _ID = id.getText().toString();
                if (_ID.equals("")) {
                    Toasty.error(getBaseContext(), "Please fill the id field to fetch the data", Toast.LENGTH_LONG, true).show();
                    return;
                } else {
                    DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("Student");
                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                String idfetch = snapshot.child(_ID).child("id").getValue().toString();
                                String namefetch = snapshot.child(_ID).child("name").getValue().toString();
                                String surnamefetch = snapshot.child(_ID).child("surname").getValue().toString();
                                String fathernamefetch = snapshot.child(_ID).child("fatherName").getValue().toString();
                                String nationalidfetch = snapshot.child(_ID).child("nationalID").getValue().toString();
                                String datefetch = snapshot.child(_ID).child("dateOfBirth").getValue().toString();
                                String genderfetch = snapshot.child(_ID).child("gender").getValue().toString();
                                id.setText(idfetch);
                                name.setText(namefetch);
                                surname.setText(surnamefetch);
                                fatherName.setText(fathernamefetch);
                                nationalId.setText(nationalidfetch);
                                dateOfBirth.setText(datefetch);
                                gender.setText(genderfetch);

                                Toasty.success(getBaseContext(), "Fetch of "+_ID+" is successful", Toast.LENGTH_LONG, true).show();
                            } catch (Exception e) {
                                Toasty.error(getBaseContext(), "ID " +_ID+" does not exist", Toast.LENGTH_LONG, true).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("ray", "error" + error.toException());
                        }
                    });

                }
            }
        });
        updateFireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _ID = id.getText().toString();
                if (_ID.equals("")) {
                    Toasty.error(getBaseContext(), "Please fill the id field", Toast.LENGTH_LONG, true).show();
                    return;
                }else if (gender.getText().toString().equalsIgnoreCase("male") || gender.getText().toString().equalsIgnoreCase("female")){
                    try {
                        myRef.child(_ID).child("name").setValue(name.getText().toString());
                        myRef.child(_ID).child("surname").setValue(surname.getText().toString());
                        myRef.child(_ID).child("fatherName").setValue(fatherName.getText().toString());
                        myRef.child(_ID).child("nationalID").setValue(nationalId.getText().toString());
                        myRef.child(_ID).child("dateOfBirth").setValue(dateOfBirth.getText().toString());
                        myRef.child(_ID).child("gender").setValue(gender.getText().toString());
                        Toasty.success(getBaseContext(), "Update of "+_ID+" successful", Toast.LENGTH_LONG, true).show();

                    } catch (Exception e) {
                        Toasty.error(getBaseContext(), "ID "+_ID+" does not exist", Toast.LENGTH_LONG, true).show();
                    }
                }else{
                    Toasty.info(getBaseContext(), "Please insert male or female", Toast.LENGTH_LONG, true).show();
                }
            }
        });

        deleteFireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _ID = id.getText().toString();
                if(_ID.equals("")){
                    Toasty.error(getBaseContext(), "Please insert ID",Toast.LENGTH_SHORT, true).show();
                }else {
                    myRef.child("Student").removeValue();
                    Toasty.success(getBaseContext(), "Deletion of " +_ID+ " Successful.", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        studentListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StudentsList.class));
            }
        });
        weatherActiviy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Weather.class));

            }
        });
        SQLiteActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SQLiteDatabase.class));

            }
        });

    }

}