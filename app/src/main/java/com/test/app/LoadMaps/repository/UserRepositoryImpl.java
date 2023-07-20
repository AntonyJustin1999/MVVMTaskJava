package com.test.app.LoadMaps.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.app.LoadMaps.data.db.UserData;
import com.test.app.LoadMaps.MyApplication;
import com.test.app.LoadMaps.data.db.RealmDBAdapter;

public class UserRepositoryImpl implements UsersRepository{

    private MutableLiveData<Boolean> mutableIsAnyAccountActive;
    private MutableLiveData<Boolean> mutableLoginValidation;
    private MutableLiveData<Boolean> mutableRegisterSuccess;
    private MutableLiveData<Boolean> mutableLogOutProcess;
    private boolean isAccountActive = false;
    private RealmDBAdapter realmDBAdapter;

    private static  UsersRepository instance = null;

    public static UsersRepository getInstance(){
        if(instance == null){
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    private UserRepositoryImpl(){
        this.realmDBAdapter = MyApplication.getRealmDBAdapterInstance();
        this.mutableIsAnyAccountActive = new MutableLiveData<>();
        this.mutableLoginValidation = new MutableLiveData<>();
        this.mutableRegisterSuccess = new MutableLiveData<>();
        this.mutableLogOutProcess = new MutableLiveData<>();
    }

    @Override
    public LiveData<Boolean> getIsAccoutActive() throws Exception {
        this.isAccountActive = realmDBAdapter.IsloginAccountExists();
        mutableIsAnyAccountActive.setValue(this.isAccountActive);
        Log.e("Test"," mutableIsAccountActive reference: "+mutableIsAnyAccountActive);
        return mutableIsAnyAccountActive;
    }

    @Override
    public LiveData<Boolean> IsloginAccountExists(String username, String password) throws Exception {
        mutableLoginValidation.setValue(realmDBAdapter.IsloginCredentialExists(username, password));
        Log.e("Test"," mutableLoginValidation reference: "+mutableLoginValidation);
        return mutableLoginValidation;
    }

    @Override
    public LiveData<Boolean> FindUserNameExists(String userName) throws Exception {
        mutableRegisterSuccess.setValue(realmDBAdapter.findUserNameExists(userName));
        return mutableRegisterSuccess;
    }

    @Override
    public void UserRegistration(UserData registerData) throws Exception {
        realmDBAdapter.RegisterData(registerData);
    }

    @Override
    public LiveData<Boolean> OnLogOut() throws Exception {
        mutableLogOutProcess.setValue(realmDBAdapter.LogOut());
        return mutableLogOutProcess;
    }



//    @Override
//    public void loggedInUser(String UserName) throws Exception {
//        realmDBAdapter.loginUser(UserName);
//    }
}
