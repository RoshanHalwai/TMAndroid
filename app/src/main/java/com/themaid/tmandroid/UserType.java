package com.themaid.tmandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserType extends AppCompatActivity {

    private UserObject userObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        userObject = (UserObject) getIntent().getSerializableExtra("UserObject");

        final TextView textUserTypeTitle = findViewById(R.id.textUserTypeTitle);
        final Button buttonMaid = findViewById(R.id.buttonMaid);
        final Button buttonCustomer = findViewById(R.id.buttonCustomer);
        final ImageView backButton = findViewById(R.id.backButton);

        final Typeface latoLight = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        textUserTypeTitle.setTypeface(latoLight);
        buttonMaid.setTypeface(latoLight);
        buttonCustomer.setTypeface(latoLight);

        buttonCustomer.setOnClickListener(view -> {
            Intent intent = new Intent(UserType.this, SetupProfileAuto.class);
            userObject.setUserType("Customer");
            intent.putExtra("UserObject", userObject);
            startActivity(intent);
        });

        buttonMaid.setOnClickListener(view -> {
            Intent intent = new Intent(UserType.this, ProfileProcess.class);
            userObject.setUserType("Maid");
            intent.putExtra("UserObject", userObject);
            startActivity(intent);
        });

        backButton.setOnClickListener(view -> onBackPressed());
    }
}
