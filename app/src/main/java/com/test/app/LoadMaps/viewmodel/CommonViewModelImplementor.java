package com.test.app.LoadMaps.viewmodel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.app.LoadMaps.data.api.RestManager;
import com.test.app.LoadMaps.data.dataSets.CountriesApi;
import com.test.app.LoadMaps.data.db.UserData;
import com.test.app.LoadMaps.repository.UserRepositoryImpl;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonViewModelImplementor extends ViewModel implements CommonViewModel {

    private UserRepositoryImpl userRepository;
    private MutableLiveData<Boolean> mutableIsAccountActive;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Boolean> onLogin;
    private MutableLiveData<Boolean> onRegister;
    private MutableLiveData<Boolean> onLogOut;
    private MutableLiveData<ArrayList<CountriesApi>> mutableCountryList;
    private MutableLiveData<Boolean> onNetwork;
    private MutableLiveData<String> countryListErrorMessage;
    private MutableLiveData<Boolean> isProgressShow;
    private RestManager restManager;
    private Context context;

    public CommonViewModelImplementor() {
        userRepository = (UserRepositoryImpl) UserRepositoryImpl.getInstance();
        this.restManager = new RestManager();
        mutableIsAccountActive = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        onLogin = new MutableLiveData<>();
        onRegister = new MutableLiveData<>();
        onLogOut = new MutableLiveData<>();
        mutableCountryList = new MutableLiveData<>();
        onNetwork = new MutableLiveData<>();
        countryListErrorMessage = new MutableLiveData<>();
        isProgressShow = new MutableLiveData<>();
        this.context = context;
    }
    public LiveData<Boolean> getMutableIsAccountActive() {
        try{
            mutableIsAccountActive.setValue(userRepository.getIsAccoutActive().getValue());
            return mutableIsAccountActive;
        }catch (Exception e){
            errorMessage.setValue(e.getMessage());
            return new MutableLiveData<>();
        }
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    @Override
    public void LoginAuthentication(String username, String password) {
        try{
            onLogin.setValue(userRepository.IsloginAccountExists(username, password).getValue());
        }catch (Exception e){
            errorMessage.setValue(e.getMessage());
        }
    }

    public LiveData<Boolean> getLoginValidation() {
        return onLogin;
    }

    @Override
    public LiveData<Boolean> getRegistrationSuccess() {
        return onRegister;
    }

    public void RegisterAccount(UserData registerData) {
        try {
            if(!userRepository.FindUserNameExists(registerData.userName).getValue()){
                onRegister.setValue(false);
                userRepository.UserRegistration(registerData);
            } else {
                onRegister.setValue(true);
            }
        } catch (Exception e) {
            errorMessage.setValue(e.getMessage());
        }
    }

    @Override
    public LiveData<Boolean> getOnLogOut() {
        return onLogOut;
    }

    @Override
    public void loadCountryList() {
        Call<ArrayList<CountriesApi>> Call = null;

        //if(isNetworkAvailable()){
            try {
                Call = restManager.getAPI().getAllCountrieslist(true,"" + "name,flags");
                if(Call!=null){
                    isProgressShow.setValue(true);
                    Call.enqueue(new Callback<ArrayList<CountriesApi>>() {
                        @Override
                        public void onResponse(@NonNull Call<ArrayList<CountriesApi>> call, @NonNull Response<ArrayList<CountriesApi>> response) {
                            try {
                                isProgressShow.setValue(false);
                                ArrayList<CountriesApi> data = response.body();
                                if (data != null) {
                                    mutableCountryList.setValue(response.body());
                                } else{
                                    countryListErrorMessage.setValue("Something Wrong Try Again!!");
                                }
                            } catch (Exception e) {
                                isProgressShow.setValue(false);
                                countryListErrorMessage.setValue("Something Wrong Try Again!!");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ArrayList<CountriesApi>> call, @NonNull Throwable t) {
                            isProgressShow.setValue(false);
                            countryListErrorMessage.setValue("Something Wrong Try Again!!");
                        }
                    });
                }
            } catch (Throwable e) {
                isProgressShow.setValue(false);
                countryListErrorMessage.setValue("Something Wrong Try Again!!");
            }
//        } else{
//            countryListErrorMessage.setValue("No network Found!!");
//            onNetwork.setValue(false);
//        }
    }

    @Override
    public LiveData<ArrayList<CountriesApi>> getCountryList() {
        return mutableCountryList;
    }

    @Override
    public LiveData<Boolean> getNetworkIsAvailable() {
        return onNetwork;
    }

    @Override
    public LiveData<String> getCountrylistgetError() {
        return countryListErrorMessage;
    }

    @Override
    public LiveData<Boolean> showAndHideProgress() {
        return isProgressShow;
    }

    @Override
    public void OnLogOut() {
        try {
            if(userRepository.OnLogOut().getValue()){
                onLogOut.setValue(true);
            } else {
                onRegister.setValue(false);
            }
        } catch (Exception e) {
            errorMessage.setValue(e.getMessage());
        }
    }




//    @Override
//    public void LoggedInUser(String userName) {
//        try{
//            userRepository.loggedInUser(userName);
//        }catch (Exception e){
//            errorMessage.setValue(e.getMessage());
//        }
//    }


}
