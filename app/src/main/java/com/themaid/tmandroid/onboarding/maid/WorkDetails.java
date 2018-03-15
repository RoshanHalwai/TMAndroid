package com.themaid.tmandroid.onboarding.maid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.R;

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

        textWorkDetailsTitle.setTypeface(Constants.setLatoLightFont(this));
        textWorkDetails.setTypeface(Constants.setLatoLightFont(this));
        textMobileNumber.setTypeface(Constants.setLatoLightFont(this));
        textFullName.setTypeface(Constants.setLatoLightFont(this));
        textAddress.setTypeface(Constants.setLatoLightFont(this));
        editMobileNumber.setTypeface(Constants.setLatoLightFont(this));
        editFullName.setTypeface(Constants.setLatoLightFont(this));
        editAddress.setTypeface(Constants.setLatoLightFont(this));
        editAddressLine2.setTypeface(Constants.setLatoLightFont(this));
        buttonActivateMyProfile.setTypeface(Constants.setLatoLightFont(this));

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

        buttonActivateMyProfile.setOnClickListener(view -> {
            Intent intent = new Intent(WorkDetails.this, ProfileCreated.class);
            intent.putExtra(Constants.USER_OBJECT_INTENT_KEY, getIntent().getSerializableExtra(Constants.USER_OBJECT_INTENT_KEY));
            startActivity(intent);
        });

        backButton.setOnClickListener(view -> onBackPressed());
    }
}
