package com.fmrnz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPasswordActivity extends BaseActivity {

    EditText passwordText,confirmPasswordText;
    Button reset;
    String email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        passwordText = (EditText)findViewById(R.id.passwordText);
        confirmPasswordText = (EditText)findViewById(R.id.confirmPasswordText);
        reset = (Button)findViewById(R.id.reset);
        email = getIntent().getStringExtra("Email");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnectivity();
            }
        });
    }

    private void checkConnectivity() {

//        Intent intent = new Intent();
//        intent.putExtra("loggedIn", true);
//        setResult(RESULT_OK, intent);
//        finish();

        if (ConnectionDetector.checkConnection(this)) {
            if (!validate()) {
                passwordText.setEnabled(true);
                confirmPasswordText.setEnabled(true);
                reset.setEnabled(true);
                //  Utils.hideProgressBar(progressIndicatorView, loginBtn);

            } else {
                passwordText.setEnabled(false);
                confirmPasswordText.setEnabled(false);
                callServiceRequest();
            }
        } else {
            passwordText.setEnabled(true);
            confirmPasswordText.setEnabled(true);
            reset.setEnabled(true);
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
            //   Utils.hideProgressBar(progressIndicatorView, loginBtn);
        }


    }

    private void callServiceRequest() {
        if(passwordText.getText().toString().length() < 8){
            Toast.makeText(this,"Please enter more than 8 character for password",Toast.LENGTH_SHORT).show();
            passwordText.setEnabled(true);
            confirmPasswordText.setEnabled(true);
            return;
        }
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.RESET_PASSWORD);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", password);
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }


    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        // Utils.hideProgressBar(progressIndicatorView, loginBtn);

        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.RESET_PASSWORD:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    suuccessSweetDialgofForSuccess("Sussess", networkResponse.responseMessage);
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivithy.class);
                    intent.putExtra("CallingFrom","ResetPassword");
                    startActivity(intent);
                    finish();


                }
                else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.ERROR) {


                }
                else{
                    failureSweetDialg("Failure", networkResponse.responseMessage);

                }
                break;
        }
    }

    public boolean validate() {
        boolean valid = true;
        password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();
        if(!password.equalsIgnoreCase(confirmPassword)){
            Toast.makeText(ResetPasswordActivity.this,"Password and Confirm Password should be same",Toast.LENGTH_SHORT).show();
            valid = false;
        }


        if (passwordText.getText().toString().trim().equals("")) {
            passwordText.setError("Required field");
            valid = false;
        }

        if (confirmPasswordText.getText().toString().trim().equals("")) {
            confirmPasswordText.setError("Required field");
            valid = false;
        }

        return valid;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidMobileNo(String mobileNo) {
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher matcher = pattern.matcher(mobileNo);
        return matcher.matches();
    }

}
