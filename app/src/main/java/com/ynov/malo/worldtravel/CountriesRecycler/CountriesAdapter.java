package com.ynov.malo.worldtravel.CountriesRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.ynov.malo.worldtravel.Database.CountriesDAO;
import com.ynov.malo.worldtravel.R;
import com.ynov.malo.worldtravel.RecyclerTools.ClickListener;

import java.util.List;

/**
 * Created by Malo on 14/03/2018.
 */

public class CountriesAdapter extends RecyclerView.Adapter<CountryViewHolder> {

    private List<Country> listCountries = null;

    private CountriesDAO dao;

    private Context context;


    public CountriesAdapter(List<Country> listCountries, Context context) {
        this.listCountries = listCountries;
        this.context = context;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCountry = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_country_view,parent,false);
        return new CountryViewHolder(viewCountry);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, final int position) {
        holder.getTextViewcountryName().setText(listCountries.get(position).getName());
        holder.getTextViewcountryCapitalCity().setText("Capitale : " + listCountries.get(position).getCapitalCity());
        holder.getTextViewcountryContinent().setText("Continent : " + listCountries.get(position).getContinent());
        holder.getTextViewcountryDate().setText(listCountries.get(position).getDate());
        Picasso.with(context)
                .load("http://www.geognos.com/api/en/countries/flag/" + listCountries.get(position).getCountryCode() + ".png")
                .placeholder(context.getResources().getDrawable(R.drawable.ic_hourglass_loading))
                .error(context.getResources().getDrawable(R.drawable.ic_error))
                .into(holder.getImageViewcountryFlag());
    }


    @Override
    public int getItemCount() {
        return listCountries.size();
    }
}
