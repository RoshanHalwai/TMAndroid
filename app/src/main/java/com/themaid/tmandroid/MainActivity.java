package com.themaid.tmandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Getting ID's for all the views */
        final TextView textAppTitle = findViewById(R.id.textAppTitle);
        final Button buttonGetStarted = findViewById(R.id.buttonGetStarted);

        /*Setting font for all the views */
        final Typeface latoLight = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        textAppTitle.setTypeface(latoLight);
        buttonGetStarted.setTypeface(latoLight);

        /* Adding events to Get Started Button */
        buttonGetStarted.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SignIn.class)));
    }

}
