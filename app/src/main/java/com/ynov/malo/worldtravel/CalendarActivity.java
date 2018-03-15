package com.ynov.malo.worldtravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.ynov.malo.worldtravel.CountriesRecycler.Country;
import com.ynov.malo.worldtravel.Database.CountriesDAO;

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
        finish();
    }



    public String prepareDate(DatePicker datePicker) {
        String day = String.valueOf(datePicker.getDayOfMonth());
        String month ="";
        String year = String.valueOf(datePicker.getYear());

        switch(datePicker.getMonth()) {
            case 0:
                month = "jan.";
                break;
            case 1:
                month = "fév.";
                break;
            case 2:
                month = "mar.";
                break;
            case 3:
                month = "avr.";
                break;
            case 4:
                month = "mai.";
                break;
            case 5:
                month = "juin";
                break;
            case 6:
                month = "jui.";
                break;
            case 7:
                month = "août";
                break;
            case 8:
                month = "sep.";
                break;
            case 9:
                month = "oct.";
                break;
            case 10:
                month = "nov.";
                break;
            case 11:
                month = "déc.";
                break;
        }

        return day + " " + month + " " + year;
    }
}
