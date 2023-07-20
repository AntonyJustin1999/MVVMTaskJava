package com.test.app.LoadMaps.CountryDetailsPage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.test.app.LoadMaps.data.dataSets.CountryDetailsApi;
import com.test.app.R;

import java.util.ArrayList;

public class CountryDetailsActivity extends AppCompatActivity implements CountryDetailsContract.view {
    private LottieAnimationView progress_bar;
    private Context context;

    private TextView tv_common_name,tv_official_name,tv_currency,tv_capital, tv_languages,tv_start_of_week,tv_latlng,tv_population;
    private ImageView iv_flag;
    private LinearLayout layout_content_holder;
    private RelativeLayout layout_empty_holder;
    private String countryName = "";
    private CountryDetailsContract.presenter mPresenter;
//    private INetworkApiCallDataSource mNetworkApicall;
//    private IRealmDataSource mRealmDataSource;
//    private AppDataRepository mAppDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_detail_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        Intent intent = getIntent();
        countryName = intent.getStringExtra("CountryName");

        context = this;


//        mRealmDataSource = new RealmDataStorage(context);
//        mNetworkApicall = new RetrofitApiCallDataSource(context);
//        mAppDataRepository = new AppDataRepository(mRealmDataSource,mNetworkApicall);
        //mPresenter = new CountryDetailsPresenter(this,mAppDataRepository,context);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        progress_bar = findViewById(R.id.progress_bar);
        iv_flag = findViewById(R.id.iv_flag);
        tv_common_name = findViewById(R.id.tv_common_name);
        tv_official_name = findViewById(R.id.tv_official_name);
        tv_currency = findViewById(R.id.tv_currency);
        tv_capital = findViewById(R.id.tv_capital);
        tv_languages = findViewById(R.id.tv_languages);
        tv_start_of_week = findViewById(R.id.tv_start_of_week);
        tv_latlng = findViewById(R.id.tv_latlng);
        tv_population = findViewById(R.id.tv_population);
        layout_content_holder = findViewById(R.id.layout_content_holder);
        layout_empty_holder = findViewById(R.id.layout_empty_holder);
        layout_empty_holder.setVisibility(View.GONE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Log.e("Test","CountryDetailsActivity Called()");

        mPresenter.loadCountryDetails(countryName);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            mPresenter.redirectCountryList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.e("Test","onBackPressed - Called()");
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void showProgress() {
        progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessCountrydetailsLoaded(ArrayList<CountryDetailsApi> countrieslist) {

        Log.e("Test","onSuccessCountrydetailsLoaded Called()");

        layout_empty_holder.setVisibility(View.GONE);
        layout_content_holder.setVisibility(View.VISIBLE);
        StringBuilder temp=new StringBuilder();
        Glide.with(context).load(countrieslist.get(0).getCoatOfArms().getPng_url()).placeholder(R.drawable.baseline_image_24).into(iv_flag);

        tv_common_name.setText(countrieslist.get(0).getName().getCommon());
        tv_official_name.setText(countrieslist.get(0).getName().getOfficial());

        temp = new StringBuilder("");
        for(int i=0;i<countrieslist.get(0).getCapital().size();i++){
            temp.append(countrieslist.get(0).getCapital().get(i));
        }
        tv_capital.setText(temp);
        tv_start_of_week.setText(""+countrieslist.get(0).getStartOfWeek());
        tv_population.setText(""+countrieslist.get(0).getPopulation());
        tv_languages.setText(temp);

        Log.e("Test","LatLng() size "+countrieslist.get(0).getLatLng().size());
        if(countrieslist.get(0).getLatLng().size()>0){
            tv_latlng.setText(""+countrieslist.get(0).getLatLng().get(0)+
                    ","+countrieslist.get(0).getLatLng().get(1));
        }
    }

    @Override
    public void onFailureCountryDetails(String message) {
        Log.e("Test","onFailureCountryDetails Called()");
        layout_empty_holder.setVisibility(View.VISIBLE);
        layout_content_holder.setVisibility(View.GONE);
    }

    @Override
    public void showAlertDialog(String title, String msg) {
        ShowAlertDialog(title,msg);
    }

    @Override
    public void showCountryListPage() {
        finish();
    }
    public void ShowAlertDialog(String title, String msg) {
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
}
