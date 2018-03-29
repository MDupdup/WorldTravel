package com.ynov.malo.worldtravel.Database;

import java.sql.Blob;
import java.sql.Date;

/**
 * Created by Malo on 14/03/2018.
 */

public class Country {

    private int id;
    private String name;
    private String capitalCity;
    private String continent;
    private String date;
    private String countryCode;

    // Trois constructeurs, prenant des parametres en plus ou non, pour les differentes creations d'objets dans l'application
    public Country(String name, String capitalCity, String continent, String countryCode) {
        this.name = name;
        this.capitalCity = capitalCity;
        this.continent = continent;
        this.countryCode = countryCode;
    }

    public Country(String name, String capitalCity, String continent, String countryCode, String date) {
        this.name = name;
        this.capitalCity = capitalCity;
        this.continent = continent;
        this.countryCode = countryCode;
        this.date = date;
    }

    public Country(int id, String name, String capitalCity, String continent, String countryCode, String date) {
        this.id = id;
        this.name = name;
        this.capitalCity = capitalCity;
        this.continent = continent;
        this.countryCode = countryCode;
        this.date = date;
    }


    // Getters & Setters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
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

    public String getDate() {
        return date;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
