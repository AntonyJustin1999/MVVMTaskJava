package com.test.app.LoadMaps.data.dataSets;

import java.util.ArrayList;

public class NetworkResponseData {
    public ArrayList<CountryDetailsApi> countriesDetails;
    public ArrayList<CountriesApi> countryList;

    public ArrayList<CountryDetailsApi> getCountriesDetails() {
        return countriesDetails;
    }

    public void setCountriesDetails(ArrayList<CountryDetailsApi> countriesDetails) {
        this.countriesDetails = countriesDetails;
    }

    public ArrayList<CountriesApi> getCountryList() {
        return countryList;
    }

    public void setCountryList(ArrayList<CountriesApi> countryList) {
        this.countryList = countryList;
    }
}
