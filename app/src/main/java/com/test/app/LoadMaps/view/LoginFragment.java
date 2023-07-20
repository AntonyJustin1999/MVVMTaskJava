package com.test.app.LoadMaps.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.test.app.LoadMaps.viewmodel.CommonViewModel;
import com.test.app.LoadMaps.viewmodel.CommonViewModelImplementor;
import com.test.app.R;

public class LoginFragment extends Fragment {
    private View mLoginView;
    private EditText etUserName,etPassword;
    private Boolean IsPwdShow = false;
    private ImageView iv_pwd_eye;
    private Button btnLogin;
    private TextView tv_register;
    private LottieAnimationView progress_bar;
    private Context context;
    private CommonViewModel viewModel;

//    public LoginFragment() {
//        // Required empty public constructor
//    }
    public LoginFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // Inflates the custom fragment layout
        mLoginView = inflater.inflate(R.layout.fragment_login, container, false);

        Bundle bundle = getArguments();
        if(bundle!=null){
            String message = bundle.getString("mText");
        }

        viewModel = ViewModelProviders.of(this).get(CommonViewModelImplementor.class);
        getLifecycle().addObserver(viewModel);

        etUserName = mLoginView.findViewById(R.id.et_userName);
        etPassword = mLoginView.findViewById(R.id.et_password);
        btnLogin = mLoginView.findViewById(R.id.btn_login);
        progress_bar = mLoginView.findViewById(R.id.progress_bar);

        iv_pwd_eye = mLoginView.findViewById(R.id.iv_password_eye);

        iv_pwd_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsPwdShow){
                    showeye();
                } else {
                    hideeye();
                }
            }
        });

        if(IsPwdShow){
            iv_pwd_eye.setImageResource(R.drawable.ic_eye);
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            iv_pwd_eye.setImageResource(R.drawable.ic_eye_off);
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoggedIn(etUserName.getText().toString().trim(),etPassword.getText().toString().trim());
            }
        });

        tv_register = mLoginView.findViewById(R.id.tv_register);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateRegisterPage();
            }
        });

        viewModel.getLoginValidation().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLogin) {
                if(isLogin){
                    hideProgress();
                    //viewModel.LoggedInUser(etUserName.getText().toString().trim());
                    navigateHomeActivity();
                } else {
                    showError("Invalid UserName and Password");
                    hideProgress();
                }
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlertDialogBox("",errMsg);
            }
        });

        return mLoginView;
    }

    public void onLoggedIn(String username, String password) {
        if(username.length()>0){
            if(password.length()>0){
                showProgress();
                try {
                    viewModel.LoginAuthentication(username,password);
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            } else {
                showError("Please enter Password");
            }
        } else {
            showError("Please enter eMail Id");
        }
    }


    public void showProgress() {
        progress_bar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progress_bar.setVisibility(View.GONE);
    }

    public void showError(String ErrorMsg) {
        showAlertDialogBox("",ErrorMsg);
    }
    public void navigateHomeActivity() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
    public void navigateRegisterPage() {
        FragmentManager mFragmentManager = getParentFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        RegisterFragment mFragment = new RegisterFragment(context);
        Bundle mBundle = new Bundle();
        mBundle.putString("mText", "TestData");
        mFragment.setArguments(mBundle);
        mFragmentTransaction.replace(R.id.layout_container, mFragment).addToBackStack("RegisterFragment");
        mFragmentTransaction.commitAllowingStateLoss();
    }
    public void showeye() {
        iv_pwd_eye.setImageResource(R.drawable.ic_eye);
        etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        etPassword.setSelection(etPassword.length());
        IsPwdShow = !IsPwdShow;
    }
    public void hideeye() {
        iv_pwd_eye.setImageResource(R.drawable.ic_eye_off);
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etPassword.setSelection(etPassword.length());
        IsPwdShow = !IsPwdShow;
    }
    private void showAlertDialogBox(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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