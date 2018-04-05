package com.themaid.tmandroid.onboarding.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.MaidBookings;
import com.themaid.tmandroid.R;
import com.themaid.tmandroid.onboarding.pojo.UserObject;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {

    private String strUserPhoneNumber;

    private UserObject userObject;

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private EditText editFirstOTPDigit;
    private EditText editSecondOTPDigit;
    private EditText editThirdOTPDigit;
    private EditText editFourthOTPDigit;
    private EditText editFifthOTPDigit;
    private EditText editSixthOTPDigit;
    private Button buttonVerifyOTP;
    private Button buttonResendOTP;

    private DatabaseReference userPrivateInfo;
    private FirebaseUser firebaseUser;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        /* Getting reference to Firebase - Real time database*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userPrivateInfo = database.getReference(Constants.FIREBASE_CHILD_USERS).child(Constants.FIREBASE_CHILD_PRIVATE);

        fbAuth = FirebaseAuth.getInstance();

        /* Getting User Phone Number from previous activities */
        userObject = (UserObject) getIntent().getSerializableExtra(Constants.USER_OBJECT_INTENT_KEY);
        strUserPhoneNumber = userObject.getMobileNumber();

        /* Getting Id's for all the views */
        editFirstOTPDigit = findViewById(R.id.editFirstOTPDigit);
        editSecondOTPDigit = findViewById(R.id.editSecondOTPDigit);
        editThirdOTPDigit = findViewById(R.id.editThirdOTPDigit);
        editFourthOTPDigit = findViewById(R.id.editFourthOTPDigit);
        editFifthOTPDigit = findViewById(R.id.editFifthOTPDigit);
        editSixthOTPDigit = findViewById(R.id.editSixthOTPDigit);
        buttonVerifyOTP = findViewById(R.id.buttonVerifyOTP);
        buttonResendOTP = findViewById(R.id.buttonResendOTP);
        final TextView textPhoneVerificationTitle = findViewById(R.id.textPhoneVerificationTitle);
        final TextView textPhoneVerification = findViewById(R.id.textPhoneVerification);
        final TextView textIncorrectOTP = findViewById(R.id.textIncorrectOTP);
        final ImageView backButton = findViewById(R.id.backButton);

        /* Setting font for all the views */
        editFirstOTPDigit.setTypeface(Constants.setLatoLightFont(this));
        editSecondOTPDigit.setTypeface(Constants.setLatoLightFont(this));
        editThirdOTPDigit.setTypeface(Constants.setLatoLightFont(this));
        editFourthOTPDigit.setTypeface(Constants.setLatoLightFont(this));
        editFifthOTPDigit.setTypeface(Constants.setLatoLightFont(this));
        editSixthOTPDigit.setTypeface(Constants.setLatoLightFont(this));
        buttonVerifyOTP.setTypeface(Constants.setLatoLightFont(this));
        buttonResendOTP.setTypeface(Constants.setLatoLightFont(this));
        textPhoneVerificationTitle.setTypeface(Constants.setLatoLightFont(this));
        textPhoneVerification.setTypeface(Constants.setLatoLightFont(this));
        textIncorrectOTP.setTypeface(Constants.setLatoLightFont(this));

        editFirstOTPDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editSecondOTPDigit.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editSecondOTPDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editThirdOTPDigit.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editThirdOTPDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editFourthOTPDigit.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editFourthOTPDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editFifthOTPDigit.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editFifthOTPDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editSixthOTPDigit.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editSixthOTPDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(editSixthOTPDigit.getWindowToken(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonVerifyOTP.setOnClickListener(view -> {
            if (validateOTP()) {
                verifyCode();
            }
        });

        buttonResendOTP.setOnClickListener(view -> resendCode());

        /* Adding events to back button */
        backButton.setOnClickListener(view -> onBackPressed());

        sendOTP();
    }

    private void sendOTP() {
        setUpVerificationCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                strUserPhoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);
    }

    private void setUpVerificationCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(PhoneVerification.this, "Verification Success", Toast.LENGTH_LONG).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(PhoneVerification.this, "Verification Failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                phoneVerificationId = s;
                resendToken = forceResendingToken;
            }
        };
    }

    /**
     * Performs validation on OTP Edit Text field
     *
     * @return true if validation is successful, false otherwise
     */
    private boolean validateOTP() {
        if (editFirstOTPDigit.getText().toString().isEmpty() ||
                editFirstOTPDigit.getText().toString().isEmpty() ||
                editFirstOTPDigit.getText().toString().isEmpty() ||
                editFirstOTPDigit.getText().toString().isEmpty() ||
                editFirstOTPDigit.getText().toString().isEmpty() ||
                editFirstOTPDigit.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter valid OTP", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void verifyCode() {
        String code = editFirstOTPDigit.getText().toString() +
                editSecondOTPDigit.getText().toString() +
                editThirdOTPDigit.getText().toString() +
                editFourthOTPDigit.getText().toString() +
                editFifthOTPDigit.getText().toString() +
                editSixthOTPDigit.getText().toString();

        if (phoneVerificationId != null) {
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }
    }

    private void resendCode() {
        buttonResendOTP.setVisibility(View.INVISIBLE);
        buttonVerifyOTP.setVisibility(View.VISIBLE);
        setUpVerificationCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                strUserPhoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }

    private void signInWithPhoneAuthCredential(@NonNull PhoneAuthCredential phoneAuthCredential) {
        fbAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, (task) -> {
                    if (task.isSuccessful()) {
                        firebaseUser = task.getResult().getUser();

                        userPrivateInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                /* Check if User already exists */
                                if (dataSnapshot.hasChild(firebaseUser.getUid())) {
                                    String strCustomerType = Objects.requireNonNull(dataSnapshot.child(firebaseUser.getUid()).child(Constants.FIREBASE_CHILD_USER_TYPE).getValue()).toString();

                                    /* If user type is Customer then MaidServices activity is shown allowing users to select their services
                                     * Else we show MaidBookings activity where maids will be waiting for customers request */
                                    if (strCustomerType.equals(Constants.USER_TYPE_CUSTOMER)) {
                                        Intent intent = new Intent(PhoneVerification.this, MaidServices.class);
                                        intent.putExtra(Constants.FIREBASE_CHILD_USER_TYPE, Constants.USER_TYPE_CUSTOMER);
                                        startActivity(intent);
                                    } else {
                                        startActivity(new Intent(PhoneVerification.this, MaidBookings.class));
                                    }
                                }
                                /* User is Logging in for the first time */
                                else {
                                    Intent intent = new Intent(PhoneVerification.this, LanguageSelection.class);
                                    userObject.setUID(firebaseUser.getUid());
                                    intent.putExtra(Constants.USER_OBJECT_INTENT_KEY, userObject);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            buttonResendOTP.setVisibility(View.VISIBLE);
                            buttonVerifyOTP.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

}
