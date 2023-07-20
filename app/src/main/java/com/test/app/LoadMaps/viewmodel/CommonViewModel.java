package com.test.app.LoadMaps.viewmodel;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;

import com.test.app.LoadMaps.data.dataSets.CountriesApi;
import com.test.app.LoadMaps.data.db.UserData;

import java.util.ArrayList;

public interface CommonViewModel extends LifecycleObserver {
    LiveData<Boolean> getMutableIsAccountActive();
    LiveData<String> getErrorMessage();
    void LoginAuthentication(String username, String password);
    LiveData<Boolean> getLoginValidation();
    //void LoggedInUser(String UserName);
    void RegisterAccount(UserData registerData);
    LiveData<Boolean> getRegistrationSuccess();
    void OnLogOut();
    LiveData<Boolean> getOnLogOut();

    void loadCountryList();
    LiveData<ArrayList<CountriesApi>> getCountryList();
    LiveData<Boolean> getNetworkIsAvailable();
    LiveData<String> getCountrylistgetError();
    LiveData<Boolean> showAndHideProgress();

}
