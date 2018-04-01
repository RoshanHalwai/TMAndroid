package com.themaid.tmandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.themaid.tmandroid.onboarding.pojo.CustomerRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MaidBookings extends AppCompatActivity {

    CustomerRequest customerRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_bookings);

        FirebaseMessaging.getInstance().subscribeToTopic("SEATTLE_WEATHER");

        /* Getting Id for all the views */
        final TextView textBookingTitle = findViewById(R.id.textBookingTitle);
        final TextView textTodayDate = findViewById(R.id.textTodayDate);
        final TextView textNumberOfBookings = findViewById(R.id.textNumberOfBookings);
        final TextView textBookings = findViewById(R.id.textBookings);
        final TextView textTodayEarnings = findViewById(R.id.textTodayEarnings);
        final TextView textEarnings = findViewById(R.id.textEarnings);
        final TextView textWaitingForCustomers = findViewById(R.id.textWaitingForCustomers);
        final Button buttonAccountSummary = findViewById(R.id.buttonAccountSummary);
        final Button buttonLogout = findViewById(R.id.buttonLogout);
        final com.wang.avi.AVLoadingIndicatorView animationWaitingForCustomers = findViewById(R.id.animationWaitingForCustomers);

        final TextView textCustomerName = findViewById(R.id.textCustomerName);
        final TextView textCustomerPhone = findViewById(R.id.textCustomerPhone);
        final Button buttonNavigate = findViewById(R.id.buttonNavigate);

        /* Setting font for all the views */
        final Typeface latoLightItalic = Typeface.createFromAsset(getAssets(), "fonts/Lato-LightItalic.ttf");
        textBookingTitle.setTypeface(Constants.setLatoLightFont(this));
        textTodayDate.setTypeface(Constants.setLatoLightFont(this));
        textNumberOfBookings.setTypeface(Constants.setLatoLightFont(this));
        textBookings.setTypeface(Constants.setLatoLightFont(this));
        textTodayEarnings.setTypeface(Constants.setLatoLightFont(this));
        textEarnings.setTypeface(Constants.setLatoLightFont(this));
        textWaitingForCustomers.setTypeface(latoLightItalic);
        buttonAccountSummary.setTypeface(Constants.setLatoLightFont(this));
        buttonLogout.setTypeface(Constants.setLatoLightFont(this));

        textCustomerName.setTypeface(Constants.setLatoLightFont(this));
        textCustomerPhone.setTypeface(Constants.setLatoLightFont(this));
        buttonNavigate.setTypeface(Constants.setLatoLightFont(this));

        customerRequest = (CustomerRequest) getIntent().getSerializableExtra("CustomerRequest");
        if (customerRequest == null) {
            /* Show Waiting Animation*/
            textCustomerName.setVisibility(View.GONE);
            textCustomerPhone.setVisibility(View.GONE);
            buttonNavigate.setVisibility(View.GONE);
            textWaitingForCustomers.setVisibility(View.VISIBLE);
            animationWaitingForCustomers.setVisibility(View.VISIBLE);
            animationWaitingForCustomers.smoothToShow();
        } else {
            textCustomerName.setText(customerRequest.getCustomerName());
            textCustomerPhone.setText(customerRequest.getCustomerPhone());
            textCustomerName.setVisibility(View.VISIBLE);
            textCustomerPhone.setVisibility(View.VISIBLE);
            buttonNavigate.setVisibility(View.VISIBLE);
            textWaitingForCustomers.setVisibility(View.GONE);
            animationWaitingForCustomers.setVisibility(View.GONE);
        }

        /* Set today's date */
        textTodayDate.setText(getTodayDate());

        buttonNavigate.setOnClickListener(view -> {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + customerRequest.getCustomerAddress() + "&avoid=tf");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        buttonLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MaidBookings.this, MainActivity.class));
        });

    }

    private String getTodayDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String day = dayFormat.format(c);
        String month = monthFormat.format(c);
        day = day + getDayOfMonthSuffix(Integer.valueOf(day)) + " " + month;
        return day;
    }

    private String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
