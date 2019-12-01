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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonSearch,buttonAdd;
    EditText editTextZipSearch,editTextAdd;
    TextView textViewBird,textViewYourMail,textViewImportance;

    EditText editTextYourMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);

        editTextZipSearch = findViewById(R.id.editTextZipSearch);
        editTextAdd = findViewById( R.id.editTextAdd );

        editTextYourMail = findViewById( R.id.editTextYourMail );

        textViewBird = findViewById(R.id.textViewBird);
        textViewYourMail = findViewById(R.id.textViewYourMail );
        textViewImportance = findViewById( R.id.textViewImportance );
    }


    @Override
    public void onClick(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("mybirds");

        if (view == buttonSearch) {

            String findZip = editTextZipSearch.getText().toString();

            myRef.orderByChild("zip").equalTo(findZip).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    // String findKey = dataSnapshot.getKey();
                    Bird foundBird = dataSnapshot.getValue(Bird.class);
                    String findBird = foundBird.bird;
                    String findYourMail = foundBird.mail;
                    String findImportance = foundBird.importance;

                    textViewBird.setText(findBird);
                    textViewYourMail.setText(findYourMail);
                    textViewImportance.setText(findImportance);
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

        if (buttonAdd == view){


        }

        if (editTextZipSearch.getText().toString().trim().equalsIgnoreCase("")) {
            editTextZipSearch.setError("This field can not be blank");
        }




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

        } else if(item.getItemId() == R.id.itemSearch) {
            Toast.makeText(this, "You are already in Search page, you fool!", Toast.LENGTH_SHORT).show();

        } else if(item.getItemId() == R.id.itemLogout){
            Intent logoutIntent = new Intent(this,MainActivity.class);
            FirebaseAuth.getInstance().signInAnonymously();
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logoutIntent);
            editTextYourMail.setText("");
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


}
