package com.ynov.malo.worldtravel.CountriesSelectRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.ynov.malo.worldtravel.Database.Country;
import com.ynov.malo.worldtravel.R;

import java.util.List;

/**
 * Created by Malo on 14/03/2018.
 */

public class CountriesSelectAdapter extends RecyclerView.Adapter<CountrySelectViewHolder> {

    private List<Country> listAllCountries = null;
    private Context context;

    // On recupere aussi le context pour Picasso
    public CountriesSelectAdapter(List<Country> listAllCountries, Context context) {
        this.listAllCountries = listAllCountries;
        this.context = context;
    }

    @Override
    public CountrySelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCountry = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_country_view,parent,false);
        return new CountrySelectViewHolder(viewCountry);
    }

    @Override
    public void onBindViewHolder(CountrySelectViewHolder holder, int position) {
        holder.getTextViewcountryName().setText(listAllCountries.get(position).getName());
        holder.getTextViewcountryCapitalCity().setText(context.getResources().getString(R.string.capital) + listAllCountries.get(position).getCapitalCity());
        holder.getTextViewcountryContinent().setText(context.getResources().getString(R.string.region) + listAllCountries.get(position).getContinent());

        // Charge le drapeau du pays depuis le site geognos.com, affiche un sablier le temps du chargement et une icone d'erreur s'il
        // N'y a pas de drapeau correspondant au pays recherche sur le site
        Picasso.with(context)
                .load("http://www.geognos.com/api/en/countries/flag/" + listAllCountries.get(position).getCountryCode() + ".png")
                .placeholder(context.getResources().getDrawable(R.drawable.ic_hourglass_loading))
                .error(context.getResources().getDrawable(R.drawable.ic_error))
                .into(holder.getImageViewcountryFlag());
    }

    @Override
    public int getItemCount() {
        return listAllCountries.size();
    }
}
