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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonReport;
    EditText editTextBird,editTextYourMail,editTextZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonReport = findViewById(R.id.buttonReport);
        buttonReport.setOnClickListener(this);

        editTextBird = findViewById(R.id.editTextBird );
        editTextYourMail = findViewById( R.id.editTextYourMail );
        editTextZip = findViewById(R.id.editTextZip );

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
              String email = user.getEmail();

            boolean emailVerified = user.isEmailVerified();
            editTextYourMail.setText(email);

        }

    }

    @Override
    public void onClick(View view) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("mybirds");

        if (view == buttonReport) {

            String createBird = editTextBird.getText().toString();
            String createMail = editTextYourMail.getText().toString();
            String createZip = editTextZip.getText().toString();
            Integer createImportance = 0;

            Bird createBirds = new Bird(createBird,createMail,createZip,createImportance);

            myRef.push().setValue(createBirds);
        }

        if (editTextBird.getText().toString().trim().equalsIgnoreCase("")) {
            editTextBird.setError("This field can not be blank");
        }

        if (editTextYourMail.getText().toString().trim().equalsIgnoreCase("")) {
            editTextYourMail.setError("This field can not be blank");
        }

        if (editTextZip.getText().toString().trim().equalsIgnoreCase("")) {
            editTextZip.setError("This field can not be blank");
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
            Toast.makeText(this, "You are already in Report page, you fool!", Toast.LENGTH_SHORT).show();

        }else if(item.getItemId() == R.id.itemSearch){
            Intent searchIntent = new Intent(this,SearchActivity.class);
            startActivity(searchIntent);
            //adding menu to move to landing page
        }else if(item.getItemId() == R.id.itemLanding) {
            Intent landingIntent = new Intent( this, LandingActivity.class );
            landingIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( landingIntent );
        }
        else if(item.getItemId() == R.id.itemLogout){
            Intent logoutIntent = new Intent(this,MainActivity.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            editTextYourMail.setText("");
            startActivity(logoutIntent);
            FirebaseAuth.getInstance().signInAnonymously();
            finish();

        }

            return super.onOptionsItemSelected(item);
    }

}