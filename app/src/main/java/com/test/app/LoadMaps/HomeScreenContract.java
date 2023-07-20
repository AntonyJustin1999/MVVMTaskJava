package com.test.app.LoadMaps;

public interface HomeScreenContract {
    interface View extends BaseView<Presenter> {
        void showProgress();
        void hideProgress();
        void showError(String ErrorMsg);
        void navigateToCountryListPage();
        void navigateToReloadPage();
        void navigateToLoginPage();
    }

    interface Presenter extends BasePresenter{
        void redirectCountryListPage();
        void reloadHomePage();
        void logOut();
    }
}
