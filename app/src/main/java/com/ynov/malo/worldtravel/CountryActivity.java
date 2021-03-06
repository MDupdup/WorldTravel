package com.ynov.malo.worldtravel;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ynov.malo.worldtravel.Database.Country;
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

        // Ajout des "barres" de separation
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Appel de la methode de requete API
        callAPI();

        dao = new CountriesDAO(this);

        // Listener du recyclerview
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                if(selectedListCountriesFromAPI.size() > 0) {
                    listCountriesFromAPI = selectedListCountriesFromAPI;
                }
                
                if(dao.isCountryInDB(listCountriesFromAPI.get(position).getName())) {
                    CountriesDialogFragment dialog = new CountriesDialogFragment();
                    Bundle bundle = new Bundle();
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

    // Methode qui lance la troisieme activite, a savoir CalendarActivity, et envoie en bundle les informations
    // du pays selectionne par l'utilisateur
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


    // Methode qui cree une liste de substitution contenant les pays apres la recherche par mot cle
    // (cette methode est appelee par le bouton "OK" apres le champ de recherche)
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


    // Appel de l'API qui enregistre les resultats dans une nouvelle liste de pays
    public void callAPI() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("https://restcountries.eu/rest/v2/all?fields=name;capital;region;alpha2Code", new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONArray countriesArray = new JSONArray(response);

                    for(int i = 0; i < countriesArray.length(); i++) {
                        JSONObject countriesObject = countriesArray.getJSONObject(i);

                        listCountriesFromAPI.add(new Country(countriesObject.getString("name"),
                                countriesObject.getString("capital"),
                                countriesObject.getString("region"),
                                countriesObject.getString("alpha2Code")
                        ));
                    }

                    countriesSelectAdapter = new CountriesSelectAdapter(listCountriesFromAPI, CountryActivity.this);
                    recyclerView.setAdapter(countriesSelectAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
    }
}