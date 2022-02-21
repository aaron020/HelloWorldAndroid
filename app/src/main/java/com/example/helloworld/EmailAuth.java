package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailAuth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_auth);
    }

    public void checkIfEmailVerified(View view)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Toast.makeText(EmailAuth.this,"Error", Toast.LENGTH_SHORT).show();
            return;
        }

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.
            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        }else {
            Toast.makeText(EmailAuth.this,"Email Not Verified!", Toast.LENGTH_SHORT).show();
        }
    }
}