package com.ynov.malo.worldtravel.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ynov.malo.worldtravel.CountriesRecycler.Country;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Malo on 14/03/2018.
 */

public class CountriesDAO {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static DatabaseHelper databaseHelper;

    public CountriesDAO(Context context) {databaseHelper = new DatabaseHelper(context);}

    public List<Country> getAllCountries() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                BaseContract.CountriesContract.COLUMN_COUNTRY_ID,
                BaseContract.CountriesContract.COLUMN_COUNTRY_NAME,
                BaseContract.CountriesContract.COLUMN_COUNTRY_CAPITAL_CITY,
                BaseContract.CountriesContract.COLUMN_COUNTRY_CONTINENT,
                BaseContract.CountriesContract.COLUMN_COUNTRY_COUNTRY_CODE,
                BaseContract.CountriesContract.COLUMN_COUNTRY_DATE
        };

        String sort = "date(" + BaseContract.CountriesContract.COLUMN_COUNTRY_DATE + ") ASC ";

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
                            cs.getInt(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_ID)),
                            cs.getString(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_NAME)),
                            cs.getString(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_CAPITAL_CITY)),
                            cs.getString(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_CONTINENT)),
                            cs.getString(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_COUNTRY_CODE)),
                            prepareDate(cs.getString(cs.getColumnIndex(BaseContract.CountriesContract.COLUMN_COUNTRY_DATE)))
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

    public boolean isCountryInDB(String name) {
        List<Country> list = getAllCountries();
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addCountry(Country country) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BaseContract.CountriesContract.COLUMN_COUNTRY_NAME, country.getName());
        values.put(BaseContract.CountriesContract.COLUMN_COUNTRY_CAPITAL_CITY, country.getCapitalCity());
        values.put(BaseContract.CountriesContract.COLUMN_COUNTRY_CONTINENT, country.getContinent());
        values.put(BaseContract.CountriesContract.COLUMN_COUNTRY_COUNTRY_CODE, country.getCountryCode());
        values.put(BaseContract.CountriesContract.COLUMN_COUNTRY_DATE, country.getDate());

        country.setId((int) db.insert(BaseContract.CountriesContract.TABLE_COUNTRIES, null, values));
    }

    public void deleteCountry(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String selection = BaseContract.CountriesContract._ID + " = ? ";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(BaseContract.CountriesContract.TABLE_COUNTRIES, selection, selectionArgs);
    }

    private String prepareDate(String initDate) {
        String month;
        Date date = null;

        try {
            date = format.parse(initDate);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        switch(new SimpleDateFormat("MM").format(date)) {
            case "01":
                month = "jan.";
                break;
            case "02":
                month = "fév.";
                break;
            case "03":
                month = "mar.";
                break;
            case "04":
                month = "avr.";
                break;
            case "05":
                month = "mai.";
                break;
            case "06":
                month = "juin";
                break;
            case "07":
                month = "jui.";
                break;
            case "08":
                month = "août";
                break;
            case "09":
                month = "sep.";
                break;
            case "10":
                month = "oct.";
                break;
            case "11":
                month = "nov.";
                break;
            case "12":
                month = "déc.";
                break;
            default:
                month = "null";
                break;
        }

        return new SimpleDateFormat("dd").format(date) + " " + month + " " + new SimpleDateFormat("yyyy").format(date);
    }
}