package com.example.myapplication;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class SQLiteDatabase extends AppCompatActivity {
    EditText id, name, surname, fatherName, nationalID, dateOfBirth, gender;
    Button insertSQLite, updateSQLite, deleteSQLite, fetchSQLite, SQLiteToFirebase, SQLiteToWeather, fetchFirebase, viewList;
    DatabaseHelper myDb;
    ImageView img2;
    TextView cityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_database);

        id = (EditText) findViewById(R.id.SQLiteID);
        name = (EditText) findViewById(R.id.SQLiteName);
        surname = (EditText) findViewById(R.id.SQLiteSurname);
        fatherName = (EditText) findViewById(R.id.SQLiteFatherName);
        nationalID = (EditText) findViewById(R.id.SQLiteNationalID);
        dateOfBirth = (EditText) findViewById(R.id.SQLiteDateOfBirth);
        gender = (EditText) findViewById(R.id.SQLiteGender);

        insertSQLite = (Button) findViewById(R.id.insertSQLite);
        updateSQLite = (Button) findViewById(R.id.updateSQLite);
        deleteSQLite = (Button) findViewById(R.id.deleteSQLite);
        fetchSQLite = (Button) findViewById(R.id.fetchSQLite);
        SQLiteToFirebase = (Button) findViewById(R.id.SQLiteToFirebase);
        SQLiteToWeather = (Button) findViewById(R.id.SQLiteToWeather);
        fetchFirebase = (Button) findViewById(R.id.retrievFirebaseInsertSQLite);
        viewList = (Button) findViewById(R.id.viewSQLiteDatabase);


        myDb = new DatabaseHelper(this);

        img2 = (ImageView) findViewById(R.id.img2);
        cityText = (TextView) findViewById(R.id.citySQLite);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        String iconLink = sh.getString("link", "");
        String city = sh.getString("city", "");

        Glide.with(SQLiteDatabase.this).load(iconLink).into(img2);
        cityText.setText("Weather Conidition in: " + city);

        insertSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertSQLite(id, name, surname, fatherName, nationalID, dateOfBirth, gender);
            }

        });

        fetchSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _ID = id.getText().toString();
                if (_ID.equals("")) {
                    Toasty.error(getBaseContext(), "Please Fill the ID Field to fetch data", Toast.LENGTH_LONG, true).show();
                    return;
                }

                Cursor cursor = myDb.structuredQuery(_ID);
                if (cursor.getString(1).equals("")) {
                    Toasty.info(getBaseContext(), "ID " + _ID + " not found", Toast.LENGTH_LONG, true).show();
                    return;
                }
                id.setText(cursor.getString(0));
                name.setText(cursor.getString(1));
                surname.setText(cursor.getString(2));
                fatherName.setText(cursor.getString(3));
                nationalID.setText(cursor.getString(4));
                dateOfBirth.setText(cursor.getString(5));
                gender.setText(cursor.getString(6));

                Toasty.success(getBaseContext(), "Fetch of" + _ID + " is successful", Toast.LENGTH_LONG, true).show();

            }
        });

        deleteSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _ID = id.getText().toString();
                if (_ID.equals("")) {
                    Toasty.error(getBaseContext(), "Please Fill the ID Field to delete data", Toast.LENGTH_LONG, true).show();
                    return;
                }
                if (!myDb.deleteData(_ID)) {
                    Toasty.error(getBaseContext(), "Deletion of" + _ID + " has failed", Toast.LENGTH_LONG, true).show();
                    return;
                }
                Toasty.success(getBaseContext(), "Deletion of" + _ID + " is successful", Toast.LENGTH_LONG, true).show();
                id.setText("");
                name.setText("");
                surname.setText("");
                fatherName.setText("");
                nationalID.setText("");
                dateOfBirth.setText("");
                gender.setText("");
            }
        });

        updateSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _ID = id.getText().toString();
                String _Name = name.getText().toString();
                String _Surname = surname.getText().toString();
                String _FatherName = fatherName.getText().toString();
                String _NationalID = nationalID.getText().toString();
                String _DateOfBirth = dateOfBirth.getText().toString();
                String _Gender = gender.getText().toString();

                if (_ID.equals("") || _Name.equals("") || _Surname.equals("") || _FatherName.equals("") || _NationalID.equals("") || _DateOfBirth.equals("") || _Gender.equals("")) {
                    Toasty.error(getBaseContext(), "Please fill all fields to update", Toast.LENGTH_LONG, true).show();
                    return;
                }
                if (_Gender.equalsIgnoreCase("male") || _Gender.equalsIgnoreCase("female")) {


                    if (myDb.updateData(_ID, _Name, _Surname, _FatherName, _NationalID, _DateOfBirth, _Gender)) {
                        Toasty.success(getBaseContext(), "Update of " + _ID + " is successful", Toast.LENGTH_LONG, true).show();


                    } else {
                        Toasty.error(getBaseContext(), "Update of " + _ID + " has failed", Toast.LENGTH_LONG, true).show();

                    }
                } else {
                    Toasty.info(getBaseContext(), "Please input Male Or Female", Toast.LENGTH_LONG, true).show();
                }

                id.setText(_ID);
                name.setText(_Name);
                surname.setText(_Surname);
                fatherName.setText(_FatherName);
                nationalID.setText(_NationalID);
                dateOfBirth.setText(_DateOfBirth);
                gender.setText(_Gender);
            }
        });

        fetchFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fetchID = id.getText().toString();
                if (fetchID.equals("")) {
                    Toasty.error(getBaseContext(), "Please fill the id field to fetch from firebase", Toast.LENGTH_LONG, true).show();
                    return;
                } else {
                    DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("Student");
                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                String namefetch = snapshot.child(fetchID).child("name").getValue().toString();
                                String surnamefetch = snapshot.child(fetchID).child("surname").getValue().toString();
                                String fathernamefetch = snapshot.child(fetchID).child("fatherName").getValue().toString();
                                String nationalidfetch = snapshot.child(fetchID).child("nationalID").getValue().toString();
                                String datefetch = snapshot.child(fetchID).child("dateOfBirth").getValue().toString();
                                String genderfetch = snapshot.child(fetchID).child("gender").getValue().toString();
                                name.setText(namefetch);
                                surname.setText(surnamefetch);
                                fatherName.setText(fathernamefetch);
                                nationalID.setText(nationalidfetch);
                                dateOfBirth.setText(datefetch);
                                gender.setText(genderfetch);
                                Toasty.success(getBaseContext(), "Fetch of " + namefetch + " from FireBase is successful", Toast.LENGTH_LONG, true).show();
                                insertSQLite(id, name, surname, fatherName, nationalID, dateOfBirth, gender);


                            } catch (Exception e) {
                                Toasty.error(getBaseContext(), "ID  does not exist", Toast.LENGTH_LONG, true).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("ray", "erorr: " + error.toException());
                        }
                    });

                }
            }
        });

        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SQLiteDatabase.this, StudentListSQLite.class));

            }
        });

        SQLiteToFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SQLiteDatabase.this, MainActivity.class));

            }
        });

        SQLiteToWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SQLiteDatabase.this, Weather.class));

            }
        });

    }

    public void insertSQLite(EditText ID, EditText Name, EditText Surename, EditText FatherName, EditText NationalID, EditText DateOfBirth, EditText Gender) {
        String _ID = id.getText().toString();
        String _Name = name.getText().toString();
        String _Surname = surname.getText().toString();
        String _FatherName = fatherName.getText().toString();
        String _NationalID = nationalID.getText().toString();
        String _DateOfBirth = dateOfBirth.getText().toString();
        String _Gender = gender.getText().toString();


        if (_ID.equals("") || _Name.equals("") || _Surname.equals("") || _FatherName.equals("") || _NationalID.equals("") || _DateOfBirth.equals("") || _Gender.equals("")) {
            Toasty.error(getBaseContext(), "Please fill all fields to add new Data", Toast.LENGTH_LONG, true).show();
            return;
        }
        if (_Gender.equalsIgnoreCase("male") || _Gender.equalsIgnoreCase("female")) {


            if (!myDb.addData(_ID, _Name, _Surname, _FatherName, _NationalID, _DateOfBirth, _Gender)) {
                Toasty.error(getBaseContext(), "Insertion of " + _ID + " Has Failed, duplicate ID", Toast.LENGTH_LONG, true).show();
            } else {
                Toasty.success(getBaseContext(), "Insertion of " + _ID + " is successful", Toast.LENGTH_LONG, true).show();

            }
        } else {
            Toasty.info(getBaseContext(), "Please input Male Or Female", Toast.LENGTH_LONG, true).show();
            return;
        }
    }
}