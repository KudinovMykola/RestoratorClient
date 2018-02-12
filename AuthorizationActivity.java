package com.kudinov.restoratorclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kudinov.restoratorclient.datawaiter.Hall;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;//FireBase
    private FirebaseAuth.AuthStateListener mAuthListener;//FireBase

    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();//FireBase
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        mAuth = FirebaseAuth.getInstance();//FireBase
        mAuthListener = new FirebaseAuth.AuthStateListener() {
          @Override
          public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user != null) {
                //user signed in
                Intent intent = new Intent(AuthorizationActivity.this, TestRoomActivity.class);
                startActivity(intent);
            } else {
                //user signed out
                Toast.makeText(AuthorizationActivity.this, "Fail enter", Toast.LENGTH_SHORT).show();
            }
          }
        };

        etEmail = (EditText)findViewById(R.id.etMail);
        etPassword = (EditText)findViewById(R.id.etPassword);

        findViewById(R.id.bttnSignIn).setOnClickListener(this);
        findViewById(R.id.bttnRegistration).setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();//FireBase

        if(currentUser != null) {
            Intent intent = new Intent(AuthorizationActivity.this, TestRoomActivity.class);
            startActivity(intent);
        }
    }

    //click methods
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bttnSignIn){

            signIn(etEmail.getText().toString(),
                    etPassword.getText().toString());

        } else if(v.getId() == R.id.bttnRegistration) {

            registration(etEmail.getText().toString(),
                    etPassword.getText().toString());

        }
    }

    public void signIn(String mail, String pass) {
        //FireBase
        mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AuthorizationActivity.this, "Authorization complete", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AuthorizationActivity.this, OrderActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AuthorizationActivity.this, "Fail authorization", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registration(String mail, String pass) {
        //FireBase
        mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AuthorizationActivity.this, "Registration complete", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AuthorizationActivity.this, OrderActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AuthorizationActivity.this, "Fail registration", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
