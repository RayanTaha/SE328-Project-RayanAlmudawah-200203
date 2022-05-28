package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.MyViewHolder> {

    Context context;
    ArrayList<Student> list;

    public MyAdapterList(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = list.get(position);
        holder.id.setText(student.getID());
        holder.name.setText(student.getName());
        holder.surname.setText(student.getSurname());
        holder.fatherName.setText(student.getFatherName());
        holder.nationalID.setText(student.getNationalID());
        holder.dateOfBirth.setText(student.getDateOfBirth());
        holder.gender.setText(student.getGender());
    }

    @Override
    public int getItemCount() {
        return list.size();
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
