package com.themaid.tmandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.themaid.tmandroid.onboarding.GettingStarted;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(MainActivity.this, MaidBookings.class));
        } else {
            setContentView(R.layout.activity_main);

            /*Getting ID's for all the views */
            final TextView textAppTitle = findViewById(R.id.textAppTitle);
            final Button buttonGetStarted = findViewById(R.id.buttonGetStarted);

            /*Setting font for all the views */
            textAppTitle.setTypeface(Constants.setLatoLightFont(this));
            buttonGetStarted.setTypeface(Constants.setLatoLightFont(this));

            /* Adding events to Get Started Button */
            buttonGetStarted.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GettingStarted.class)));
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
