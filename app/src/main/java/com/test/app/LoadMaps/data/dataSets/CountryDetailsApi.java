package com.test.app.LoadMaps.data.dataSets;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;

public class CountryDetailsApi {
    @SerializedName("flags")
    Flags flags = null;

    @SerializedName("coatOfArms")
    CoatOfArms coatOfArms = null;

    @SerializedName("name")
    Name name = null;

    @SerializedName("startOfWeek")
    String startOfWeek = null;

    @SerializedName("currencies")
    JSONObject Currencies = null;

    @SerializedName("capital")
    ArrayList<String> capital = null;

    @SerializedName("languages")
    JSONObject Languages = null;

    @SerializedName("latlng")
    ArrayList<Float> LatLng = null;

    @SerializedName("borders")
    ArrayList<String> Borders = null;

    @SerializedName("area")
    Double area = null;

    @SerializedName("population")
    Long population = null;

    @SerializedName("continents")
    ArrayList<String> Continents = null;

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public String getStartOfWeek() {
        return startOfWeek;
    }

    public void setStartOfWeek(String startOfWeek) {
        this.startOfWeek = startOfWeek;
    }

    public JSONObject getCurrencies() {
        return Currencies;
    }

    public void setCurrencies(JSONObject currencies) {
        Currencies = currencies;
    }

    public ArrayList<String> getCapital() {
        return capital;
    }

    public void setCapital(ArrayList<String> capital) {
        this.capital = capital;
    }

    public JSONObject getLanguages() {
        return Languages;
    }

    public void setLanguages(JSONObject languages) {
        Languages = languages;
    }

    public ArrayList<Float> getLatLng() {
        return LatLng;
    }

    public void setLatLng(ArrayList<Float> latLng) {
        LatLng = latLng;
    }

    public ArrayList<String> getBorders() {
        return Borders;
    }

    public void setBorders(ArrayList<String> borders) {
        Borders = borders;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public ArrayList<String> getContinents() {
        return Continents;
    }

    public void setContinents(ArrayList<String> continents) {
        Continents = continents;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public CoatOfArms getCoatOfArms() {
        return coatOfArms;
    }

    public void setCoatOfArms(CoatOfArms coatOfArms) {
        this.coatOfArms = coatOfArms;
    }
}




