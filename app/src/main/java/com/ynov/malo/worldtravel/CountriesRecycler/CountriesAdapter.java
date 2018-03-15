package com.ynov.malo.worldtravel.CountriesRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.ynov.malo.worldtravel.R;

import java.util.List;

/**
 * Created by Malo on 14/03/2018.
 */

public class CountriesAdapter extends RecyclerView.Adapter<CountryViewHolder> {

    private List<Country> listCountries = null;

    private Context context;

    public CountriesAdapterListener onClickListener;

    public CountriesAdapter(List<Country> listCountries, Context context) {
        this.listCountries = listCountries;
        this.context = context;
    }

    public CountriesAdapter(List<Country> listCountries, Context context, CountriesAdapterListener listener) {
        this.listCountries = listCountries;
        this.context = context;
        this.onClickListener = listener;

    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCountry = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_country_view,parent,false);
        return new CountryViewHolder(viewCountry);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, final int position) {
        holder.getTextViewcountryName().setText(listCountries.get(position).getName());
        holder.getTextViewcountryCapitalCity().setText(listCountries.get(position).getCapitalCity());
        holder.getTextViewcountryContinent().setText(listCountries.get(position).getContinent());
        holder.getTextViewcountryDate().setText(listCountries.get(position).getDate());
        Picasso.with(context)
                .load("http://www.geognos.com/api/en/countries/flag/" + listCountries.get(position).getCountryCode() + ".png")
                .placeholder(context.getResources().getDrawable(R.drawable.ic_hourglass_loading))
                .error(context.getResources().getDrawable(R.drawable.ic_error))
                .into(holder.getImageViewcountryFlag());

        holder.getImageViewDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.imageViewOnClick(view,position);
            }
        });
    }


    public interface CountriesAdapterListener {
        void imageViewOnClick(View v, int position);
    }

    @Override
    public int getItemCount() {
        return listCountries.size();
    }
}
