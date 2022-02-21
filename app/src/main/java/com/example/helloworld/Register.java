package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;

public class Register extends AppCompatActivity {
    EditText usernameSignUp;
    EditText emailSignUp;
    EditText passwordSignUp;
    FirebaseAuth fAuth;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);

        fAuth = FirebaseAuth.getInstance();
        usernameSignUp = findViewById(R.id.usernameSignUp);
        emailSignUp = findViewById(R.id.email);
        passwordSignUp = findViewById(R.id.passwordSignUp);
        progress = findViewById(R.id.progressBarReg);
        System.out.println("Opened register page");

        //If user is already logged in

//        if(fAuth.getCurrentUser() != null){
//            Toast.makeText(Register.this,"Already Signed In", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Register.this, MainActivity.class);
//            startActivity(intent);
//        }



        MaterialButton signUp = (MaterialButton) findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameSignUp.getText().toString().trim();
                String password = passwordSignUp.getText().toString().trim();
                String email = emailSignUp.getText().toString().trim();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email)
                    || TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Missing Field!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progress.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"Account Created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Register.this,"Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.INVISIBLE);
                        }
                    }
                });




            }
        });


    }


}