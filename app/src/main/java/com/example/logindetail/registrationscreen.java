package com.example.logindetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registrationscreen extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextInputLayout inputemail, inputpassword, inputconfirmpassword;
    Button btnRegister;
    Button btnlogin;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore fstore;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationscreen);

        inputemail = findViewById(R.id.email_field);
        inputpassword = findViewById(R.id.password_field);
        inputconfirmpassword = findViewById(R.id.confirmpassword_field);
        btnRegister = findViewById(R.id.next_signup1button);
        btnlogin = findViewById(R.id.login_Btn1);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressbar);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(registrationscreen.this,login.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perforAuth();
            }
        });


    }

    private void perforAuth() {
        String email = inputemail.getEditText().getText().toString();
        String password = inputpassword.getEditText().getText().toString();
        String confirmpassword = inputconfirmpassword.getEditText().getText().toString();

        if (!email.matches(emailpattern))
        {
            inputemail.setError("Enter correct email");
            inputemail.requestFocus();
            return;
        } else if (password.isEmpty() || password.length()<6) {
            inputpassword.setError("Enter proper password");
            return;

        } else if (!password.equals(confirmpassword)) {

            inputpassword.setError("Password not matched");
            return;
        }else {
            progressDialog.setMessage("please wait while registration");
            progressDialog.setTitle("registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){


                        Toast.makeText(registrationscreen.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(registrationscreen.this, login.class));
                        finish();
                        progressDialog.dismiss();
//                            gotoanotherscreen();
//                        Intent intent=new Intent(getApplicationContext(),)
                        //send verification code
                        FirebaseUser user = mAuth.getCurrentUser();


                        String userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fstore.collection("users").document(userID);
                        Map<String, Object> muser = new HashMap<>();
                        muser.put("email",email);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void avoid) {
                                Log.d(TAG,"onSuccess:user profile is created for"+ userID);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"onFailure: "+e.toString());

                            }
                        });




                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(registrationscreen.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }


            });


        }

    }

    public void movetologinscreen(View view) {
        Intent intent = new Intent(getApplicationContext(),login.class);
        startActivity(intent);
        finish();
    }


//    public void movetologinscreen (View view){
//            Intent intent = new Intent(getApplicationContext(), login.class);
//            startActivity(intent);
//        }



}





