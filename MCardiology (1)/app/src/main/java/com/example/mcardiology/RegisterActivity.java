package com.example.mcardiology;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button login_btn;
    TextInputLayout inputEmail, inputPassword, inputConfirmPassword,mFullName,mPhone;
    Button signup_btn;
    Spinner mgender;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog ProgressDialog;
    String userID;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore fStore;
   UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initDatePicker();
        dateButton = findViewById(R.id.mdob);
        dateButton. setText (getTodaysDate());
        login_btn = findViewById(R.id.login_btn);
        mFullName = findViewById(R.id.mFullName) ;
        mPhone = findViewById(R.id.mPhone) ;
        mgender = findViewById(R.id.mgender) ;

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        signup_btn=findViewById(R.id.signup_btn);
        ProgressDialog  = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth. getCurrentUser ();
        fStore = FirebaseFirestore.getInstance();

        //If already have an account.. go to login page
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        //register..
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }

            private void Register() {
                String email=inputEmail.getEditText().getText() .toString();
                String password=inputPassword.getEditText().getText().toString();
                String confirmPassword = inputConfirmPassword.getEditText().getText().toString();

                String fullName = mFullName.getEditText().getText().toString() ;
                String phone = mPhone.getEditText().getText().toString();
                String gender = mgender.getSelectedItem().toString() ;
                String dob = dateButton.getText().toString();

                if (!email.matches(emailPattern)) {
                    inputEmail.setError("Enter correct Email");
                }else if(password.isEmpty() || password.length()<6)
                {
                    inputPassword. setError ("Enter valid Password");
                }else if (!password.equals(confirmPassword))
                {
                    inputConfirmPassword. setError("Password Not matched");
                }else
                {
                    ProgressDialog.setMessage("Please Wait While Registration...");
                    ProgressDialog.setTitle("Registration");
                    ProgressDialog.setCanceledOnTouchOutside(false);
                    ProgressDialog.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                ProgressDialog.dismiss ();

                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection( "users").document(userID);
                           //     Map<String,Object> user = new HashMap<>();
                                userData=new UserData(fullName,email,phone,gender,dob);
//                                user.put("fName", fullName);
//                                user.put("email", email);
//                                user.put ("phone" , phone) ;
//                                user.put ("gender" , gender) ;
//                                user.put ("dob" , dob) ;
                                System.out.println("User data is "+ userData);
                                sendUserToNextActivity();
                                documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure (@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString()) ;
                                    }
                                });
                                Toast.makeText (RegisterActivity. this,"Registration Successful", Toast. LENGTH_SHORT) . show();
                            }else{
                                ProgressDialog.dismiss ();
                                Toast.makeText (RegisterActivity. this,""+ task.getException(), Toast. LENGTH_SHORT) . show();
                            }
                        }

                        private void sendUserToNextActivity() {
                            Intent intent=new Intent(RegisterActivity.this, HomeActivity.class);
                            intent.setFlags (Intent. FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("UserData",userData);
                            System.out.println(userData);
                            startActivity(intent);
                        }
                    });
            }
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get (Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar. DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog( this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat (month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
//defualt should never happen
    return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}