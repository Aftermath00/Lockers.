package com.example.graphlocker;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.util.Random;

public class LockerMaker extends AppCompatActivity {
    TextView otp;
    EditText cipher;
    Button generateBtn, createBtn;
    Random rand = new Random();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_maker);
        FirebaseApp.initializeApp(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Locker Maker");
            actionBar.show();
        }

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cryptoproject-6eeed-default-rtdb.firebaseio.com");
        otp = findViewById(R.id.OTPNumber);
        cipher = findViewById(R.id.cipherText);
        generateBtn = findViewById(R.id.generateOTP_button);
        createBtn = findViewById(R.id.locker_btn);

        generateBtn.setOnClickListener(v -> {
            int random = rand.nextInt(900000) + 100000;
            otp.setText(String.valueOf(random));
        });
        createBtn.setOnClickListener(v -> {
            String otpNumber = otp.getText().toString();
            String finalCipher = cipher.getText().toString();

            Helper helper = new Helper(otpNumber,finalCipher);
            databaseReference.child(otpNumber).setValue(helper);

            Toast.makeText(LockerMaker.this, "Successfully created a locker room!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LockerMaker.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}