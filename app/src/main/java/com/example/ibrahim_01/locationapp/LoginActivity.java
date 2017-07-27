package com.example.ibrahim_01.locationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import org.w3c.dom.Text;

import java.util.Map;

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
            Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(intent);
        }

        //buttonLogin = (Button) findViewById(R.id.btnLogin);


        buttonRegister = (Button) findViewById(R.id.btnRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        email1 = (EditText) findViewById(R.id.editTextEmail);

        password1 = (EditText) findViewById(R.id.editTxtPassword);

    }

    public void onClickLogin(View view){

        String email =  email1.getText().toString().trim();
        String password = password1.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Please Enter Email",Toast.LENGTH_LONG).show();
            return;


        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_LONG).show();
            return;

        }



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing In");
        progressDialog.show();



        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    finish();
                    Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                    startActivity(intent);


                }else {
                    progressDialog.dismiss();

              //      Toast.makeText(getApplicationContext(),"yos nhe huwa",Toast.LENGTH_LONG).show();
                    try {
                        throw task.getException();
                    }  catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(getApplicationContext(), "please enter valid information", Toast.LENGTH_LONG).show();

                    }  catch (FirebaseAuthInvalidUserException e) {
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();

                    }catch (FirebaseNetworkException e) {
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                    }



                }
            }
        });






    }








}
