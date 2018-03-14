package com.ynov.malo.worldtravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class CountryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);


    }

    public void callAPI() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

    }
}
