package com.test.app.LoadMaps.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.test.app.LoadMaps.CountryDetailsPage.CountryDetailsActivity;
import com.test.app.LoadMaps.CountryListScreenContract;
import com.test.app.LoadMaps.presenter.CountryListPresenterImpl;
import com.test.app.LoadMaps.data.dataSets.CountriesApi;
import com.test.app.LoadMaps.view.Adapter.CountiresListAdapter;
import com.test.app.LoadMaps.viewmodel.CommonViewModel;
import com.test.app.LoadMaps.viewmodel.CommonViewModelImplementor;
import com.test.app.R;

import java.util.ArrayList;

public class CountryListFragment extends Fragment {
    private View mCountryListView;
    public Context context;
    public Activity activity;
    private LottieAnimationView animationLoader;
    private RecyclerView rv_country_list;
    private CountiresListAdapter mRestaurantListAdapter;

    private CommonViewModel viewModel;

    public CountryListFragment() {
        // Required empty public constructor
    }

    public CountryListFragment(Context context) {
        this.context = context;
    }

    // Override function when the view is being created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // Inflates the custom fragment layout
        mCountryListView = inflater.inflate(R.layout.fragment_country_list, container, false);

        Bundle bundle = getArguments();
        String message = bundle.getString("mText");

        viewModel = ViewModelProviders.of(this).get(CommonViewModelImplementor.class);
        getLifecycle().addObserver(viewModel);

        animationLoader = mCountryListView.findViewById(R.id.progress_bar);
        rv_country_list = mCountryListView.findViewById(R.id.rv_restaurant_list);

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlertDialogBox("",errMsg);
            }
        });
        viewModel.getCountryList().observe(getViewLifecycleOwner(), new Observer<ArrayList<CountriesApi>>() {
            @Override
            public void onChanged(ArrayList<CountriesApi> countrylist) {
                onSuccessCountrylistLoaded(countrylist);
            }
        });
        viewModel.getCountrylistgetError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String msg) {
                onFailureCountryList(msg);
            }
        });
        viewModel.showAndHideProgress().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isShow) {
                if(isShow) {
                    showProgress();
                } else{
                    hideProgress();
                }
            }
        });
        viewModel.getNetworkIsAvailable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isNetwork) {
                if(isNetwork){
                    showError("NetworkNotAvailable");
                    onFailureCountryList("Network Not Available");
                }
            }
        });

        if(isNetworkAvailable()){
            viewModel.loadCountryList();
        } else {
            showError("NetworkNotAvailable");
            onFailureCountryList("Network Not Available");
        }

        return mCountryListView;
    }
    public void showProgress() {
        animationLoader.setVisibility(View.VISIBLE);
    }
    public void hideProgress() {
        animationLoader.setVisibility(View.GONE);
    }
    public void onSuccessCountrylistLoaded(ArrayList<CountriesApi> countryList) {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rv_country_list.setLayoutManager(linearLayoutManager);
        mRestaurantListAdapter = new CountiresListAdapter(countryList,getContext());
        rv_country_list.setAdapter(mRestaurantListAdapter);
    }
    public void onFailureCountryList(String message) {
        ArrayList<CountriesApi> countrieslist = new ArrayList<>();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rv_country_list.setLayoutManager(linearLayoutManager);
        mRestaurantListAdapter = new CountiresListAdapter(countrieslist,getContext());
        rv_country_list.setAdapter(mRestaurantListAdapter);
    }
    public void showError(String ErrorMsg) {
        showAlertDialogBox("",ErrorMsg);
    }
    public void showCountryDetailsPage(String commonName) {
        Intent intent = new Intent(context, CountryDetailsActivity.class);
        intent.putExtra("CountryName", commonName);
        context.startActivity(intent);
    }

    private void showAlertDialogBox(String title,String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
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
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}