package com.test.app.LoadMaps.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.airbnb.lottie.LottieAnimationView;
import com.test.app.LoadMaps.data.db.UserData;
import com.test.app.LoadMaps.Utils.Utils;
import com.test.app.LoadMaps.viewmodel.CommonViewModel;
import com.test.app.LoadMaps.viewmodel.CommonViewModelImplementor;
import com.test.app.R;

public class RegisterFragment extends Fragment {
    private View mRegisterView;
    private EditText etUserName,etPassword,et_mobile_number,et_con_password,etEmail;
    private Boolean IsPwdShow = false,IsPwdShowCon = false;
    private ImageView iv_pwd_eye,iv_pwd_eye_con;
    private Button btnRegister;
    private TextView tv_login;
    private LottieAnimationView progress_bar;
    private Context context;
    private CommonViewModel viewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public RegisterFragment(Context context) {
        this.context = context;
    }

    // Override function when the view is being created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // Inflates the custom fragment layout
        mRegisterView = inflater.inflate(R.layout.fragment_register, container, false);

        Bundle bundle = getArguments();
        String message = bundle.getString("mText");

        viewModel = ViewModelProviders.of(this).get(CommonViewModelImplementor.class);
        getLifecycle().addObserver(viewModel);

        etUserName = mRegisterView.findViewById(R.id.et_username);
        etEmail = mRegisterView.findViewById(R.id.et_email);
        et_mobile_number = mRegisterView.findViewById(R.id.et_mobile_number);
        etPassword = mRegisterView.findViewById(R.id.et_password);
        et_con_password = mRegisterView.findViewById(R.id.et_con_passwrod);
        tv_login = mRegisterView.findViewById(R.id.tv_login);
        btnRegister = mRegisterView.findViewById(R.id.btn_register);
        progress_bar = mRegisterView.findViewById(R.id.progress_bar);

        iv_pwd_eye = mRegisterView.findViewById(R.id.iv_password_eye);

        iv_pwd_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsPwdShow){
                    hideeye();
                } else {
                    showeye();
                }
            }
        });

        if(IsPwdShow){
            iv_pwd_eye.setImageResource(R.drawable.ic_eye);
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            iv_pwd_eye.setImageResource(R.drawable.ic_eye_off);
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }


        iv_pwd_eye_con = mRegisterView.findViewById(R.id.iv_password_eye1);

        iv_pwd_eye_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsPwdShowCon){
                    hideconeye();
                } else {
                    showconeye();
                }
            }
        });

        if(IsPwdShowCon){
            iv_pwd_eye_con.setImageResource(R.drawable.ic_eye);
            et_con_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            iv_pwd_eye_con.setImageResource(R.drawable.ic_eye_off);
            et_con_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegistration(etUserName.getText().toString().trim(),etPassword.getText().toString().trim(),et_con_password.getText().toString().trim(), et_mobile_number.getText().toString().trim(),etEmail.getText().toString().trim());
            }
        });

        tv_login=mRegisterView.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLoginPage();
            }
        });

        viewModel.getRegistrationSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegisterSuccess) {
                try {
                    if(!isRegisterSuccess){
                        hideProgress();
                        navigateToLoginPage();
                    } else {
                        hideProgress();
                        showError("UserName Already Exists");
                    }
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errMsg) {
                showAlertDialogBox("",errMsg);
            }
        });

        return mRegisterView;
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
    public void onRegistration(String username, String password, String con_password , String mobilenum, String emailId) {
        if(username.length()>0){
            if(mobilenum.length()>0){
                if(emailId.length()>0){
                    if(Utils.isValidEmail(emailId)){
                        if(password.length()>0){
                            if(con_password.length()>0){
                                if(password.equals(con_password)){
                                    showProgress();

                                    UserData registerData = new UserData();
                                    registerData.setUserName(username);
                                    registerData.setMobileNumber(mobilenum);
                                    registerData.setEmailId(emailId);
                                    registerData.setPassword(password);
                                    registerData.setCurrentUser(false);

                                    viewModel.RegisterAccount(registerData);
                                } else {
                                    showError("Password and Confirm Password Must be Same");
                                }
                            } else {
                                showError("Please enter Confirm Password");
                            }
                        } else {
                            showError("Please enter Password");
                        }
                    } else {
                        showError("Please enter valid eMail Id");
                    }
                } else {
                    showError("Please enter eMail Id");
                }
            } else {
                showError("Please enter MobileNumber");
            }
        } else {
            showError("Please enter user name");
        }
    }
    public void navigateToLoginPage() {
        FragmentManager mFragmentManager = getParentFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        LoginFragment mFragment = new LoginFragment(getContext());
        Bundle mBundle = new Bundle();
        mBundle.putString("mText", "TestData");
        mFragment.setArguments(mBundle);
        mFragmentTransaction.replace(R.id.layout_container, mFragment).addToBackStack("LoginFragment");
        mFragmentTransaction.commitAllowingStateLoss();
    }

    public void showAlertDialogBox(String title, String msg){

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
    public void showconeye() {
        iv_pwd_eye_con.setImageResource(R.drawable.ic_eye);
        et_con_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        et_con_password.setSelection(et_con_password.length());
        IsPwdShowCon = !IsPwdShowCon;
    }
    public void hideconeye() {
        iv_pwd_eye_con.setImageResource(R.drawable.ic_eye_off);
        et_con_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        et_con_password.setSelection(et_con_password.length());
        IsPwdShowCon = !IsPwdShowCon;
    }

}