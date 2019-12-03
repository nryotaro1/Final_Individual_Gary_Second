package com.example.finalindividualgary_second;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonLogin, buttonRegister;
    EditText editTextEmail, editTextPassword;

    //#1
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        buttonLogin = findViewById( R.id.buttonLogin );
        buttonRegister = findViewById( R.id.buttonRegister );
        editTextEmail = findViewById( R.id.editTextEmail );
        editTextPassword = findViewById( R.id.editTextPassword );

        buttonLogin.setOnClickListener( this );
        buttonRegister.setOnClickListener( this );

        //#2
        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View view) {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();


        if (view == buttonRegister) {
            // What to do to register folks
            mAuth.createUserWithEmailAndPassword( email, password )
                    .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText( MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT ).show();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText( MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } );

        } else if (view == buttonLogin) {
            // What to do to log in registered users
            mAuth.signInWithEmailAndPassword( email, password )
                    .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText( MainActivity.this, "Login Successful", Toast.LENGTH_SHORT ).show();

                                /////
                                Intent loginintent = new Intent(MainActivity.this,RegisterActivity.class);
                                startActivity( loginintent );

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText( MainActivity.this, "Login Failed", Toast.LENGTH_SHORT ).show();
                            }
                            // ...
                        }
                    } );

            if (editTextEmail.getText().toString().trim().equalsIgnoreCase("")) {
                editTextEmail.setError("This field can not be blank");
            }

            if (editTextPassword.getText().toString().trim().equalsIgnoreCase("")) {
                editTextPassword.setError("This field can not be blank");
            }

        }



    }

}
