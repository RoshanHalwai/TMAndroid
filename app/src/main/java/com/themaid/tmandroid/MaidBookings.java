package com.themaid.tmandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MaidBookings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_bookings);

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

        /* Setting font for all the views */
        final Typeface latoLight = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        final Typeface latoLightItalic = Typeface.createFromAsset(getAssets(), "fonts/Lato-LightItalic.ttf");
        textBookingTitle.setTypeface(latoLight);
        textTodayDate.setTypeface(latoLight);
        textNumberOfBookings.setTypeface(latoLight);
        textBookings.setTypeface(latoLight);
        textTodayEarnings.setTypeface(latoLight);
        textEarnings.setTypeface(latoLight);
        textWaitingForCustomers.setTypeface(latoLightItalic);
        buttonAccountSummary.setTypeface(latoLight);
        buttonLogout.setTypeface(latoLight);

        /* Show Waiting Animation*/
        animationWaitingForCustomers.smoothToShow();

        /* Set today's date */
        textTodayDate.setText(getTodayDate());

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
