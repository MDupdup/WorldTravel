package com.ynov.malo.worldtravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.ynov.malo.worldtravel.CountriesRecycler.Country;
import com.ynov.malo.worldtravel.Database.CountriesDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    DatePicker datePicker;

    CountriesDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dao = new CountriesDAO(this);

        datePicker = findViewById(R.id.calendar_date_picker);

    }

    public void validateAll(View v) {

        dao.addCountry(new Country(getIntent().getStringExtra("name"),
                getIntent().getStringExtra("capitalcity"),
                getIntent().getStringExtra("continent"),
                getIntent().getStringExtra("countrycode"),
                prepareDate(datePicker)));

        Intent intentToMain = new Intent(CalendarActivity.this,MainActivity.class);
        //intentToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToMain);
        CountryActivity.ca.finish();
        finish();
    }



    public String prepareDate(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(calendar.getTime());
    }
}
