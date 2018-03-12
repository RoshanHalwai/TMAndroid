package com.themaid.tmandroid;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileCreated extends AppCompatActivity {

    UserObject userObject;

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
        allUsers = database.getReference("users").child("all");
        userPrivateInfo = database.getReference("users").child("private");

        /* Getting reference to Firebase - Storage */
        storageReference = FirebaseStorage.getInstance().getReference("private").child("users");

        final TextView textProfileCreatedTitle = findViewById(R.id.textProfileCreatedTitle);
        final TextView textProfileCreated = findViewById(R.id.textProfileCreated);
        final ImageView backButton = findViewById(R.id.backButton);

        final Typeface latoLight = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        textProfileCreatedTitle.setTypeface(latoLight);
        textProfileCreated.setTypeface(latoLight);

        userObject = (UserObject) getIntent().getSerializableExtra("UserObject");

        backButton.setOnClickListener(view -> onBackPressed());

        storeUserInformation();
    }

    private void storeUserInformation() {
        UID = userObject.getUID();
        if (userObject.getUserType().equals("Maid")) {
            uriUserAadhar = Uri.parse(userObject.getUriUserAadhar());
            allUsers.child("maids").child(userObject.getMobileNumber()).setValue(UID);
            uploadAadharCard();
        } else {
            allUsers.child("customers").child(userObject.getMobileNumber()).setValue(UID);
        }
        userPrivateInfo.child(UID).child("mobileNumber").setValue(userObject.getMobileNumber());
        userPrivateInfo.child(UID).child("fullName").setValue(userObject.getFullName());
        userPrivateInfo.child(UID).child("userType").setValue(userObject.getUserType());
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

                        userPrivateInfo.child(UID).child("aadharCard").setValue(taskSnapshot.getDownloadUrl().toString());
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

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
