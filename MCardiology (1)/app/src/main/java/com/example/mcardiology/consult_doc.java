package com.example.mcardiology;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class consult_doc extends AppCompatActivity {
    ArrayList<contactModel> arrContacts = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_doc);

        RecyclerView recyclerView = findViewById(R.id.consult_recycleVU);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrContacts.add(new contactModel(R.drawable.baseline_man_24, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.baseline_face_24, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.ic_launcher_foreground, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.google_logo, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.ic_launcher_foreground, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.google_logo, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.ic_launcher_foreground, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.google_logo, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.ic_launcher_foreground, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.google_logo, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.ic_launcher_foreground, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.google_logo, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.ic_launcher_foreground, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.google_logo, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.ic_launcher_foreground, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.google_logo, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.ic_launcher_foreground, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.google_logo, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.ic_launcher_foreground, "Dr Taha", "1234567890"));
        arrContacts.add(new contactModel(R.drawable.google_logo, "Dr Taha", "1234567890"));

        recyclerDocConAdapter adapter = new recyclerDocConAdapter(this, arrContacts);
        recyclerView.setAdapter(adapter);

    }
}