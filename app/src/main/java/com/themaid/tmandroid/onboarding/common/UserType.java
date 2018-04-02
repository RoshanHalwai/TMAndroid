package com.themaid.tmandroid.onboarding.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.R;
import com.themaid.tmandroid.onboarding.maid.ProfileProcess;
import com.themaid.tmandroid.onboarding.pojo.UserObject;

public class UserType extends AppCompatActivity {

    private UserObject userObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        userObject = (UserObject) getIntent().getSerializableExtra(Constants.USER_OBJECT_INTENT_KEY);

        final TextView textUserTypeTitle = findViewById(R.id.textUserTypeTitle);
        final Button buttonMaid = findViewById(R.id.buttonMaid);
        final Button buttonCustomer = findViewById(R.id.buttonCustomer);
        final ImageView backButton = findViewById(R.id.backButton);

        textUserTypeTitle.setTypeface(Constants.setLatoLightFont(this));
        buttonMaid.setTypeface(Constants.setLatoLightFont(this));
        buttonCustomer.setTypeface(Constants.setLatoLightFont(this));

        buttonCustomer.setOnClickListener(view -> {
            Intent intent = new Intent(UserType.this, SetupProfileAuto.class);
            userObject.setUserType(Constants.USER_TYPE_CUSTOMER);
            intent.putExtra(Constants.USER_OBJECT_INTENT_KEY, userObject);
            startActivity(intent);
        });

        buttonMaid.setOnClickListener(view -> {
            Intent intent = new Intent(UserType.this, ProfileProcess.class);
            userObject.setUserType(Constants.USER_TYPE_MAID);
            intent.putExtra(Constants.USER_OBJECT_INTENT_KEY, userObject);
            startActivity(intent);
        });

        backButton.setOnClickListener(view -> onBackPressed());
    }
}
