package com.themaid.tmandroid.onboarding.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.R;

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

        textSelectLanguageTitle.setTypeface(Constants.setLatoLightFont(this));
        textSelectLanguage.setTypeface(Constants.setLatoLightFont(this));
        buttonHindi.setTypeface(Constants.setLatoLightFont(this));
        buttonEnglish.setTypeface(Constants.setLatoLightFont(this));
        buttonMarathi.setTypeface(Constants.setLatoLightFont(this));
        buttonTamil.setTypeface(Constants.setLatoLightFont(this));
        buttonKannada.setTypeface(Constants.setLatoLightFont(this));
        buttonBengali.setTypeface(Constants.setLatoLightFont(this));
        buttonGujarati.setTypeface(Constants.setLatoLightFont(this));
        buttonTelugu.setTypeface(Constants.setLatoLightFont(this));
        buttonMalayalam.setTypeface(Constants.setLatoLightFont(this));

        buttonEnglish.setOnClickListener(view -> {
            Intent intent = new Intent(LanguageSelection.this, UserType.class);
            intent.putExtra(Constants.USER_OBJECT_INTENT_KEY, getIntent().getSerializableExtra(Constants.USER_OBJECT_INTENT_KEY));
            startActivity(intent);
        });

        backButton.setOnClickListener(view -> onBackPressed());
    }
}
