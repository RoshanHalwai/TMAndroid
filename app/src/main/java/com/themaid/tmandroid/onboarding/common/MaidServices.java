package com.themaid.tmandroid.onboarding.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.themaid.tmandroid.Constants;
import com.themaid.tmandroid.R;
import com.themaid.tmandroid.onboarding.maid.WorkDetails;
import com.themaid.tmandroid.onboarding.pojo.MaidServiceObject;
import com.themaid.tmandroid.onboarding.pojo.UserObject;

public class MaidServices extends AppCompatActivity {

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_services);

        /* Getting Id's for all the views */
        final TextView textMaidServicesTitle = findViewById(R.id.textMaidServicesTitle);
        final TextView textSelectServices = findViewById(R.id.textSelectServices);
        final TextView textCooking = findViewById(R.id.textCooking);
        final CheckBox checkboxVegCooking = findViewById(R.id.checkboxVegCooking);
        final CheckBox checkboxNonVegCooking = findViewById(R.id.checkboxNonVegCooking);
        final TextView textHousehold = findViewById(R.id.textHousehold);
        final CheckBox checkboxHouseCleaning = findViewById(R.id.checkboxHouseCleaning);
        final CheckBox checkboxUtensilsWashing = findViewById(R.id.checkboxUtensilsWashing);
        final CheckBox checkboxClothesWashing = findViewById(R.id.checkboxClothesWashing);
        final CheckBox checkboxDusting = findViewById(R.id.checkboxDusting);
        final CheckBox checkboxDeepCleaning = findViewById(R.id.checkboxDeepCleaning);
        final Button buttonNext = findViewById(R.id.buttonAddWorkDetails);
        backButton = findViewById(R.id.backButton);

        /* Setting font for all the views */
        textMaidServicesTitle.setTypeface(Constants.setLatoLightFont(this));
        textSelectServices.setTypeface(Constants.setLatoLightFont(this));
        textCooking.setTypeface(Constants.setLatoLightItalicFont(this));
        checkboxVegCooking.setTypeface(Constants.setLatoLightFont(this));
        checkboxNonVegCooking.setTypeface(Constants.setLatoLightFont(this));
        textHousehold.setTypeface(Constants.setLatoLightItalicFont(this));
        checkboxHouseCleaning.setTypeface(Constants.setLatoLightFont(this));
        checkboxUtensilsWashing.setTypeface(Constants.setLatoLightFont(this));
        checkboxClothesWashing.setTypeface(Constants.setLatoLightFont(this));
        checkboxDusting.setTypeface(Constants.setLatoLightFont(this));
        checkboxDeepCleaning.setTypeface(Constants.setLatoLightFont(this));
        buttonNext.setTypeface(Constants.setLatoLightFont(this));

        if (getIntent().getStringExtra(Constants.FIREBASE_CHILD_USER_TYPE) != null) {
            textSelectServices.setText(R.string.select_services_text_customer);
            buttonNext.setText(R.string.calculate_maid_charges);
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

}
