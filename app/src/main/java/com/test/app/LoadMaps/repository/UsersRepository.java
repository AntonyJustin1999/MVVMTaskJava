package com.test.app.LoadMaps.repository;

import androidx.lifecycle.LiveData;

import com.test.app.LoadMaps.data.db.UserData;

public interface UsersRepository {
    LiveData<Boolean> getIsAccoutActive() throws Exception;
    LiveData<Boolean> IsloginAccountExists(String username, String password) throws Exception;
    //void loggedInUser(String UserName) throws Exception;
    LiveData<Boolean> FindUserNameExists(String userName) throws Exception;
    void UserRegistration(UserData registerData) throws Exception;
    LiveData<Boolean> OnLogOut() throws Exception;
}
