package com.themaid.tmandroid.onboarding;

import android.content.Intent;
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

import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.R;
import com.themaid.tmandroid.onboarding.maid.MaidServices;
import com.themaid.tmandroid.onboarding.maid.ProfileCreated;
import com.themaid.tmandroid.onboarding.pojo.UserObject;

public class SetupProfileAuto extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 2;

    private UserObject userObject;

    private String strUserAadhar = null;
    private TextView textAadharUploaded;
    private Button buttonProceed;
    private EditText editFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile_auto);

        userObject = (UserObject) getIntent().getSerializableExtra(Constants.USER_OBJECT_INTENT_KEY);

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
        textAadharUploaded.setTypeface(Constants.setLatoLightFont(this));
        editFullName.setTypeface(Constants.setLatoLightFont(this));
        buttonProceed.setTypeface(Constants.setLatoLightFont(this));
        textSetupProfileTitle.setTypeface(Constants.setLatoLightFont(this));

        textFullName.setTypeface(Constants.setLatoLightFont(this));
        textLibraryPermission.setTypeface(Constants.setLatoLightFont(this));
        buttonUploadAadharCard.setTypeface(Constants.setLatoLightItalicFont(this));
        buttonCamera.setTypeface(Constants.setLatoLightFont(this));
        buttonGallery.setTypeface(Constants.setLatoLightFont(this));
        buttonCancel.setTypeface(Constants.setLatoLightFont(this));

        /* Adjusting the views based on the User Type
        *  If User Type is Maid we need them to upload Aadhar Card
        *  If User Type is Customer we don't need them to upload Aadhar Card */
        if (strUserType != null && strUserType.equals(Constants.USER_TYPE_MAID)) {
            buttonUploadAadharCard.setOnClickListener(view -> {
                buttonProceed.setText(R.string.add_service_details);
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

        } else if (strUserType != null && strUserType.equals(Constants.USER_TYPE_CUSTOMER)) {
            buttonUploadAadharCard.setVisibility(View.INVISIBLE);
            textLibraryPermission.setVisibility(View.INVISIBLE);
            buttonProceed.setText(R.string.hire_a_maid);
            buttonProceed.setVisibility(View.VISIBLE);
        }

        /* Adding events to Proceed button */
        buttonProceed.setOnClickListener(view -> {
            if (validateUserDetails()) {
                Intent intent;
                if (strUserType != null && strUserType.equals(Constants.USER_TYPE_MAID)) {
                    intent = new Intent(SetupProfileAuto.this, MaidServices.class);
                    userObject.setFullName(editFullName.getText().toString());
                    userObject.setUriUserAadhar(strUserAadhar);
                    intent.putExtra(Constants.USER_OBJECT_INTENT_KEY, userObject);
                } else {
                    intent = new Intent(SetupProfileAuto.this, ProfileCreated.class);
                    userObject.setFullName(editFullName.getText().toString());
                    intent.putExtra(Constants.USER_OBJECT_INTENT_KEY, userObject);
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
                Uri uriUserAadhar;
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    uriUserAadhar = data.getData();
                    strUserAadhar = uriUserAadhar.toString();
                    displaySuccessMessage();
                } else {
                    displayErrorMessage();
                }
                break;
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK && data.getExtras() != null && data.getData() != null) {
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
