package com.example.mcardiology;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InputDataActivity extends AppCompatActivity {

    private Button submit;

    TextInputLayout  mWeight, mHeight, mspo2, msugar, mbloodpressure;
    Spinner mbloodgroup;

    String userID;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore fStore;

    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        submit = findViewById(R.id.button);

        mWeight = findViewById(R.id.mWeight);
        mHeight = findViewById(R.id.mHeight);
        mspo2 = findViewById(R.id.mspo2);
        msugar = findViewById(R.id.msugar);
        mbloodgroup = findViewById(R.id.mbloodgroup);
        mbloodpressure = findViewById(R.id.mbloodpressure);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth. getCurrentUser ();
        fStore = FirebaseFirestore.getInstance();

//        Spinner spinner = (Spinner)findViewById(R.id.spinner);

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
//                    Toast.makeText(InputDataActivity.this, ""+parent.getSelectedItem().toString() ,Toast.LENGTH_SHORT).show();
//        }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent){
//            }
//        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Weight = mWeight.getEditText().getText().toString();
                String Height = mHeight.getEditText().getText().toString();
                String spo2 = mspo2.getEditText().getText().toString();
                String sugar = msugar.getEditText().getText().toString();
                String bp = mbloodpressure.getEditText().getText().toString();
                String bg = mbloodgroup.getSelectedItem().toString() ;

                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection( "users").document(userID);

                userData=(UserData)getIntent().getSerializableExtra("UserData");
                System.out.println(userData);

                userData.setWeight(Weight);
                userData.setHeight(Height);
                userData.setSpo2(spo2);
                userData.setBp(bp);
                userData.setBg(bg);
                userData.setSugar(sugar);
//                user.put("Weight", Weight);
//                user.put("Height", Height);
//                user.put("spo2", spo2);
//                user.put("sugar", sugar);
//                user.put("bloodpressure", bp);
//                user.put("bloodgroup", bg);

                documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                        startActivity(new Intent(InputDataActivity.this,MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure (@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString()) ;
                        startActivity(new Intent(InputDataActivity.this,MainActivity.class));
                    }
                });


//                databaseReference.child("Users")
//                        .child(getName)
//                        .setValue(hashMap)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(InputDataActivity.this,
//                                        "Data added successfully", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(InputDataActivity.this,MainActivity.class));
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(InputDataActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(InputDataActivity.this,MainActivity.class));
//                            }
//
//                        });
            }
        });
    }
}