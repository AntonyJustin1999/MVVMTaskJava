package com.test.app.LoadMaps;

import com.test.app.LoadMaps.data.dataSets.CountriesApi;

import java.util.ArrayList;

public interface CountryListScreenContract {
    interface View extends BaseView<Presenter> {
        void showProgress();
        void hideProgress();
        void onSuccessCountrylistLoaded(ArrayList<CountriesApi> countriesList);
        void onFailureCountryList(String message);
        void showError(String ErrorMsg);
        void showCountryDetailsPage(String commonName);
    }

    interface Presenter extends BasePresenter{
        void loadCountryList();
        void redirectCountryDetails(String commonName);
    }
}
