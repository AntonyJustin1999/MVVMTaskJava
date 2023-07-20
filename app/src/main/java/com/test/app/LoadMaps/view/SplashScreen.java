package com.test.app.LoadMaps.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.test.app.LoadMaps.viewmodel.CommonViewModel;
import com.test.app.LoadMaps.viewmodel.CommonViewModelImplementor;
import com.test.app.R;

public class SplashScreen extends AppCompatActivity {
    private CommonViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        viewModel = ViewModelProviders.of(this).get(CommonViewModelImplementor.class);
        getLifecycle().addObserver(viewModel);

        // This method is used so that your splash activity can cover the entire screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.getMutableIsAccountActive().observe(SplashScreen.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isAccountActive) {
                        if(isAccountActive){
                            redirectToHome();
                        } else {
                            redirectToAccount();
                        }
                    }
                });
            }
        },2000);

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlertDialogBox("",errMsg);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void redirectToAccount() {
        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivity(intent);
        finish();
    }

    public void redirectToHome() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void showAlertDialogBox(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
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
