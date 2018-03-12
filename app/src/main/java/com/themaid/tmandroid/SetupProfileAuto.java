package com.themaid.tmandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SetupProfileAuto extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 2;

    UserObject userObject;

    Uri uriUserAadhar = null;
    String strUserAadhar = null;
    private TextView textAadharUploaded;
    private Button buttonProceed;
    private EditText editFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile_auto);

        userObject = (UserObject) getIntent().getSerializableExtra("UserObject");

        /* Getting User Type - Customer or Maid */
        String strUserType = userObject.getUserType();

        /* Getting Id's for all the views */
        textAadharUploaded = findViewById(R.id.textAadharUploaded);
        editFullName = findViewById(R.id.editFullName);
        buttonProceed = findViewById(R.id.buttonProceed);
        final TextView textSetupProfileTitle = findViewById(R.id.textSetupProfileTitle);

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
        editFullName.setTypeface(latoLight);
        buttonProceed.setTypeface(latoLight);
        textSetupProfileTitle.setTypeface(latoLight);

        textFullName.setTypeface(latoLight);
        textLibraryPermission.setTypeface(latoLight);
        buttonUploadAadharCard.setTypeface(latoLightItalic);
        buttonCamera.setTypeface(latoLight);
        buttonGallery.setTypeface(latoLight);
        buttonCancel.setTypeface(latoLight);

        /* Adjusting the views based on the User Type
        *  If User Type is Maid we need them to upload Aadhar Card
        *  If User Type is Customer we don't need them to upload Aadhar Card */
        if (strUserType != null && strUserType.equals("Maid")) {
            buttonUploadAadharCard.setOnClickListener(view -> {
                buttonProceed.setText(R.string.add_work_details);
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
                buttonProceed.setVisibility(View.INVISIBLE);
            });

        } else if (strUserType != null && strUserType.equals("Customer")) {
            buttonUploadAadharCard.setVisibility(View.INVISIBLE);
            textLibraryPermission.setVisibility(View.INVISIBLE);
            buttonProceed.setText(R.string.hire_a_maid);
            buttonProceed.setVisibility(View.VISIBLE);
        }

        /* Adding events to Proceed button */
        buttonProceed.setOnClickListener(view -> {
            if (validateUserDetails()) {
                Intent intent;
                if (strUserType != null && strUserType.equals("Maid")) {
                    intent = new Intent(SetupProfileAuto.this, WorkDetails.class);
                    userObject.setFullName(editFullName.getText().toString());
                    userObject.setUriUserAadhar(strUserAadhar);
                    intent.putExtra("UserObject", userObject);
                } else {
                    intent = new Intent(SetupProfileAuto.this, ProfileCreated.class);
                    userObject.setFullName(editFullName.getText().toString());
                    intent.putExtra("UserObject", userObject);
                }
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
                    uriUserAadhar = data.getData();
                    strUserAadhar = uriUserAadhar.toString();
                    displaySuccessMessage();
                } else {
                    displayErrorMessage();
                }
                break;
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK && data.getExtras() != null) {
                    uriUserAadhar = data.getData();
                    strUserAadhar = uriUserAadhar.toString();
                    displaySuccessMessage();
                } else {
                    displayErrorMessage();
                }
                break;
        }
    }

    /**
     * Performs validation on Full Name Edit Text field
     *
     * @return true if validation is successful, false otherwise
     */
    private boolean validateUserDetails() {
        if (editFullName.getText().toString().isEmpty()) {
            editFullName.setError("Name is Invalid");
            return false;
        }
        return true;
    }

    private void displaySuccessMessage() {
        textAadharUploaded.setText(getResources().getString(R.string.aadhar_card_uploaded_successfully));
        textAadharUploaded.setBackgroundColor(getResources().getColor(R.color.tmDarkGreen));
        buttonProceed.setVisibility(View.VISIBLE);
        textAadharUploaded.setVisibility(View.VISIBLE);
    }

    private void displayErrorMessage() {
        textAadharUploaded.setText(getResources().getString(R.string.aadhar_card_uploaded_failed));
        textAadharUploaded.setBackgroundColor(getResources().getColor(R.color.tmDarkRed));
        textAadharUploaded.setVisibility(View.VISIBLE);
        buttonProceed.setVisibility(View.INVISIBLE);
    }

}
