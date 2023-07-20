package com.test.app.LoadMaps.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;

import com.test.app.LoadMaps.CountryListScreenContract;
import com.test.app.LoadMaps.data.dataSets.CountriesApi;
import com.test.app.LoadMaps.data.api.RestManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryListPresenterImpl implements CountryListScreenContract.Presenter {
    private CountryListScreenContract.View mCountryListView;
    //private MVPModelImplementor model;
    private Context context;

    public CountryListPresenterImpl(CountryListScreenContract.View mCountryListView,Context context){
        this.mCountryListView = mCountryListView;
        this.context = context;
        //this.model = MyApplication.getModel();

    }

    @Override
    public void loadCountryList() {

    }

    @Override
    public void redirectCountryDetails(String commonName) {
        mCountryListView.showCountryDetailsPage(commonName);
    }

    public void LoadCountrylist() {



    }



}
