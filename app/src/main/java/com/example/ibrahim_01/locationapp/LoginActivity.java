package com.example.ibrahim_01.locationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static  String TAG = "Logs";

    private Button buttonRegister;
    private EditText email;
    private EditText password;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = (Button) findViewById(R.id.btnLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"yos kesa hae",Toast.LENGTH_LONG);
                Log.d(TAG,"yos console pae agya");
            }
        });

        buttonRegister = (Button) findViewById(R.id.btnRegister);

        email = (EditText) findViewById(R.id.editTextEmail);

        password = (EditText) findViewById(R.id.editTxtPassword);

    }





}
