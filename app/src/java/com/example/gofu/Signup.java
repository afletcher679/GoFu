package com.example.gofu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity  {
private EditText txtname,txtemail,txtphone,txtpassword,txtratId;
Button btnSignUp;
TextView btnSignIn;
//private DatabaseReference mDatabaseReference;
//User user;
private FirebaseAuth mFirebaseAuth;
private FirebaseDatabase mDatabase;
String UID;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtname =findViewById(R.id.txtname);
        txtemail =findViewById(R.id.txtemail);
        txtphone =findViewById(R.id.txtphone);
        txtpassword =findViewById(R.id.txtpassword);
        txtratId =findViewById(R.id.txtratId);
        btnSignUp =findViewById(R.id.btnSignUp);
        btnSignIn =findViewById(R.id.btnSignIn);

        db = FirebaseFirestore.getInstance();

        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        //mDatabaseReference = mDatabase.getReference();

        //Click on Sign Up to add User to database and open up to user home
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = txtemail.getText().toString();
                final String pwd = txtpassword.getText().toString();
                if(email.isEmpty()){
                    txtemail.setError("Please enter valid email");
                    txtemail.requestFocus();
                }
                else if (pwd.isEmpty()){
                    txtpassword.setError("Please enter your password");
                    txtpassword.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(Signup.this,"Email and password are empty!",Toast.LENGTH_SHORT).show();

                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(Signup.this,"Signup unsuccessful, please try again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                UID = user.getUid();
                                String name = txtname.getText().toString();
                                String phone = txtphone.getText().toString();
                                String ratid = txtratId.getText().toString();
                                uploadData(UID,name, ratid, email, phone, pwd);
                                 goHomeActivity();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Signup.this,"Error occurred!",Toast.LENGTH_SHORT).show();
                }



                //add data to realtime database
                //user = new User(ratid,name,email,phone,pwd);
                //mDatabaseReference = mDatabase.getReference().child("User");
               // mDatabaseReference.setValue(user);

                //add data to cloud firestore


            }
        });

        //Click on Sign In to go to Login activity
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginActivity();
            }
        });
    }

    //Function to add new User to database
    private void uploadData(String uid, String name, String ratid, String email, String phone, String pwd) {
        //String id = ratid.toString();
        //Boolean status = false;
        Boolean admin = false;
        Boolean maint = false;

        Map<String,Object> user = new HashMap<>();
        user.put("id", uid);
        user.put("Name",name);
        user.put("RattlerID",ratid);
        user.put("Email", email);
        user.put("Phone", phone);
        user.put("Password",pwd);
        user.put("Ban_status", "Not Banned");
        user.put("Admin_status", admin);
        user.put("Maint_status", maint);

        db.collection("Users").document(uid).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //this is called when data is added successfully
                        Toast.makeText(Signup.this, "Upload complete", Toast.LENGTH_SHORT).show();

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                     //this is called if there is an error while uploading
                        Toast.makeText(Signup.this, "Error while uploading", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void goHomeActivity(){
        Intent i = new Intent(this, Homepage.class);
        startActivity(i);
        finish();
    }

    private void goLoginActivity(){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        finish();
    }

}
