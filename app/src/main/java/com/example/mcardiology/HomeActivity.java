package com.example.mcardiology;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    TextView welcome;
    Button logoutBtn, consult;
    Button upload_ecg, details_btn, profile_btn;
    ImageView tip_logo;

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    public HomeActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcome = findViewById(R.id.welcome);
        welcome.setText("Welcome, ");
        logoutBtn = findViewById(R.id.logout_btn);
        tip_logo  = findViewById(R.id.tips);
        consult = findViewById(R.id.consult_but);

        upload_ecg = findViewById(R.id.upload_ecg);
        details_btn = findViewById(R.id.details_btn);
        profile_btn = findViewById(R.id.profile_btn);


        //tips
        tip_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(HomeActivity.this,TipsActivity.class));
            }
        });

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Check condition
        if (firebaseUser != null && firebaseUser.getDisplayName()!=null) {
            welcome.append(" "+firebaseUser.getDisplayName());
            welcome.append("\n" + firebaseUser.getEmail());
        }
        else{
            welcome.append(" ");
            welcome.append("\n" + firebaseUser.getEmail());
        }

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(HomeActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        logoutBtn.setOnClickListener(view -> {
            // Sign out from google
            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Check condition
                    if (task.isSuccessful()) {
                        // When task is successful sign out from firebase
                        firebaseAuth.signOut();
                        // Display Toast
                        Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                        // Finish activity
                        finish();
                    }
                }
            });
        });

        details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,InputDataActivity.class);
                UserData userData=(UserData)getIntent().getSerializableExtra("UserData");
                System.out.println(userData);
                intent.putExtra("UserData",userData);
                startActivity(intent);
            }
        });

        upload_ecg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ImguploadActivity.class));
            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,UserProfile.class));
            }
        });

        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, consult_doc.class));
            }
        });



    }
}

