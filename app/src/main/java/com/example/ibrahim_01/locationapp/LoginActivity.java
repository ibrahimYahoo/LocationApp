package com.example.ibrahim_01.locationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {


    private Button buttonRegister;
    private EditText email;
    private EditText password;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = (Button) findViewById(R.id.btnLogin);

        buttonRegister = (Button) findViewById(R.id.btnRegister);

        email = (EditText) findViewById(R.id.editTextEmail);

        password = (EditText) findViewById(R.id.editTxtPassword);






    }
}
