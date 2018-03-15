package com.themaid.tmandroid.onboarding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.R;
import com.themaid.tmandroid.onboarding.pojo.UserObject;

public class GettingStarted extends AppCompatActivity {

    private EditText editMobileNumber;
    private UserObject userObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        /* Creating a User Object - Serializable*/
        userObject = new UserObject();

        /*Getting ID's for all the views */
        final TextView textSignInTitle = findViewById(R.id.textSignInTitle);
        final TextView textMobileNumber = findViewById(R.id.textMobileNumber);
        editMobileNumber = findViewById(R.id.editMobileNumber);
        final Button buttonVerifyPhoneNumber = findViewById(R.id.buttonVerifyPhoneNumber);
        final ImageView backButton = findViewById(R.id.backButton);

        /*Setting font for all the views */
        textSignInTitle.setTypeface(Constants.setLatoLightFont(this));
        textMobileNumber.setTypeface(Constants.setLatoLightFont(this));
        editMobileNumber.setTypeface(Constants.setLatoLightFont(this));
        buttonVerifyPhoneNumber.setTypeface(Constants.setLatoLightFont(this));

        /* Adding events to Mobile Number edit text */
        editMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 10) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(editMobileNumber.getWindowToken(), 0);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /* Adding events to Verify Number button */
        buttonVerifyPhoneNumber.setOnClickListener(view -> {
            if (validateMobileNumber()) {
                Intent intent = new Intent(GettingStarted.this, PhoneVerification.class);
                userObject.setMobileNumber(editMobileNumber.getText().toString());
                intent.putExtra(Constants.USER_OBJECT_INTENT_KEY, userObject);
                startActivity(intent);
            }
        });

        /* Adding events to back button */
        backButton.setOnClickListener(view -> onBackPressed());
    }

    /**
     * Performs validation on Mobile Number Edit Text field
     *
     * @return true if validation is successful, false otherwise
     */
    private boolean validateMobileNumber() {
        if (editMobileNumber.getText().toString().isEmpty() || editMobileNumber.getText().toString().length() != 10) {
            Toast.makeText(this, "Please enter valid Mobile Number", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
