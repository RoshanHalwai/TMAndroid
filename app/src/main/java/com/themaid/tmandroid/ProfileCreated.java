package com.themaid.tmandroid;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileCreated extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_created);

        final TextView textProfileCreatedTitle = findViewById(R.id.textProfileCreatedTitle);
        final TextView textProfileCreated = findViewById(R.id.textProfileCreated);
        final ImageView backButton = findViewById(R.id.backButton);

        final Typeface latoLight = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        textProfileCreatedTitle.setTypeface(latoLight);
        textProfileCreated.setTypeface(latoLight);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
