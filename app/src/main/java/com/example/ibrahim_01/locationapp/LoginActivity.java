package com.example.ibrahim_01.locationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public static  String TAG = "Logs";

    private Button buttonRegister;
    private EditText email1;
    private EditText password1;
    private Button buttonLogin;


    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
        }

        //buttonLogin = (Button) findViewById(R.id.btnLogin);


        buttonRegister = (Button) findViewById(R.id.btnRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        email1 = (EditText) findViewById(R.id.editTextEmail);

        password1 = (EditText) findViewById(R.id.editTxtPassword);

    }

    public void onClickLogin(View view){

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        String email =  email1.getText().toString().trim();
        String password = password1.getText().toString().trim();


        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent);


                }else {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(),"yos nhe huwa",Toast.LENGTH_LONG).show();

                }
            }
        });






    }








}
