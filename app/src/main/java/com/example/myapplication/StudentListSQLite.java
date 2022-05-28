package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class StudentListSQLite extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList id,name,surname,fatherName,nationalID,dateOfBirth,gender;
    DatabaseHelper Mydb;
    MyAdapterListSQLite myAdapterListSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_sqlite);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Mydb = new DatabaseHelper(this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        surname = new ArrayList<>();
        fatherName = new ArrayList<>();
        nationalID = new ArrayList<>();
        dateOfBirth = new ArrayList<>();
        gender = new ArrayList<>();

        myAdapterListSQLite = new MyAdapterListSQLite(this, id,name,surname,fatherName,nationalID,dateOfBirth,gender);
        recyclerView.setAdapter(myAdapterListSQLite);

        displayData();
    }
    public void displayData(){
        Cursor cursor = Mydb.getListContents();
        if(cursor.getCount() ==0){
            Toasty.error(getBaseContext(), "there is no data",
                    Toast.LENGTH_SHORT, true).show();
        }
        else {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                surname.add(cursor.getString(2));
                fatherName.add(cursor.getString(3));
                nationalID.add(cursor.getString(4));
                dateOfBirth.add(cursor.getString(5));
                gender.add(cursor.getString(6));
            }
        }
    }
}