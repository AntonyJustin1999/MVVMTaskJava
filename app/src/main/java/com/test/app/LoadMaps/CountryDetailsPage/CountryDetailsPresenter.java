package com.test.app.LoadMaps.CountryDetailsPage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.test.app.LoadMaps.data.dataSets.NetworkResponseData;

public class CountryDetailsPresenter implements CountryDetailsContract.presenter {
    private CountryDetailsContract.view mCountryDetailsView;
    //private AppDataRepository mAppDataRepository;
    private Context context;

    public CountryDetailsPresenter(CountryDetailsContract.view mCountryDetailsView, Context context){
        this.mCountryDetailsView = mCountryDetailsView;
        this.context = context;
    }

    private boolean isNetworkAvailable(){
        try {
            if(context!=null){
                ConnectivityManager connectivity = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivity != null) {
                    NetworkInfo info = connectivity.getActiveNetworkInfo();
                    if (info != null && info.isConnected()) {
                        return true;
                    }
                }
            }else{
                Log.e("Test","isNetworkAvailable context is null");
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void onSuccess(NetworkResponseData countriesList) {
        Log.e("Test","CountryDetailsPresenter onSuccess Called()");
        mCountryDetailsView.hideProgress();
        mCountryDetailsView.onSuccessCountrydetailsLoaded(countriesList.getCountriesDetails());
    }

    public void onFailure(String msg) {
        Log.e("Test","CountryDetailsPresenter onFailure Called()");
        mCountryDetailsView.hideProgress();
        mCountryDetailsView.onFailureCountryDetails(msg);
    }

    @Override
    public void loadCountryDetails(String name) {
        Log.e("Test","CountryDetailsPresenter loadCountryDetails Called()");
        mCountryDetailsView.showProgress();
        if(isNetworkAvailable()){
            Log.e("Test","CountryDetailsPresenter network true");
            //mAppDataRepository.loadCountrydetails(name,this);
        } else{
            Log.e("Test","CountryDetailsPresenter network false");
            mCountryDetailsView.hideProgress();
            mCountryDetailsView.onFailureCountryDetails("No network Found!!");
        }
    }

    @Override
    public void redirectCountryList() {
        mCountryDetailsView.showCountryListPage();
    }
}
