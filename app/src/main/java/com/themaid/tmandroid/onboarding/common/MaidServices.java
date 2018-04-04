package com.themaid.tmandroid.onboarding.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.MaidCharges;
import com.themaid.tmandroid.R;
import com.themaid.tmandroid.onboarding.maid.WorkDetails;
import com.themaid.tmandroid.onboarding.pojo.MaidServiceObject;
import com.themaid.tmandroid.onboarding.pojo.UserObject;

public class MaidServices extends AppCompatActivity {

    private static final String RUPEES = "Rs ";

    private ImageView backButton;
    private CheckBox checkboxVegCooking;
    private CheckBox checkboxNonVegCooking;
    private CheckBox checkboxHouseCleaning;
    private CheckBox checkboxUtensilsWashing;
    private CheckBox checkboxClothesWashing;
    private CheckBox checkboxDusting;
    private CheckBox checkboxDeepCleaning;

    private String vegCookingCharges;
    private String nonVegCookingCharges;
    private String houseCleaningCharges;
    private String utensilsWashingCharges;
    private String clothesWashingCharges;
    private String dustingCharges;
    private String deepCleaningCharges;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_services);

        /* Getting Id's for all the views */
        checkboxVegCooking = findViewById(R.id.checkboxVegCooking);
        checkboxNonVegCooking = findViewById(R.id.checkboxNonVegCooking);
        checkboxHouseCleaning = findViewById(R.id.checkboxHouseCleaning);
        checkboxUtensilsWashing = findViewById(R.id.checkboxUtensilsWashing);
        checkboxClothesWashing = findViewById(R.id.checkboxClothesWashing);
        checkboxDusting = findViewById(R.id.checkboxDusting);
        checkboxDeepCleaning = findViewById(R.id.checkboxDeepCleaning);
        final TextView textMaidServicesTitle = findViewById(R.id.textMaidServicesTitle);
        final TextView textSelectServices = findViewById(R.id.textSelectServices);
        final TextView textCooking = findViewById(R.id.textCooking);
        final TextView textHousehold = findViewById(R.id.textHousehold);
        final Button buttonNext = findViewById(R.id.buttonAddWorkDetails);

        backButton = findViewById(R.id.backButton);

        /* Setting font for all the views */
        checkboxVegCooking.setTypeface(Constants.setLatoLightFont(this));
        checkboxNonVegCooking.setTypeface(Constants.setLatoLightFont(this));
        checkboxHouseCleaning.setTypeface(Constants.setLatoLightFont(this));
        checkboxUtensilsWashing.setTypeface(Constants.setLatoLightFont(this));
        checkboxClothesWashing.setTypeface(Constants.setLatoLightFont(this));
        checkboxDusting.setTypeface(Constants.setLatoLightFont(this));
        checkboxDeepCleaning.setTypeface(Constants.setLatoLightFont(this));
        textMaidServicesTitle.setTypeface(Constants.setLatoLightFont(this));
        textSelectServices.setTypeface(Constants.setLatoLightFont(this));
        textCooking.setTypeface(Constants.setLatoLightItalicFont(this));
        textHousehold.setTypeface(Constants.setLatoLightItalicFont(this));
        buttonNext.setTypeface(Constants.setLatoLightFont(this));

        /*We change the text of views if user type is Customer*/
        if (getIntent().getStringExtra(Constants.FIREBASE_CHILD_USER_TYPE) != null) {
            textSelectServices.setText(R.string.select_services_text_customer);
            buttonNext.setText(R.string.calculate_maid_charges);
            buttonNext.setOnClickListener(v -> {
                if (isServiceSelected()) {
                    showMaidChargesDialog();
                } else {
                    Toast.makeText(MaidServices.this, "Please select at least one service", Toast.LENGTH_SHORT).show();
                }
            });
            backButton.setVisibility(View.INVISIBLE);
        } else {
            /* Adding events to Add Work Details button */
            buttonNext.setOnClickListener(view -> {
                Intent intent = new Intent(MaidServices.this, WorkDetails.class);

                MaidServiceObject maidServiceObject = new MaidServiceObject();
                maidServiceObject.setVegCooking(checkboxVegCooking.isChecked());
                maidServiceObject.setNonVegCooking(checkboxNonVegCooking.isChecked());
                maidServiceObject.setHouseCleaning(checkboxHouseCleaning.isChecked());
                maidServiceObject.setUtensilsWashing(checkboxUtensilsWashing.isChecked());
                maidServiceObject.setClothesWashing(checkboxClothesWashing.isChecked());
                maidServiceObject.setDusting(checkboxDusting.isChecked());
                maidServiceObject.setDeepCleaning(checkboxDeepCleaning.isChecked());

                UserObject userObject = (UserObject) getIntent().getSerializableExtra(Constants.USER_OBJECT_INTENT_KEY);
                userObject.setMaidServiceObject(maidServiceObject);
                intent.putExtra(Constants.USER_OBJECT_INTENT_KEY, userObject);

                startActivity(intent);
            });

            /* Adding events to back button */
            backButton.setOnClickListener(view -> onBackPressed());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userPrivateInfo = database.getReference(Constants.FIREBASE_CHILD_MAIDCHARGES);
        userPrivateInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MaidCharges maidCharges = dataSnapshot.getValue(MaidCharges.class);
                if (maidCharges != null) {
                    vegCookingCharges = RUPEES + maidCharges.getVegCooking();
                    nonVegCookingCharges = RUPEES + maidCharges.getNonVegCooking();
                    houseCleaningCharges = RUPEES + maidCharges.getHouseCleaning();
                    utensilsWashingCharges = RUPEES + maidCharges.getUtensilsWashing();
                    clothesWashingCharges = RUPEES + maidCharges.getClothesWashing();
                    dustingCharges = RUPEES + maidCharges.getDusting();
                    deepCleaningCharges = RUPEES + maidCharges.getDeepCleaning();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * This dialog displays a summary of Maid charges. It's a kind of dynamic dialog which keeps
     * updating the total charges based on the service selected by the customers. Here the charges
     * for each service is obtained from the firebase see {@link #onStart} method.
     */
    private void showMaidChargesDialog() {
        final Dialog dialog = new Dialog(MaidServices.this);
        dialog.setContentView(R.layout.custom_dialog_maid_charges);

        TextView textMaidCharges = dialog.findViewById(R.id.textMaidCharges);
        TextView textChargesMayVary = dialog.findViewById(R.id.textChargesMayVary);
        TextView textVegCooking = dialog.findViewById(R.id.textVegCooking);
        TextView textNonVegCooking = dialog.findViewById(R.id.textNonVegCooking);
        TextView textHouseCleaning = dialog.findViewById(R.id.textHouseCleaning);
        TextView textUtensilsWashing = dialog.findViewById(R.id.textUtensilsWashing);
        TextView textClothesWashing = dialog.findViewById(R.id.textClothesWashing);
        TextView textDusting = dialog.findViewById(R.id.textDusting);
        TextView textDeepCleaning = dialog.findViewById(R.id.textDeepCleaning);
        TextView textTotalCharges = dialog.findViewById(R.id.textTotalCharges);

        TextView textVegCookingCharges = dialog.findViewById(R.id.textVegCookingCharges);
        TextView textNonVegCookingCharges = dialog.findViewById(R.id.textNonVegCookingCharges);
        TextView textHouseCleaningCharges = dialog.findViewById(R.id.textHouseCleaningCharges);
        TextView textUtensilsWashingCharges = dialog.findViewById(R.id.textUtensilsWashingCharges);
        TextView textClothesWashingCharges = dialog.findViewById(R.id.textClothesWashingCharges);
        TextView textDustingCharges = dialog.findViewById(R.id.textDustingCharges);
        TextView textDeepCleaningCharges = dialog.findViewById(R.id.textDeepCleaningCharges);
        TextView textTotalChargesAmount = dialog.findViewById(R.id.textTotalChargesAmount);
        Button buttonConfirmBooking = dialog.findViewById(R.id.buttonConfirmBooking);

        textVegCookingCharges.setText(vegCookingCharges);
        textNonVegCookingCharges.setText(nonVegCookingCharges);
        textHouseCleaningCharges.setText(houseCleaningCharges);
        textUtensilsWashingCharges.setText(utensilsWashingCharges);
        textClothesWashingCharges.setText(clothesWashingCharges);
        textDustingCharges.setText(dustingCharges);
        textDeepCleaningCharges.setText(deepCleaningCharges);

        textMaidCharges.setTypeface(Constants.setLatoRegularFont(this));
        textChargesMayVary.setTypeface(Constants.setLatoLightItalicFont(this));
        textVegCooking.setTypeface(Constants.setLatoLightFont(this));
        textNonVegCooking.setTypeface(Constants.setLatoLightFont(this));
        textHouseCleaning.setTypeface(Constants.setLatoLightFont(this));
        textUtensilsWashing.setTypeface(Constants.setLatoLightFont(this));
        textClothesWashing.setTypeface(Constants.setLatoLightFont(this));
        textDusting.setTypeface(Constants.setLatoLightFont(this));
        textDeepCleaning.setTypeface(Constants.setLatoLightFont(this));
        textTotalCharges.setTypeface(Constants.setLatoLightFont(this));
        textVegCookingCharges.setTypeface(Constants.setLatoLightFont(this));
        textNonVegCookingCharges.setTypeface(Constants.setLatoLightFont(this));
        textHouseCleaningCharges.setTypeface(Constants.setLatoLightFont(this));
        textUtensilsWashingCharges.setTypeface(Constants.setLatoLightFont(this));
        textClothesWashingCharges.setTypeface(Constants.setLatoLightFont(this));
        textDustingCharges.setTypeface(Constants.setLatoLightFont(this));
        textDeepCleaningCharges.setTypeface(Constants.setLatoLightFont(this));
        textTotalChargesAmount.setTypeface(Constants.setLatoLightFont(this));
        buttonConfirmBooking.setTypeface(Constants.setLatoRegularFont(this));

        int integerVegCookingCharges = Integer.valueOf(textVegCookingCharges.getText().toString().substring(3));
        int integerNonVegCookingCharges = Integer.valueOf(textNonVegCookingCharges.getText().toString().substring(3));
        int integerHouseCleaningCharges = Integer.valueOf(textHouseCleaningCharges.getText().toString().substring(3));
        int integerUtensilsWashingCharges = Integer.valueOf(textUtensilsWashingCharges.getText().toString().substring(3));
        int integerClothesWashingCharges = Integer.valueOf(textClothesWashingCharges.getText().toString().substring(3));
        int integerDustingCharges = Integer.valueOf(textDustingCharges.getText().toString().substring(3));
        int integerDeepCleaningCharges = Integer.valueOf(textDeepCleaningCharges.getText().toString().substring(3));
        int integerTotalCharges = integerVegCookingCharges + integerNonVegCookingCharges + integerHouseCleaningCharges +
                integerUtensilsWashingCharges + integerClothesWashingCharges + integerDustingCharges + integerDeepCleaningCharges;
        String stringTotalCharges = RUPEES;

        if (checkboxVegCooking.isChecked()) {
            textVegCooking.setVisibility(View.VISIBLE);
            textVegCookingCharges.setVisibility(View.VISIBLE);
        } else {
            textVegCooking.setVisibility(View.GONE);
            textVegCookingCharges.setVisibility(View.GONE);
            integerTotalCharges = integerTotalCharges - integerVegCookingCharges;
        }

        if (checkboxNonVegCooking.isChecked()) {
            textNonVegCooking.setVisibility(View.VISIBLE);
            textNonVegCookingCharges.setVisibility(View.VISIBLE);
        } else {
            textNonVegCooking.setVisibility(View.GONE);
            textNonVegCookingCharges.setVisibility(View.GONE);
            integerTotalCharges = integerTotalCharges - integerNonVegCookingCharges;
        }

        if (checkboxHouseCleaning.isChecked()) {
            textHouseCleaning.setVisibility(View.VISIBLE);
            textHouseCleaningCharges.setVisibility(View.VISIBLE);
        } else {
            textHouseCleaning.setVisibility(View.GONE);
            textHouseCleaningCharges.setVisibility(View.GONE);
            integerTotalCharges = integerTotalCharges - integerHouseCleaningCharges;
        }

        if (checkboxUtensilsWashing.isChecked()) {
            textUtensilsWashing.setVisibility(View.VISIBLE);
            textUtensilsWashingCharges.setVisibility(View.VISIBLE);
        } else {
            textUtensilsWashing.setVisibility(View.GONE);
            textUtensilsWashingCharges.setVisibility(View.GONE);
            integerTotalCharges = integerTotalCharges - integerUtensilsWashingCharges;
        }

        if (checkboxClothesWashing.isChecked()) {
            textClothesWashing.setVisibility(View.VISIBLE);
            textClothesWashingCharges.setVisibility(View.VISIBLE);
        } else {
            textClothesWashing.setVisibility(View.GONE);
            textClothesWashingCharges.setVisibility(View.GONE);
            integerTotalCharges = integerTotalCharges - integerClothesWashingCharges;
        }

        if (checkboxDusting.isChecked()) {
            textDusting.setVisibility(View.VISIBLE);
            textDustingCharges.setVisibility(View.VISIBLE);
        } else {
            textDusting.setVisibility(View.GONE);
            textDustingCharges.setVisibility(View.GONE);
            integerTotalCharges = integerTotalCharges - integerDustingCharges;
        }

        if (checkboxDeepCleaning.isChecked()) {
            textDeepCleaning.setVisibility(View.VISIBLE);
            textDeepCleaningCharges.setVisibility(View.VISIBLE);
        } else {
            textDeepCleaning.setVisibility(View.GONE);
            textDeepCleaningCharges.setVisibility(View.GONE);
            integerTotalCharges = integerTotalCharges - integerDeepCleaningCharges;
        }

        stringTotalCharges = stringTotalCharges + String.valueOf(integerTotalCharges);

        textTotalChargesAmount.setText(stringTotalCharges);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (backButton.getVisibility() == View.INVISIBLE) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            super.onBackPressed();
        }
    }

    public boolean isServiceSelected() {
        return checkboxVegCooking.isChecked() ||
                checkboxNonVegCooking.isChecked() ||
                checkboxHouseCleaning.isChecked() ||
                checkboxUtensilsWashing.isChecked() ||
                checkboxClothesWashing.isChecked() ||
                checkboxDusting.isChecked() ||
                checkboxDeepCleaning.isChecked();
    }
}
