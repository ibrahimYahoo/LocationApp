package com.example.ibrahim_01.locationapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {


    private Button buttonRegister;
    private EditText email1;
    private EditText password1;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        email1 = (EditText) findViewById(R.id.txtEmailReg);

        password1 = (EditText) findViewById(R.id.txtPassInReg);

    }

    public void onClickBtnRegInReg(View view){

        firebaseAuth = FirebaseAuth.getInstance();

        String email =  email1.getText().toString().trim();
        String password = password1.getText().toString().trim();


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"yos firebase hae",Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(getApplicationContext(),"yos nhe huwa",Toast.LENGTH_LONG).show();


                }


            }
        });




    }
}
