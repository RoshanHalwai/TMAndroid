package com.themaid.tmandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class SetupProfileAuto extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 2;

    private TextView textAadharUploaded;
    private Button buttonVerifyPhoneNumber;
    private EditText editMobileNumber;
    private EditText editFullName;

    private DatabaseReference allUsers;

    private boolean boolUserExists = false;
    private Bitmap bitmapAadhar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile_auto);

                /* Getting reference to Firebase */
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        allUsers = database.getReference("users").child("all");

        /* Getting User Type - Customer or Maid */
        String strUserType = getUserType(savedInstanceState);

        /* Getting Id's for all the views */
        textAadharUploaded = findViewById(R.id.textAadharUploaded);
        editMobileNumber = findViewById(R.id.editMobileNumber);
        editFullName = findViewById(R.id.editFullName);
        buttonVerifyPhoneNumber = findViewById(R.id.buttonVerifyPhoneNumber);
        final TextView textSetupProfileTitle = findViewById(R.id.textSetupProfileTitle);
        final TextView textMobileNumber = findViewById(R.id.textMobileNumber);
        final TextView textFullName = findViewById(R.id.textFullName);
        final TextView textLibraryPermission = findViewById(R.id.textLibraryPermission);
        final ImageView backButton = findViewById(R.id.backButton);
        final Button buttonUploadAadharCard = findViewById(R.id.buttonUploadAadhar);
        final Button buttonCamera = findViewById(R.id.buttonCamera);
        final Button buttonGallery = findViewById(R.id.buttonGallery);
        final Button buttonCancel = findViewById(R.id.buttonCancel);
        final LinearLayout layoutUploadAadharCard = findViewById(R.id.layoutUploadAadharCard);

        /* Setting font for all the views */
        final Typeface latoLight = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        final Typeface latoLightItalic = Typeface.createFromAsset(getAssets(), "fonts/Lato-LightItalic.ttf");
        textAadharUploaded.setTypeface(latoLight);
        editMobileNumber.setTypeface(latoLight);
        editFullName.setTypeface(latoLight);
        buttonVerifyPhoneNumber.setTypeface(latoLight);
        textSetupProfileTitle.setTypeface(latoLight);
        textMobileNumber.setTypeface(latoLight);
        textFullName.setTypeface(latoLight);
        textLibraryPermission.setTypeface(latoLight);
        buttonUploadAadharCard.setTypeface(latoLightItalic);
        buttonCamera.setTypeface(latoLight);
        buttonGallery.setTypeface(latoLight);
        buttonCancel.setTypeface(latoLight);

        /* Adding events to Mobile Number edit text */
        editMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 10) {
                    String keyUserType = "";
                    if (strUserType.equals("Maid")) {
                        keyUserType = "maids";
                    } else if (strUserType.equals("Customer")) {
                        keyUserType = "customers";
                    }
                    allUsers.child(keyUserType).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(charSequence.toString())) {
                                editMobileNumber.setError("Mobile Number already exists");
                                boolUserExists = true;
                            } else {
                                editMobileNumber.setError(null);
                                boolUserExists = false;
                                editFullName.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /* Adjusting the views based on the User Type
        *  If User Type is Maid we need them to upload Aadhar Card
        *  If User Type is Customer we don't need them to upload Aadhar Card */
        if (strUserType != null && strUserType.equals("Maid")) {
            buttonUploadAadharCard.setOnClickListener(view -> {
                textAadharUploaded.setVisibility(View.INVISIBLE);
                layoutUploadAadharCard.setVisibility(View.VISIBLE);
            });

            buttonCamera.setOnClickListener(view -> {
                layoutUploadAadharCard.setVisibility(View.INVISIBLE);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            });

            buttonGallery.setOnClickListener(view -> {
                layoutUploadAadharCard.setVisibility(View.INVISIBLE);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            });

            buttonCancel.setOnClickListener(view -> {
                layoutUploadAadharCard.setVisibility(View.INVISIBLE);
                bitmapAadhar = null;
                buttonVerifyPhoneNumber.setVisibility(View.INVISIBLE);
            });

        } else if (strUserType != null && strUserType.equals("Customer")) {
            buttonUploadAadharCard.setVisibility(View.INVISIBLE);
            textLibraryPermission.setVisibility(View.INVISIBLE);
            buttonVerifyPhoneNumber.setVisibility(View.VISIBLE);
        }

        /* Adding events to Verify Phone Number button */
        buttonVerifyPhoneNumber.setOnClickListener(view -> {
            if (validateUserDetails()) {
                Intent intent = new Intent(SetupProfileAuto.this, PhoneVerification.class);
                intent.putExtra("UserType", strUserType);
                intent.putExtra("LoginType", getLoginType(savedInstanceState));
                intent.putExtra("UserPhoneNumber", editMobileNumber.getText().toString());
                startActivity(intent);
            }
        });

        /* Adding events to back button */
        backButton.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmapAadhar = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        displaySuccessMessage();
                    } catch (IOException exception) {
                        exception.getStackTrace();
                    }
                } else {
                    displayErrorMessage();
                }
                break;
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK && data.getExtras() != null) {
                    bitmapAadhar = (Bitmap) data.getExtras().get("data");
                    displaySuccessMessage();
                } else {
                    displayErrorMessage();
                }
                break;
        }
    }

    /**
     * Performs validation on Mobile Number and Full Name Edit Text field
     *
     * @return true if validation is successful, false otherwise
     */
    private boolean validateUserDetails() {
        if (editMobileNumber.getText().toString().isEmpty() || editMobileNumber.getText().toString().length() != 10) {
            editMobileNumber.setError("Mobile Number is Invalid");
            return false;
        } else if (boolUserExists) {
            editMobileNumber.setError("Mobile Number already exists");
            return false;
        } else if (editFullName.getText().toString().isEmpty()) {
            editFullName.setError("Name is Invalid");
            return false;
        } else if (bitmapAadhar == null) {
            displayErrorMessage();
            return false;
        }
        return true;
    }

    /**
     * Check from previous activity the User Type
     *
     * @param savedInstanceState to get previous instance
     * @return User Type - Customer or Maid
     */
    private String getUserType(Bundle savedInstanceState) {
        String strUserType;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                strUserType = null;
            } else {
                strUserType = extras.getString("UserType");
            }
        } else {
            strUserType = (String) savedInstanceState.getSerializable("UserType");
        }
        return strUserType;
    }

    /**
     * Check from previous activity the Login Type
     *
     * @param savedInstanceState to get previous instance
     * @return User Type - Customer or Maid
     */
    private String getLoginType(Bundle savedInstanceState) {
        String strUserType;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                strUserType = null;
            } else {
                strUserType = extras.getString("LoginType");
            }
        } else {
            strUserType = (String) savedInstanceState.getSerializable("LoginType");
        }
        return strUserType;
    }

    private void displaySuccessMessage() {
        if (!boolUserExists) {
            textAadharUploaded.setText(getResources().getString(R.string.aadhar_card_uploaded_successfully));
            textAadharUploaded.setBackgroundColor(getResources().getColor(R.color.tmDarkGreen));
            buttonVerifyPhoneNumber.setVisibility(View.VISIBLE);
            textAadharUploaded.setVisibility(View.VISIBLE);
        }
    }

    private void displayErrorMessage() {
        textAadharUploaded.setText(getResources().getString(R.string.aadhar_card_uploaded_failed));
        textAadharUploaded.setBackgroundColor(getResources().getColor(R.color.tmDarkRed));
        textAadharUploaded.setVisibility(View.VISIBLE);
        buttonVerifyPhoneNumber.setVisibility(View.INVISIBLE);
    }

}
