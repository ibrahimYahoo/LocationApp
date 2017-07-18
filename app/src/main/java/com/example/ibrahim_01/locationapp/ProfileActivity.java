package com.example.ibrahim_01.locationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity {

    private TextView welcomeEmail ;
    private Button Logout;
    private FirebaseAuth firebaseAuth;
    public static String Tag = "logs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firebaseAuth = FirebaseAuth.getInstance();
        welcomeEmail = (TextView) findViewById(R.id.txtEmail);
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        Log.d(Tag,user.getEmail());


            welcomeEmail.setText("Welcome " + user.getEmail());

        Logout = (Button) findViewById(R.id.btnLogoutProfile);

    }

    public void onClickLogoutProfile(View view){


        firebaseAuth.signOut();
        finish();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);




    }
}
