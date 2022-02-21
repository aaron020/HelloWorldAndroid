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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText emailSignin = findViewById(R.id.emailSignin);
        EditText passwordSignin = findViewById(R.id.password);
        MaterialButton loginBtn = (MaterialButton) findViewById(R.id.loginbtn);
        MaterialButton regBtn = (MaterialButton) findViewById(R.id.register);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        ProgressBar progressBar = findViewById(R.id.progressBarLog);





        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailSignin.getText().toString().trim();
                String password =  passwordSignin.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(MainActivity.this,"Missing Email Field!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this,"Missing Password Field!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Auth the user using firebase

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Signed In", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(!user.isEmailVerified()){
                                Intent intent = new Intent(MainActivity.this, EmailAuth.class);
                                startActivity(intent);
                                sendVerificationEmail();
                                FirebaseAuth.getInstance().signOut();
                            }else{
                                Intent intent = new Intent(MainActivity.this, Home.class);
                                startActivity(intent);
                            }

                        }else{
                            Toast.makeText(MainActivity.this,"Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });



            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

    }

    public void forgotPassword(View view){
        startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
        finish();
    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent just logout the user and finish this activity
                            Toast.makeText(MainActivity.this,"Email Sent", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Error " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }




}