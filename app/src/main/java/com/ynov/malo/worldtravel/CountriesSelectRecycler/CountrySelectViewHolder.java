package com.ynov.malo.worldtravel.CountriesSelectRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.malo.worldtravel.R;

/**
 * Created by Malo on 14/03/2018.
 */

public class CountrySelectViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewcountryName = null;
    private ImageView imageViewcountryFlag = null;
    private TextView textViewcountryCapitalCity = null;
    private TextView textViewcountryContinent = null;


    public CountrySelectViewHolder(View itemView) {
        super(itemView);

        textViewcountryName = itemView.findViewById(R.id.search_country_name);
        imageViewcountryFlag = itemView.findViewById(R.id.search_country_flag);
        textViewcountryCapitalCity = itemView.findViewById(R.id.search_country_capital_city);
        textViewcountryContinent = itemView.findViewById(R.id.search_country_continent);
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
}
