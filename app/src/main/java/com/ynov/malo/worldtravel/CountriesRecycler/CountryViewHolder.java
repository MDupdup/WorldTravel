package com.ynov.malo.worldtravel.CountriesRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.malo.worldtravel.Database.CountriesDAO;
import com.ynov.malo.worldtravel.R;

/**
 * Created by Malo on 14/03/2018.
 */

public class CountryViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewcountryName = null;
    private ImageView imageViewcountryFlag = null;
    private TextView textViewcountryCapitalCity = null;
    private TextView textViewcountryContinent = null;
    private TextView textViewcountryDate = null;
    private ImageView imageViewDelete = null;

    CountriesDAO dao;


    public CountryViewHolder(View itemView) {
        super(itemView);

        textViewcountryName = itemView.findViewById(R.id.country_name);
        imageViewcountryFlag = itemView.findViewById(R.id.country_flag);
        textViewcountryCapitalCity = itemView.findViewById(R.id.country_capital_city);
        textViewcountryContinent = itemView.findViewById(R.id.country_continent);
        textViewcountryDate = itemView.findViewById(R.id.country_date);
        imageViewDelete = itemView.findViewById(R.id.delete_country_entry);

        imageViewDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {}
        });
    }

    public TextView getTextViewcountryName() {
        return textViewcountryName;
    }

    public ImageView getImageViewcountryFlag() {
        return imageViewcountryFlag;
    }

    public TextView getTextViewcountryCapitalCity() {
        return textViewcountryCapitalCity;
    }

    public TextView getTextViewcountryContinent() {
        return textViewcountryContinent;
    }

    public TextView getTextViewcountryDate() {
        return textViewcountryDate;
    }
}
