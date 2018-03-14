package com.ynov.malo.worldtravel.CountriesRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ynov.malo.worldtravel.R;

import java.util.List;

/**
 * Created by Malo on 14/03/2018.
 */

public class CountriesAdapter extends RecyclerView.Adapter<CountryViewHolder> {

    private List<Country> listCountries = null;

    public CountriesAdapter(List<Country> listCountries) {
        this.listCountries = listCountries;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCountry = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_country_view,parent,false);
        return new CountryViewHolder(viewCountry);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        holder.getTextViewcountryName().setText(listCountries.get(position).getName());
        holder.getTextViewcountryCapitalCity().setText(listCountries.get(position).getCapitalCity());
        holder.getTextViewcountryContinent().setText(listCountries.get(position).getContinent());
        holder.getTextViewcountryDate().setText(listCountries.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return listCountries.size();
    }
}
