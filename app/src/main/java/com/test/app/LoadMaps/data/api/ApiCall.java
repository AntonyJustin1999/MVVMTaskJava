package com.test.app.LoadMaps.data.api;

import com.test.app.LoadMaps.data.dataSets.CountriesApi;
import com.test.app.LoadMaps.data.dataSets.CountryDetailsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCall {

    @GET("v3.1/independent?")
    public Call<ArrayList<CountriesApi>> getAllCountrieslist(@Query("status") boolean status, @Query("fields") String fields);

    @GET("v3.1/name/{countryName}")
    public Call<ArrayList<CountryDetailsApi>> getCountryInfo(@Path("countryName")String countryName);

}
