package com.fmrnz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.fmrNetworking.AtechnosServerService;
import com.fmrnz.fmrNetworking.RetrofitRestController;
import com.fmrnz.pojo.RespomSIgnUpforOtpCheck;
import com.fmrnz.pojo.updatepassword.UpdatePasswordResponse;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;

public class ForgetActivity extends BaseActivity {

    EditText email1;
    Button submit;
    String email;
    AlertDialog.Builder alertDialog;
    AVLoadingIndicatorView progressIndicatorView;
    String emailID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
         String emaijjj = getIntent().getStringExtra("Email");
        progressIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));
        email1 = (EditText) findViewById(R.id.emailET);
        submit = (Button) findViewById(R.id.submit);

        alertDialog = new AlertDialog.Builder(this);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailID =email1.getText().toString();
                String email_for_update= email1.getText().toString();
                if (!isValidEmailRegex(email_for_update)) {

                    email1.setError("Please provide a valid e-mail address ");
                    email1.requestFocus();

                }
                else{
                    postdatawithUpadtePasswordVerify(email_for_update);
                }


            }
        });
    }
    private boolean isValidEmailRegex(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void checkConnectivity(String email_for_update) {

//        Intent intent = new Intent();
//        intent.putExtra("loggedIn", true);
//        setResult(RESULT_OK, intent);
//        finish();

        if (ConnectionDetector.checkConnection(this)) {
            if (!validate()) {
                email1.setEnabled(true);
                submit.setEnabled(true);
                //  Utils.hideProgressBar(progressIndicatorView, loginBtn);

            } else {
                email1.setEnabled(false);
                email = email1.getText().toString();
                callServiceRequest(email_for_update);
            }
        } else {
            email1.setEnabled(true);
            submit.setEnabled(true);
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
            //   Utils.hideProgressBar(progressIndicatorView, loginBtn);
        }


    }
    private void postdatawithUpadtePasswordVerify(final String email_for_update) {

       Utils.showProgressBar(progressIndicatorView);

        AtechnosServerService Service = RetrofitRestController.getClient().create(AtechnosServerService.class);
        Call<UpdatePasswordResponse> call = Service.postUpdatepasswordVerifyRequest(email_for_update,"1222");
        // TODO: 01/12/17 Below mentioned code is for aSynchronus call
        call.enqueue(new retrofit2.Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {
                Utils.hideProgressBar(progressIndicatorView);
                if(response.body().getResponseCode().equalsIgnoreCase("0")){


                    showAlertdialogforOtp("This e-mail address is not registered!");
                }
                if(response.body().getEmail()!=null){
                    if(response.body().getResponseCode().equalsIgnoreCase("1") &&
                            response.body().getEmail().get(0).getLoginType().equalsIgnoreCase("social")){


                        showAlertdialogforOtp(" This e-mail address is registered from a social account and password is not required for this ");
                    }
                }

                if(response.body().getResponseCode().equalsIgnoreCase("1")){

                    checkConnectivity(email_for_update);
                }


            }

            @Override
            public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                showAlertdialogforOtp(t.getLocalizedMessage());
                Utils.hideProgressBar(progressIndicatorView);

            }
        });
    }
    private void showAlertdialogforOtp(String responseMessage) {

        // Setting Dialog Title
        alertDialog.setTitle("Update password Failed!");

        // Setting Dialog Message
        alertDialog.setMessage(responseMessage);
        alertDialog.setCancelable(false);

        // Setting Icon to Dialog

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();


            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public boolean validate() {
        boolean valid = true;
        email = email1.getText().toString();

        if (TextUtils.isEmpty(email)) {
            valid = false;
        }

        if (email1.getText().toString().trim().equals("")) {
            email1.setError("Required field");
            valid = false;
        } else {
            final String m_no = email1.getText().toString();
            if (!isValidMobileNo(m_no) && !isValidEmail(m_no)) {
                email1.setError("Please provide a valid e-mail address .");
                valid = false;
            }

        }



        return valid;
    }


    private void callServiceRequest(String email_for_update) {
        //   ((NHPApplication) getApplication()).trackScreenView(AppConstant.LOGIN);

        //   Utils.showProgressBar(progressIndicatorView, loginBtn);
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.FORGET_PASSWORD);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", email_for_update);
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }


    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        // Utils.hideProgressBar(progressIndicatorView, loginBtn);
        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.FORGET_PASSWORD:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;
                    showDialogue("Success", responseMessage);
                    Intent intent = new Intent(ForgetActivity.this, OTPActivity3.class);
                    intent.putExtra("Email",email1.getText().toString());
                    startActivity(intent);
                    finish();


                }

                break;
        }
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
