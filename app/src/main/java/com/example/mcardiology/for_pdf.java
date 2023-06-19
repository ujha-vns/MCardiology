package com.example.mcardiology;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class for_pdf extends AppCompatActivity {
    private TextView name, mobile, email, dob, gender, weight, height, spo2, sugar, bludgp, bludpres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_pdf);

        name = findViewById(R.id.pname);
        mobile = findViewById(R.id.pmobile);
        email = findViewById(R.id.pemail);
        dob = findViewById(R.id.pdob);
        gender = findViewById(R.id.pgender);
        weight = findViewById(R.id.pweight);
        height = findViewById(R.id.pheight);
        spo2 = findViewById(R.id.pspo2);
        sugar = findViewById(R.id.psugar);
        bludgp = findViewById(R.id.pbg);
        bludpres = findViewById(R.id.pbp);





        String naam = getIntent().getStringExtra("name");
        String mobil = getIntent().getStringExtra("mob");
        String email1 = getIntent().getStringExtra("email");
        String dob1 = getIntent().getStringExtra("dob");
        String gen1 = getIntent().getStringExtra("gen");
        String weight1 = getIntent().getStringExtra("weight");
        String height1 = getIntent().getStringExtra("height");
        String spo21 = getIntent().getStringExtra("spo2");
        String sugar1 = getIntent().getStringExtra("sugar");
        String bludgp1 = getIntent().getStringExtra("bg");
        String bludpres1 = getIntent().getStringExtra("bp");

        name.setText(naam);
        mobile.setText(mobil);
        email.setText(email1);
        dob.setText(dob1);
        gender.setText(gen1);
        weight.setText(weight1);
        height.setText(height1);
        spo2.setText(spo21);
        sugar.setText(sugar1);
        bludgp.setText(bludgp1);
        bludpres.setText(bludpres1);

    }

}