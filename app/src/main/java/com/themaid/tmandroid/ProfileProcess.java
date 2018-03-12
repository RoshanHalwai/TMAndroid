package com.themaid.tmandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileProcess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_process);

        final TextView textProfileProcessTitle = findViewById(R.id.textProfileProcessTitle);
        final TextView textSetupProfile = findViewById(R.id.textSetupProfile);
        final Button buttonManual = findViewById(R.id.buttonManual);
        final Button buttonAutomatic = findViewById(R.id.buttonAutomatic);
        final ImageView backButton = findViewById(R.id.backButton);

        final Typeface latoLight = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        textProfileProcessTitle.setTypeface(latoLight);
        textSetupProfile.setTypeface(latoLight);
        buttonManual.setTypeface(latoLight);
        buttonAutomatic.setTypeface(latoLight);

        buttonManual.setOnClickListener(view -> startActivity(new Intent(ProfileProcess.this, SetupProfileManual.class)));

        buttonAutomatic.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileProcess.this, SetupProfileAuto.class);
            intent.putExtra("UserObject", getIntent().getSerializableExtra("UserObject"));
            startActivity(intent);
        });

        backButton.setOnClickListener(view -> onBackPressed());
    }
}
