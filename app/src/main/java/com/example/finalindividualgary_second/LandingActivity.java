package com.example.finalindividualgary_second;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LandingActivity extends AppCompatActivity {


    TextView textView4,textView5,textView6,textView7,textView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_landing );

        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("mybirds");

        myRef.orderByChild("importance").limitToLast(1).addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String findKey = dataSnapshot.getKey();
                Bird foundBird = dataSnapshot.getValue(Bird.class);
                String importantBird = foundBird.bird;
                String importantYourMail = foundBird.mail;
                String importantZip = foundBird.zip;
                Integer importantImportance = foundBird.importance;

                textView5.setText(importantBird);
                textView6.setText(importantYourMail);
                textView7.setText( importantZip );
                textView8.setText(String.valueOf(importantImportance));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.itemReport){
            Intent registerIntent = new Intent(this,RegisterActivity.class);
            startActivity(registerIntent);

        }else if(item.getItemId() == R.id.itemSearch){
            Intent searchIntent = new Intent(this,SearchActivity.class);
            startActivity(searchIntent);
            //adding menu to move to landing page
        }else if(item.getItemId() == R.id.itemLanding) {
            Toast.makeText(this, "You are already in Landing page, you fool!", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.itemLogout){
            Intent logoutIntent = new Intent(this,MainActivity.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logoutIntent);
            FirebaseAuth.getInstance().signInAnonymously();
            finish();

        }

        return super.onOptionsItemSelected(item);
    }



}
