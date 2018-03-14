package com.ynov.malo.worldtravel.CountriesRecycler;

import java.sql.Blob;
import java.sql.Date;

/**
 * Created by Malo on 14/03/2018.
 */

public class Country {

    private long id;
    private String name;
    private String capitalCity;
    private String continent;
    private byte[] flag;
    private String date;

    public Country(String name, String capitalCity, String continent, byte[] flag, String date) {
        this.name = name;
        this.capitalCity = capitalCity;
        this.continent = continent;
        this.flag = flag;
        this.date = date;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public String getContinent() {
        return continent;
    }

    public byte[] getFlag() {
        return flag;
    }

    public String getDate() {
        return date;
    }

}
