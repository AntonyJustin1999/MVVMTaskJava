package com.test.app.LoadMaps.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;
import com.test.app.LoadMaps.viewmodel.CommonViewModel;
import com.test.app.LoadMaps.viewmodel.CommonViewModelImplementor;
import com.test.app.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    NavigationView navigationView;
    View headerView;
    private CommonViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        context = this;

        viewModel = ViewModelProviders.of(this).get(CommonViewModelImplementor.class);
        getLifecycle().addObserver(viewModel);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);

        navigateToCountryListPage();

        viewModel.getOnLogOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLogOutProcessSuccess) {
                if(isLogOutProcessSuccess){
                    try {
                        navigateToLoginPage();
                    } catch (Exception e) {
                        showAlertDialogBox("",e.getMessage());
                    }
                }
            }
        });

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlertDialogBox("",errMsg);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            navigateToReloadPage();
        } else if (id == R.id.nav_logout) {
            viewModel.OnLogOut();
        }
        return false;
    }
    public void navigateToCountryListPage() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        CountryListFragment mFragment = new CountryListFragment(this);
        Bundle mBundle = new Bundle();
        mBundle.putString("mText", "TestData");
        mFragment.setArguments(mBundle);
        mFragmentTransaction.replace(R.id.layout_home_container, mFragment).addToBackStack("CountryListFragment");
        mFragmentTransaction.commitAllowingStateLoss();
    }
    public void navigateToReloadPage() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void navigateToLoginPage() {
        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivity(intent);
        finish();
    }

    private void showAlertDialogBox(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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

}
