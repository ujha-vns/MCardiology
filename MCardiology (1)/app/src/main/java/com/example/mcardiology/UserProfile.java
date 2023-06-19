package com.example.mcardiology;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
public class UserProfile extends AppCompatActivity {
    TextView fullName, email, phone, dob, gender;
    TextView weight, height, sugar, spo2, bg, bp;



    private PdfGenerator.XmlToPDFLifecycleObserver xmlToPDFLifecycleObserver;
//    int pageWidth=1200;

    // declaring width and height
    // for our PDF file.


    // creating a bitmap variable
    // for storing our images
    Bitmap bmp;

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;
    Button pdfButton;
//    Button gen_pdf;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        phone = findViewById(R.id.view_phone);
        fullName = findViewById(R.id.view_name);
        email = findViewById(R.id.view_email);
        dob = findViewById(R.id.view_dob);
        gender = findViewById(R.id.view_gender);

        //for report
        weight = findViewById(R.id.view_weight);
        height = findViewById(R.id.view_height);
        sugar = findViewById(R.id.view_sugar);
        spo2 = findViewById(R.id.view_spo2);
        bg = findViewById(R.id.view_bg);
        bp = findViewById(R.id.view_bp);
        pdfButton = findViewById(R.id.pdfButton);
//        gen_pdf = findViewById(R.id.gen_pdf);

        xmlToPDFLifecycleObserver = new PdfGenerator.XmlToPDFLifecycleObserver(this);
        getLifecycle().addObserver(xmlToPDFLifecycleObserver);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();




        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                fullName.setText(documentSnapshot.getString("name"));
                phone.setText(documentSnapshot.getString("phone"));
                email.setText(documentSnapshot.getString("email"));
                dob.setText(documentSnapshot.getString("dob"));
                gender.setText(documentSnapshot.getString("gender"));

                //for report
                weight.setText(documentSnapshot.getString("weight"));
                height.setText(documentSnapshot.getString("height"));
                sugar.setText(documentSnapshot.getString("sugar"));
                spo2.setText(documentSnapshot.getString("spo2"));
                bg.setText(documentSnapshot.getString("bg"));
                bp.setText(documentSnapshot.getString("bp"));
            }
        });



        pdfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PdfGenerator.getBuilder()
                        .setContext(UserProfile.this)
                        .fromLayoutXMLSource()
                        .fromLayoutXML(R.layout.activity_for_pdf)

                        /* "fromLayoutXML()" takes array of layout resources.
                         * You can also invoke "fromLayoutXMLList()" method here which takes list of layout resources instead of array. */
                        .setFileName("Test-PDF")
                        /* It is file name */
                        /*.setFolderName("FolderA/FolderB/FolderC")
                        /* It is folder name. If you set the folder name like this pattern (FolderA/FolderB/FolderC), then
                         * FolderA creates first.Then FolderB inside FolderB and also FolderC inside the FolderB and finally
                         * the pdf file named "Test-PDF.pdf" will be store inside the FolderB. */
                        .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.SHARE)
                        /*If you want to save your pdf in shared storage (where other apps can also see your pdf even after the app is uninstall).
                         * You need to pass an xmt to pdf lifecycle observer by the following method. To get complete overview please see the MainActivity of 'sample' folder */
                        .savePDFSharedStorage(xmlToPDFLifecycleObserver)
                        /* It true then the generated pdf will be shown after generated. */
                        .build(new PdfGeneratorListener() {
                            @Override
                            public void onFailure(FailureResponse failureResponse) {
                                super.onFailure(failureResponse);
                                /* If pdf is not generated by an error then you will findout the reason behind it
                                 * from this FailureResponse. */
                            }
                            @Override
                            public void onStartPDFGeneration() {
                                /*When PDF generation begins to start*/
                            }

                            @Override
                            public void onFinishPDFGeneration() {
                                /*When PDF generation is finished*/
                            }

                            @Override
                            public void showLog(String log) {
                                super.showLog(log);
                                /*It shows logs of events inside the pdf generation process*/
                            }

                            @Override
                            public void onSuccess(SuccessResponse response) {
                                super.onSuccess(response);
                                /* If PDF is generated successfully then you will find SuccessResponse
                                 * which holds the PdfDocument,File and path (where generated pdf is stored)*/

                            }
                        });
////
//                 canvas.drawText("Name: "+fullName. getText (), 20, 590, myPaint);
//            canvas.drawText("Phone: "+phone. getText (), 20, 640, myPaint);
//            canvas.drawText("email: "+email. getText (), 20, 690, myPaint);
//            canvas.drawText("Date Of Birth: "+dob. getText (), 20, 740, myPaint);
//            canvas.drawText("Gender: "+gender. getText (), 20, 790, myPaint);
//            canvas.drawText("Weight: "+weight. getText (), 20, 840, myPaint);
//            canvas.drawText("Height: "+height. getText (), 20, 890, myPaint);
//            canvas.drawText("SPO2: "+spo2. getText (), 20, 940, myPaint);
//            canvas.drawText("Sugar: "+sugar. getText (), 20, 990, myPaint);
//            canvas.drawText("Blood Group: "+bg. getText (), 20, 1040, myPaint);
//            canvas.drawText("Blood Pressure: "+bp. getText (), 20, 1090, myPaint);






//                TextView tvText = findViewById (R.id.view_phone);


//By the following statements, we are changing the text view inside of our target "view" which is going to be changed.
//So if we now print the "view" then you will see the changed text in the pdf.

//                phone = findViewById(R.id.view_phone);
//                fullName = findViewById(R.id.view_name);
//                email = findViewById(R.id.view_email);
//                dob = findViewById(R.id.view_dob);
//                gender = findViewById(R.id.view_gender);
//
//                //for report
//                weight = findViewById(R.id.view_weight);
//                height = findViewById(R.id.view_height);
//                sugar = findViewById(R.id.view_sugar);
//                spo2 = findViewById(R.id.view_spo2);
//                bg = findViewById(R.id.view_bg);
//                bp = findViewById(R.id.view_bp);



//                TextView namep = view.findViewById(R.id.view_name);
//                namep.setText(fullName.getText());
//                TextView phonep = view.findViewById(R.id.view_phone);
//                phonep.setText(phone.getText());
//                TextView emailp = view.findViewById(R.id.view_email);
//                emailp.setText(email.getText());
//                TextView dobp = view.findViewById(R.id.view_dob);
//                dobp.setText(dob.getText());


//                PdfGenerator.getBuilder()
//                        .setContext(UserProfile.this)
//                        .fromViewSource()
//                        .fromView(view)
//                        .setFileName("Test-PDF")
//                        .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.OPEN)
//                        .build(new PdfGeneratorListener() {
//                            @Override
//                            public void onFailure(FailureResponse failureResponse) {
//                                super.onFailure(failureResponse);
//                            }
//
//                            @Override
//                            public void showLog(String log) {
//                                super.showLog(log);
//
//                            }
//
//                            @Override
//                            public void onStartPDFGeneration() {
//
//                            }
//
//                            @Override
//                            public void onFinishPDFGeneration() {
//                                /*When PDF generation is finished*/
//                            }
//
//                            @Override
//                            public void onSuccess(SuccessResponse response) {
//                                super.onSuccess(response);
//                            }
//                        });

            }
        });

//        gen_pdf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String f_name = fullName.getText().toString();
//                String mob = phone.getText().toString();
//                String f_email = email.getText().toString();
//                String f_dob = dob.getText().toString();
//                String f_gen = gender.getText().toString();
//                String f_weight = weight.getText().toString();
//                String f_height = height.getText().toString();
//                String f_spo2 = spo2.getText().toString();
//                String f_sugar = sugar.getText().toString();
//                String f_bg = bg.getText().toString();
//                String f_bp = bp.getText().toString();
//
//                Intent intent = new Intent(UserProfile.this, for_pdf.class);
//                intent.putExtra("name", f_name);
//                intent.putExtra("mob", mob);
//                intent.putExtra("email", f_email);
//                intent.putExtra("dob", f_dob);
//                intent.putExtra("gen", f_gen);
//                intent.putExtra("weight", f_weight);
//                intent.putExtra("height", f_height);
//                intent.putExtra("spo2", f_spo2);
//                intent.putExtra("sugar", f_sugar);
//                intent.putExtra("bg", f_bg);
//                intent.putExtra("bp", f_bp);
//
//                startActivity(intent);
//            }
//        });


    }
}





