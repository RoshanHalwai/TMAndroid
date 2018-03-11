package com.themaid.tmandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_details);

        final TextView textWorkDetailsTitle = findViewById(R.id.textWorkDetailsTitle);
        final TextView textWorkDetails = findViewById(R.id.textAddWorkDetails);
        final TextView textMobileNumber = findViewById(R.id.textMobileNumber);
        final TextView textFullName = findViewById(R.id.textFullName);
        final TextView textAddress = findViewById(R.id.textAddress);
        final EditText editMobileNumber = findViewById(R.id.editMobileNumber);
        final EditText editFullName = findViewById(R.id.editFullName);
        final EditText editAddress = findViewById(R.id.editAddress);
        final EditText editAddressLine2 = findViewById(R.id.editAddressLine2);
        final Button buttonActivateMyProfile = findViewById(R.id.buttonActivateMyProfile);
        final ImageView backButton = findViewById(R.id.backButton);

        final Typeface latoLight = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        textWorkDetailsTitle.setTypeface(latoLight);
        textWorkDetails.setTypeface(latoLight);
        textMobileNumber.setTypeface(latoLight);
        textFullName.setTypeface(latoLight);
        textAddress.setTypeface(latoLight);
        editMobileNumber.setTypeface(latoLight);
        editFullName.setTypeface(latoLight);
        editAddress.setTypeface(latoLight);
        editAddressLine2.setTypeface(latoLight);
        buttonActivateMyProfile.setTypeface(latoLight);

        editMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 10) {
                    editFullName.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonActivateMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WorkDetails.this, ProfileCreated.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
