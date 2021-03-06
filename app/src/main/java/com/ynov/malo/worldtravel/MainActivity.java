package com.ynov.malo.worldtravel;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ynov.malo.worldtravel.CountriesRecycler.CountriesAdapter;
import com.ynov.malo.worldtravel.Database.Country;
import com.ynov.malo.worldtravel.Database.CountriesDAO;
import com.ynov.malo.worldtravel.RecyclerTools.ClickListener;
import com.ynov.malo.worldtravel.RecyclerTools.DividerItemDecorator;
import com.ynov.malo.worldtravel.RecyclerTools.RecyclerTouchListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CountriesDAO dao;

    List<Country> listDBCountries;

    RecyclerView recyclerView;
    ImageView imageViewDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new CountriesDAO(this);

        recyclerView = findViewById(R.id.list_visited_places);
        recyclerView.setHasFixedSize(true);

        // Ajout des "barres" de separation
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listDBCountries = dao.getAllCountries();

        final CountriesAdapter countriesAdapter = new CountriesAdapter(listDBCountries, this);
        recyclerView.setAdapter(countriesAdapter);


        // Listener sur la corbeille pour supprimer un pays de la liste personnelle de l'utilisateur
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {
                imageViewDelete = view.findViewById(R.id.delete_country_entry);
                imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteCountryToVisit(listDBCountries.get(position).getId());
                        listDBCountries.remove(position);
                        countriesAdapter.notifyItemRemoved(position);
                        countriesAdapter.notifyItemRangeChanged(position, listDBCountries.size());
                    }
                });
            }
        }));
    }

    // Methode qui lance la seconde activite, a savoir CountryActivity, apres le clic de l'utilisateur sur le bouton "Ajouter un pays"
    public void addCountryToVisit(View v) {
        Intent intentListAllCountries = new Intent(this,CountryActivity.class);
        startActivity(intentListAllCountries);
    }

    // Methode qui supprime le pays dans la bdd apres le clic sur la corbeille de l'utilisateur
    public void deleteCountryToVisit(int position) {
        dao.deleteCountry(position);
    }
}
