package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterListSQLite extends RecyclerView.Adapter<MyAdapterListSQLite.MyViewHolder> {
    Context context;
    ArrayList id,name,surname,fatherName,nationalID,dateOfBirth,gender;

    public MyAdapterListSQLite(Context context, ArrayList ID, ArrayList FIRSTNAME, ArrayList SURNAME, ArrayList FATHERSNAME, ArrayList NATIONALID, ArrayList DATAEOFBIRTH, ArrayList GENDER) {
        this.context = context;
        this.id = ID;
        this.name = FIRSTNAME;
        this.surname = SURNAME;
        this.fatherName = FATHERSNAME;
        this.nationalID = NATIONALID;
        this.dateOfBirth = DATAEOFBIRTH;
        this.gender = GENDER;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(String.valueOf(id.get(position)));
        holder.name.setText(String.valueOf(name.get(position)));
        holder.surname.setText(String.valueOf(surname.get(position)));
        holder.fatherName.setText(String.valueOf(fatherName.get(position)));
        holder.nationalID.setText(String.valueOf(nationalID.get(position)));
        holder.dateOfBirth.setText(String.valueOf(dateOfBirth.get(position)));
        holder.gender.setText(String.valueOf(gender.get(position)));

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, surname, fatherName, nationalID, dateOfBirth, gender;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.listID);
            name = itemView.findViewById(R.id.listName);
            surname = itemView.findViewById(R.id.listSurname);
            fatherName = itemView.findViewById(R.id.listFather);
            nationalID = itemView.findViewById(R.id.listNational);
            dateOfBirth = itemView.findViewById(R.id.listDate);
            gender = itemView.findViewById(R.id.listGender);
        }
    }
}
