package com.test.app.LoadMaps.CountryDetailsPage;

import com.test.app.LoadMaps.BasePresenter;
import com.test.app.LoadMaps.BaseView;
import com.test.app.LoadMaps.data.dataSets.CountryDetailsApi;

import java.util.ArrayList;

public interface CountryDetailsContract {
    interface view extends BaseView {
       void showProgress();
       void hideProgress();
       void onSuccessCountrydetailsLoaded(ArrayList<CountryDetailsApi> countriesList);
       void onFailureCountryDetails(String message);
       void showAlertDialog(String title,String msg);
       void showCountryListPage();
    }
    interface presenter extends BasePresenter {
        void loadCountryDetails(String name);
        void redirectCountryList();
    }
}
