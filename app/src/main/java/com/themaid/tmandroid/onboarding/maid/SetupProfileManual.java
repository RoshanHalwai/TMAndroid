package com.themaid.tmandroid.onboarding.maid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.R;

public class SetupProfileManual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile_manual);

        final TextView textSetupProfileTitle = findViewById(R.id.textSetupProfileTitle);
        final TextView textContactBackOffice = findViewById(R.id.textContactBackOffice);
        final TextView textAadharCard = findViewById(R.id.textAadharCard);
        final TextView textWorkPlace = findViewById(R.id.textWorkPlace);
        final TextView textPhoneNumber = findViewById(R.id.textPhoneNumber);
        final ImageView backButton = findViewById(R.id.backButton);

        textSetupProfileTitle.setTypeface(Constants.setLatoLightFont(this));
        textContactBackOffice.setTypeface(Constants.setLatoLightItalicFont(this));
        textAadharCard.setTypeface(Constants.setLatoLightItalicFont(this));
        textWorkPlace.setTypeface(Constants.setLatoLightItalicFont(this));
        textPhoneNumber.setTypeface(Constants.setLatoLightItalicFont(this));

        backButton.setOnClickListener(view -> onBackPressed());
    }
}
