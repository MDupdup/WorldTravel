package com.ynov.malo.worldtravel.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ynov.malo.worldtravel.CountriesRecycler.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malo on 14/03/2018.
 */

public class CountriesDAO {

    public static DatabaseHelper databaseHelper;

    public CountriesDAO(Context context) {databaseHelper = new DatabaseHelper(context);}

    public List<Country> getAllCountries() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                BaseContract.CountriesContract.COLUMN_COUNTRY_NAME,
                BaseContract.CountriesContract.COLUMN_COUNTRY_CAPITAL_CITY,
                BaseContract.CountriesContract.COLUMN_COUNTRY_CONTINENT,
                BaseContract.CountriesContract.COLUMN_COUNTRY_FLAG,
                BaseContract.CountriesContract.COLUMN_COUNTRY_DATE
        };

        String sort = BaseContract.CountriesContract.COLUMN_COUNTRY_NAME + " ASC ";

        Cursor cs = db.query(
                BaseContract.CountriesContract.TABLE_COUNTRIES,
                projection,
                null,
                null,
                null,
                null,
                sort,
                null
        );

        List<Country> listCountries = new ArrayList<>();

        if(cs != null) {
            try {
                cs.moveToFirst();
                while(!cs.isAfterLast()) {
                    listCountries.add(new Country(
                            cs.getString(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_NAME)),
                            cs.getString(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_CAPITAL_CITY)),
                            cs.getString(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_CONTINENT)),
                            cs.getBlob(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_FLAG)),
                            cs.getString(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_DATE))
                            )
                    );
                    cs.moveToNext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cs.close();
            }
        }

        return listCountries;
    }

    public void addCountry(Country country) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BaseContract.CountriesContract.COLUMN_COUNTRY_NAME, country.getName());
        values.put(BaseContract.CountriesContract.COLUMN_COUNTRY_NAME, country.getCapitalCity());
        values.put(BaseContract.CountriesContract.COLUMN_COUNTRY_NAME, country.getContinent());
        values.put(BaseContract.CountriesContract.COLUMN_COUNTRY_NAME, country.getFlag());
        values.put(BaseContract.CountriesContract.COLUMN_COUNTRY_NAME, country.getDate());

        country.setId(db.insert(BaseContract.CountriesContract.TABLE_COUNTRIES, null, values));
    }

    public void deleteCountry(Country country) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String selection = BaseContract.CountriesContract._ID + " = ? ";
        String[] selectionArgs = {String.valueOf(country.getId())};

        db.delete(BaseContract.CountriesContract.TABLE_COUNTRIES, selection, selectionArgs);
    }
}
