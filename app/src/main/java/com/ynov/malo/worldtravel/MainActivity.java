package com.ynov.malo.worldtravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ynov.malo.worldtravel.CountriesRecycler.CountriesAdapter;
import com.ynov.malo.worldtravel.CountriesRecycler.Country;
import com.ynov.malo.worldtravel.Database.CountriesDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountriesDAO dao = new CountriesDAO(this);

        RecyclerView recyclerView = findViewById(R.id.list_visited_places);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CountriesAdapter countriesAdapter = new CountriesAdapter(dao.getAllCountries());
        recyclerView.setAdapter(countriesAdapter);
    }

    public void addCountryToVisit(View v) {
        Intent intentListAllCountries = new Intent(this,CountryActivity.class);
        //intentListAllCountries.addFlags(Intent.)
        startActivity(intentListAllCountries);
    }

}
