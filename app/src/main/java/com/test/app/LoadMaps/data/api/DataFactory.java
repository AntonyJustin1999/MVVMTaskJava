package com.test.app.LoadMaps.data.api;

import com.test.app.LoadMaps.data.dataSets.CountriesApi;

import java.util.ArrayList;

public class DataFactory {
    public DATA_TYPE data_type;
    public ArrayList<CountriesApi> countriesList;

    public DATA_TYPE getData_type() {
        return data_type;
    }

    public ArrayList<CountriesApi> getCountriesList() {
        return countriesList;
    }

    public void setData_type(DATA_TYPE data_type) {
        this.data_type = data_type;
    }

    public void setCountriesList(ArrayList<CountriesApi> countriesList) {
        this.countriesList = countriesList;
    }

    public enum DATA_TYPE{
        COUNTRY_LIST_TYPE
    }
    public DataFactory getData(){
        return this;
    }
}
