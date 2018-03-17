package com.ynov.malo.worldtravel;

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
import com.ynov.malo.worldtravel.RecyclerTools.DividerItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountryActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private List<Country> listCountriesFromAPI = new ArrayList<>();
    private List<Country> selectedListCountriesFromAPI = new ArrayList<>();
    CountriesSelectAdapter countriesSelectAdapter;

    EditText searchEditText;
    RecyclerView recyclerView;

    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        searchEditText = findViewById(R.id.search_input);

        recyclerView = findViewById(R.id.list_all_countries);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        callAPI();

        gestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent event) {
                        return true;
                    }
                });

        recyclerView.addOnItemTouchListener(this);

    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent)
    {
        if (gestureDetector.onTouchEvent(motionEvent))
        {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(),
                    motionEvent.getY());
            if (child != null)
            {
                int position = recyclerView.getChildAdapterPosition(child);

                Bundle bundle = new Bundle();
                Intent intentToCalendar = new Intent(CountryActivity.this,CalendarActivity.class);
                bundle.putString("name",listCountriesFromAPI.get(position).getName());
                bundle.putString("capitalcity",listCountriesFromAPI.get(position).getCapitalCity());
                bundle.putString("continent",listCountriesFromAPI.get(position).getContinent());
                bundle.putString("countrycode",listCountriesFromAPI.get(position).getCountryCode());
                intentToCalendar.putExtras(bundle);

                startActivity(intentToCalendar);

                return true;
            }
        }
        return false;
    }


    public void searchForCountries(View view) {
        for(int i = 0; i < listCountriesFromAPI.size(); i++) {
            if(listCountriesFromAPI.get(i).getName().toLowerCase().equals(searchEditText.getText().toString().toLowerCase())) {
                selectedListCountriesFromAPI.add(listCountriesFromAPI.get(i));
            }
        }
        countriesSelectAdapter = new CountriesSelectAdapter(selectedListCountriesFromAPI,this);
        recyclerView.setAdapter(countriesSelectAdapter);
    }


    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

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
