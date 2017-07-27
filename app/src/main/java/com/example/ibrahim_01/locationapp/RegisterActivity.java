package com.example.ibrahim_01.locationapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    private Button buttonRegister;
    private EditText email1;
    private EditText password1;
    private EditText ConfirmpPssword1;
    private EditText phoneNo;


    private EditText username;

    private DatabaseReference databaseReference;



    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    public String TAG = "tag";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        email1 = (EditText) findViewById(R.id.txtEmainInReg);

        password1 = (EditText) findViewById(R.id.txtPassInReg);

        ConfirmpPssword1 = (EditText) findViewById(R.id.txtConfirmPass);

        username = (EditText) findViewById(R.id.txtNameReg);

        phoneNo = (EditText) findViewById(R.id.txtMobileInReg);



    }





    public void onClickBtnRegInReg(View view){

        try {

            String email = email1.getText().toString().trim();
            String password = password1.getText().toString().trim();
            String confirmPassword = ConfirmpPssword1.getText().toString().trim();
            String username1 = username.getText().toString().trim();
            String strPhoneNo = phoneNo.getText().toString().trim();


            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_LONG).show();
                return;


            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_LONG).show();
                return;

            }
            if (TextUtils.isEmpty(username1)) {
                Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_LONG).show();
                return;

            }



            if (TextUtils.isEmpty(strPhoneNo)) {
                Toast.makeText(getApplicationContext(), "Please Enter Phone number", Toast.LENGTH_LONG).show();
                return;

            }


            boolean resultOfComparison = password.equals(confirmPassword);

            if (!resultOfComparison) {
                Toast.makeText(getApplicationContext(), "Your Password and Confirm Password does not match", Toast.LENGTH_LONG).show();
                return;
            }


            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Registering new user");
            progressDialog.show();

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {



                        String username1 = username.getText().toString().trim();

                        double strPhoneNo =   Double.parseDouble( phoneNo.getText().toString().trim());

                        databaseReference = FirebaseDatabase.getInstance().getReference();

                        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

                        userClass userClassObj = new  userClass(username1,0,0,strPhoneNo,true);

                        databaseReference.child(user.getUid()).setValue(userClassObj);








//                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                .setDisplayName(username1)
//                              //  .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
//                                .build();
//
//
//
//                        user.updateProfile(profileUpdates)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Log.d(TAG, "User profile updated.");
//                                        }
//                                    }
//                                });
                        progressDialog.dismiss();

                        //Toast.makeText(getApplicationContext(), "yos firebase hae", Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);


                    } else {
                        progressDialog.dismiss();
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {

                       Toast.makeText(getApplicationContext(), "please select a strong password", Toast.LENGTH_LONG).show();



                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(getApplicationContext(), "please enter valid information", Toast.LENGTH_LONG).show();


                        } catch (FirebaseAuthUserCollisionException e) {

                            Toast.makeText(getApplicationContext(), "User Already Exists", Toast.LENGTH_LONG).show();


                        }
                        catch (FirebaseNetworkException e) {

                            Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();


                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_LONG).show();

                        }

                    }


                }
            });

        }catch (Exception e){
            progressDialog.dismiss();
Log.d(TAG,e.toString());

        }




    }
}
