package com.themaid.tmandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LanguageSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        final TextView textSelectLanguageTitle = findViewById(R.id.textSelectLanguageTitle);
        final TextView textSelectLanguage = findViewById(R.id.textSelectLanguage);
        final Button buttonHindi = findViewById(R.id.buttonHindi);
        final Button buttonEnglish = findViewById(R.id.buttonEnglish);
        final Button buttonMarathi = findViewById(R.id.buttonMarathi);
        final Button buttonTamil = findViewById(R.id.buttonTamil);
        final Button buttonKannada = findViewById(R.id.buttonKannada);
        final Button buttonBengali = findViewById(R.id.buttonBengali);
        final Button buttonGujarati = findViewById(R.id.buttonGujarati);
        final Button buttonTelugu = findViewById(R.id.buttonTelugu);
        final Button buttonMalayalam = findViewById(R.id.buttonMalayalam);
        final ImageView backButton = findViewById(R.id.backButton);

        final Typeface latoLight = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        textSelectLanguageTitle.setTypeface(latoLight);
        textSelectLanguage.setTypeface(latoLight);
        buttonHindi.setTypeface(latoLight);
        buttonEnglish.setTypeface(latoLight);
        buttonMarathi.setTypeface(latoLight);
        buttonTamil.setTypeface(latoLight);
        buttonKannada.setTypeface(latoLight);
        buttonBengali.setTypeface(latoLight);
        buttonGujarati.setTypeface(latoLight);
        buttonTelugu.setTypeface(latoLight);
        buttonMalayalam.setTypeface(latoLight);

        buttonEnglish.setOnClickListener(view -> startActivity(new Intent(LanguageSelection.this, UserType.class)));

        backButton.setOnClickListener(view -> onBackPressed());
    }
}
