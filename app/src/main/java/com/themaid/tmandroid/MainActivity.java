package com.themaid.tmandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.themaid.tmandroid.onboarding.common.GettingStarted;
import com.themaid.tmandroid.onboarding.common.MaidServices;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * If current user is not null; we redirect the user to their main screen based on the user type
     * If user type is Customer then MaidServices activity is shown allowing users to select their services
     * Else we show MaidBookings activity where maids will be waiting for customers request.
     * Else if current user is null, it means user is logging in for first time so setup profile by calling
     * gettingStarted method.
     */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Dialog = new ProgressDialog(MainActivity.this);
            Dialog.setMessage("Collecting data...");
            Dialog.show();

            /* Getting reference to Firebase - Real time database*/
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userPrivateInfo = database.getReference(Constants.FIREBASE_CHILD_USERS).child(Constants.FIREBASE_CHILD_PRIVATE);
            userPrivateInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(currentUser.getUid())) {
                        String strCustomerType = Objects.requireNonNull(dataSnapshot.child(currentUser.getUid()).child(Constants.FIREBASE_CHILD_USER_TYPE).getValue()).toString();
                        if (strCustomerType.equals(Constants.USER_TYPE_CUSTOMER)) {
                            Intent intent = new Intent(MainActivity.this, MaidServices.class);
                            intent.putExtra(Constants.FIREBASE_CHILD_USER_TYPE, Constants.USER_TYPE_CUSTOMER);
                            startActivity(intent);
                        } else {
                            startActivity(new Intent(MainActivity.this, MaidBookings.class));
                        }
                    } else {
                        /*We reach at this point only when user is logged in the app, but user data is not available in back end
                         * hence we redirect user to initial profile setup*/
                        gettingStarted();
                    }
                    Dialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Dialog.dismiss();
                    gettingStarted();
                }
            });
        } else {
            gettingStarted();
        }
    }

    private void gettingStarted() {
        setContentView(R.layout.activity_main);

        /*Getting ID's for all the views */
        final TextView textAppTitle = findViewById(R.id.textAppTitle);
        final Button buttonGetStarted = findViewById(R.id.buttonGetStarted);

        /*Setting font for all the views */
        textAppTitle.setTypeface(Constants.setLatoLightFont(this));
        buttonGetStarted.setTypeface(Constants.setLatoLightFont(this));

        /* Adding events to Get Started Button */
        buttonGetStarted.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GettingStarted.class)));
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
