package com.ynov.malo.worldtravel;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ynov.malo.worldtravel.CountriesRecycler.Country;
import com.ynov.malo.worldtravel.CountriesSelectRecycler.CountriesSelectAdapter;
import com.ynov.malo.worldtravel.Database.CountriesDAO;
import com.ynov.malo.worldtravel.Dialog.CountriesDialogFragment;
import com.ynov.malo.worldtravel.RecyclerTools.ClickListener;
import com.ynov.malo.worldtravel.RecyclerTools.DividerItemDecorator;
import com.ynov.malo.worldtravel.RecyclerTools.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountryActivity extends AppCompatActivity{

    private List<Country> listCountriesFromAPI = new ArrayList<>();
    private List<Country> selectedListCountriesFromAPI = new ArrayList<>();
    CountriesSelectAdapter countriesSelectAdapter;
    CountriesDAO dao;

    EditText searchEditText;
    RecyclerView recyclerView;

    public static Activity ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        ca = this;

        searchEditText = findViewById(R.id.search_input);

        recyclerView = findViewById(R.id.list_all_countries);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        callAPI();

        dao = new CountriesDAO(this);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                if(selectedListCountriesFromAPI.size() > 0) {
                    listCountriesFromAPI = selectedListCountriesFromAPI;
                }
                
                if(dao.isCountryInDB(listCountriesFromAPI.get(position).getName())) {
                    CountriesDialogFragment dialog = new CountriesDialogFragment();
                    Bundle bundle = new Bundle();
                    System.out.print("Bonjour "+position);
                    bundle.putInt("position", position);
                    bundle.putString("name",listCountriesFromAPI.get(position).getName());

                    dialog.setArguments(bundle);

                    dialog.show(getSupportFragmentManager(), "askcoutries");

                } else {
                    startCalendarActivity(position);
                }
            }
        }));
    }


    public void startCalendarActivity(int position) {
        Bundle bundle = new Bundle();

        if(selectedListCountriesFromAPI.size() > 0) {
            listCountriesFromAPI = selectedListCountriesFromAPI;
        }

        Intent intentToCalendar = new Intent(CountryActivity.this, CalendarActivity.class);
        bundle.putString("name",listCountriesFromAPI.get(position).getName());
        bundle.putString("capitalcity",listCountriesFromAPI.get(position).getCapitalCity());
        bundle.putString("continent",listCountriesFromAPI.get(position).getContinent());
        bundle.putString("countrycode",listCountriesFromAPI.get(position).getCountryCode());
        intentToCalendar.putExtras(bundle);

        startActivity(intentToCalendar);
    }


    public void searchForCountries(View view) {
        selectedListCountriesFromAPI = new ArrayList<>();

        for(int i = 0; i < listCountriesFromAPI.size(); i++) {
            if(listCountriesFromAPI.get(i).getName().toLowerCase().contains(searchEditText.getText().toString().toLowerCase())) {
                selectedListCountriesFromAPI.add(listCountriesFromAPI.get(i));
            }
        }
        countriesSelectAdapter = new CountriesSelectAdapter(selectedListCountriesFromAPI,this);
        recyclerView.setAdapter(countriesSelectAdapter);
    }


    public void callAPI() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("https://restcountries.eu/rest/v2/all?fields=name;capital;region;alpha2Code", new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Toast.makeText(CountryActivity.this,"ça passe",Toast.LENGTH_LONG).show();
                try {
                    JSONArray countriesArray = new JSONArray(response);

                    for(int i = 0; i < countriesArray.length(); i++) {
                        JSONObject countriesObject = countriesArray.getJSONObject(i);

                        listCountriesFromAPI.add(new Country(countriesObject.getString("name"),
                                countriesObject.getString("capital"),
                                countriesObject.getString("region"),
                                countriesObject.getString("alpha2Code")
                        ));

                        System.out.print("mdr "+countriesObject.getString("name"));
                    }

                    countriesSelectAdapter = new CountriesSelectAdapter(listCountriesFromAPI, CountryActivity.this);
                    recyclerView.setAdapter(countriesSelectAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CountryActivity.this,"ça casse",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(CountryActivity.this,"ça casse",Toast.LENGTH_LONG).show();
            }
        });
    }
}