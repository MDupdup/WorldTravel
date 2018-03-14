package com.ynov.malo.worldtravel.Database;

import android.provider.BaseColumns;

/**
 * Created by Malo on 14/03/2018.
 */

public final class BaseContract {
    private BaseContract() {}

    public static class CountriesContract implements BaseColumns {
        public static final String TABLE_COUNTRIES = "countries";
        public static final String COLUMN_COUNTRY_ID = "id";
        public static final String COLUMN_COUNTRY_NAME = "name";
        public static final String COLUMN_COUNTRY_CAPITAL_CITY = "capital_city";
        public static final String COLUMN_COUNTRY_CONTINENT = "continent";
        public static final String COLUMN_COUNTRY_FLAG = "flag";
        public static final String COLUMN_COUNTRY_DATE = "date";
    }
}
