package com.themaid.tmandroid.onboarding.maid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.R;
import com.themaid.tmandroid.onboarding.common.SetupProfileAuto;

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

        textProfileProcessTitle.setTypeface(Constants.setLatoLightFont(this));
        textSetupProfile.setTypeface(Constants.setLatoLightFont(this));
        buttonManual.setTypeface(Constants.setLatoLightFont(this));
        buttonAutomatic.setTypeface(Constants.setLatoLightFont(this));

        buttonManual.setOnClickListener(view -> startActivity(new Intent(ProfileProcess.this, SetupProfileManual.class)));

        buttonAutomatic.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileProcess.this, SetupProfileAuto.class);
            intent.putExtra(Constants.USER_OBJECT_INTENT_KEY, getIntent().getSerializableExtra(Constants.USER_OBJECT_INTENT_KEY));
            startActivity(intent);
        });

        backButton.setOnClickListener(view -> onBackPressed());
    }
}
