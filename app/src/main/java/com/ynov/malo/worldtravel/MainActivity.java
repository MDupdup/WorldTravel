package com.ynov.malo.worldtravel;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ynov.malo.worldtravel.CountriesRecycler.CountriesAdapter;
import com.ynov.malo.worldtravel.CountriesRecycler.Country;
import com.ynov.malo.worldtravel.Database.CountriesDAO;
import com.ynov.malo.worldtravel.RecyclerDivider.DividerItemDecorator;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    CountriesDAO dao;

    GestureDetector gestureDetector;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new CountriesDAO(this);

        recyclerView = findViewById(R.id.list_visited_places);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CountriesAdapter countriesAdapter = new CountriesAdapter(dao.getAllCountries(),this);
        recyclerView.setAdapter(countriesAdapter);

        gestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent event) {
                        return true;
                    }
                });

        recyclerView.addOnItemTouchListener(this);
    }


    public void addCountryToVisit(View v) {
        Intent intentListAllCountries = new Intent(this,CountryActivity.class);
        startActivity(intentListAllCountries);
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent me) {
        if (gestureDetector.onTouchEvent(me))
        {
            View child = recyclerView.findChildViewUnder(me.getX(),
                    me.getY());
            if (child != null)
            {
                final int position = recyclerView.getChildAdapterPosition(child);

                ImageView deletePic = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.delete_country_entry);

                deletePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dao.deleteCountry(position);
                        Toast.makeText(MainActivity.this,"Issou",Toast.LENGTH_LONG).show();
                    }
                });

                return true;
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
