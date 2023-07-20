package com.test.app.LoadMaps.data.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmDBAdapter {
    private Context context;
    private Realm realm;

    public static RealmDBAdapter realmDBAdapterInstance;

    private RealmDBAdapter(Context context){
        this.context = context;
        realm = Realm.getDefaultInstance();
    }

    public static RealmDBAdapter getInstance(Context context){
        if(realmDBAdapterInstance ==null){
            realmDBAdapterInstance = new RealmDBAdapter(context);
        }
        return realmDBAdapterInstance;
    }

    public Boolean IsloginAccountExists() throws Exception{
        try{
            Boolean isExists = false;
            RealmResults<UserData> dataModals = realm.where(UserData.class).equalTo("isCurrentUser",true).findAll();

            if(dataModals.size()>0){
                isExists = true;
            }
            return isExists;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Boolean RegisterData(UserData registerData) throws Exception{
        try{
            if(!findUserNameExists(registerData.getUserName())){
                UserData userData = new UserData();
                Number id = realm.where(UserData.class).max("id");
                long nextId;

                if(id == null){
                    nextId = Long.valueOf(1);
                } else {
                    nextId = id.intValue()+1;
                }

                userData.setId(nextId);
                userData.setUserName(registerData.getUserName());
                userData.setMobileNumber(registerData.getMobileNumber());
                userData.setEmailId(registerData.getEmailId());
                userData.setPassword(registerData.getPassword());
                userData.setCurrentUser(false);

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        // inside on execute method we are calling a method
                        // to copy to real m database from our modal class.
                        realm.copyToRealm(userData);
                    }
                });
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Boolean findUserNameExists(String UserName) throws Exception{
        try{
            Boolean isExists = false;
            RealmResults<UserData> dataModals = realm.where(UserData.class).equalTo("userName", UserName).findAll();

            if(dataModals.size()>0){
                isExists = true;
            }
            return isExists;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Boolean IsloginCredentialExists(String UserName,String Password)throws Exception{
        try {
            Boolean isExists = false;
            RealmResults<UserData> dataModals = realm.where(UserData.class).equalTo("userName",UserName).findAll();

            for(int i=0;i<dataModals.size();i++){
                if(Password.equals(dataModals.get(i).getPassword())){
                    isExists = true;
                    loginUser(UserName);
                }
            }
            return isExists;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public UserData readlogindata(String userName){
        UserData loginDetails = realm.where(UserData.class).equalTo("userName", userName).findFirst();
        return loginDetails;
    }

    public void loginUser(String UserName) throws Exception{
        try{
            UserData loginDetails = readlogindata(UserName);
            UserData modal = realm.where(UserData.class).equalTo("userName", UserName).findFirst();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // inside on execute method we are calling a method
                    // to copy to real m database from our modal class.

                    modal.setUserName(loginDetails.getUserName());
                    modal.setPassword(loginDetails.getPassword());
                    modal.setEmailId(loginDetails.getEmailId());
                    modal.setMobileNumber(loginDetails.getMobileNumber());
                    modal.setCurrentUser(true);

                    realm.copyToRealmOrUpdate(modal);
                }
            });
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private UserData getCurrentUserData(){
        UserData loginDetails = realm.where(UserData.class).equalTo("isCurrentUser", true).findFirst();
        return loginDetails;
    }
    public boolean LogOut() throws Exception {
        try{

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    UserData userDetails = getCurrentUserData();
                    UserData modal = realm.where(UserData.class).equalTo("userName", userDetails.getUserName()).findFirst();
                    modal.setUserName(userDetails.getUserName());
                    modal.setPassword(userDetails.getPassword());
                    modal.setEmailId(userDetails.getEmailId());
                    modal.setMobileNumber(userDetails.getMobileNumber());
                    modal.setCurrentUser(false);

                    // inside on execute method we are calling a method to copy
                    // and update to real m database from our modal class.

                    realm.copyToRealmOrUpdate(modal);
                }
            });


            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
