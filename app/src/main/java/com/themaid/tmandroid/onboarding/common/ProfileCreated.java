package com.themaid.tmandroid.onboarding.common;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.MaidBookings;
import com.themaid.tmandroid.R;
import com.themaid.tmandroid.onboarding.pojo.UserObject;

public class ProfileCreated extends AppCompatActivity {

    private UserObject userObject;

    private DatabaseReference allUsers;
    private DatabaseReference userPrivateInfo;
    private StorageReference storageReference;

    private String UID;
    private Uri uriUserAadhar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_created);

        /* Getting reference to Firebase - Real time database*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        allUsers = database.getReference(Constants.FIREBASE_CHILD_USERS).child(Constants.FIREBASE_CHILD_ALL);
        userPrivateInfo = database.getReference(Constants.FIREBASE_CHILD_USERS).child(Constants.FIREBASE_CHILD_PRIVATE);

        /* Getting reference to Firebase - Storage */
        storageReference = FirebaseStorage.getInstance().getReference(Constants.FIREBASE_CHILD_PRIVATE).child(Constants.FIREBASE_CHILD_USERS);

        final TextView textProfileCreatedTitle = findViewById(R.id.textProfileCreatedTitle);
        final TextView textProfileCreated = findViewById(R.id.textProfileCreated);
        final Button buttonMyAccount = findViewById(R.id.buttonMyAccount);
        final ImageView backButton = findViewById(R.id.backButton);

        textProfileCreatedTitle.setTypeface(Constants.setLatoLightFont(this));
        textProfileCreated.setTypeface(Constants.setLatoLightFont(this));
        buttonMyAccount.setTypeface(Constants.setLatoLightFont(this));

        userObject = (UserObject) getIntent().getSerializableExtra(Constants.USER_OBJECT_INTENT_KEY);

        if (userObject.getUserType().equals("Maid"))
            buttonMyAccount.setOnClickListener(view -> startActivity(new Intent(ProfileCreated.this, MaidBookings.class)));
        else {
            Intent intent = new Intent(ProfileCreated.this, MaidServices.class);
            intent.putExtra(Constants.FIREBASE_CHILD_USER_TYPE, Constants.USER_TYPE_CUSTOMER);
            buttonMyAccount.setOnClickListener(view -> startActivity(intent));
        }
        backButton.setOnClickListener(view -> onBackPressed());

        storeUserInformation();
    }

    private void storeUserInformation() {
        UID = userObject.getUID();
        if (userObject.getUserType().equals(Constants.USER_TYPE_MAID)) {
            uriUserAadhar = Uri.parse(userObject.getUriUserAadhar());
            allUsers.child(Constants.FIREBASE_CHILD_MAIDS).child(userObject.getMobileNumber()).setValue(UID);
            userPrivateInfo.child(UID).child(Constants.FIREBASE_CHILD_SERVICES).setValue(userObject.getMaidServiceObject());
            uploadAadharCard();
        } else {
            allUsers.child(Constants.FIREBASE_CHILD_CUSTOMERS).child(userObject.getMobileNumber()).setValue(UID);
        }
        userPrivateInfo.child(UID).child(Constants.FIREBASE_CHILD_MOBILE_NUMBER).setValue(userObject.getMobileNumber());
        userPrivateInfo.child(UID).child(Constants.FIREBASE_CHILD_FULL_NAME).setValue(userObject.getFullName());
        userPrivateInfo.child(UID).child(Constants.FIREBASE_CHILD_USER_TYPE).setValue(userObject.getUserType());
    }

    private void uploadAadharCard() {
        //checking if file is available
        if (uriUserAadhar != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child(UID).child("/uploads" + System.currentTimeMillis() + "." + getFileExtension(uriUserAadhar));

            //adding the file to reference
            sRef.putFile(uriUserAadhar)
                    .addOnSuccessListener(taskSnapshot -> {
                        //dismissing the progress dialog
                        progressDialog.dismiss();

                        //displaying success toast
                        Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                        if (taskSnapshot.getDownloadUrl() != null) {
                            userPrivateInfo.child(UID).child(Constants.FIREBASE_CHILD_AADHAR_CARD).setValue(taskSnapshot.getDownloadUrl().toString());
                        }
                    })
                    .addOnFailureListener(exception -> {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        //displaying the upload progress
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    });
        }
    }

    @Nullable
    private String getFileExtension(@NonNull Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
