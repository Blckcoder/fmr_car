package com.fmrnz;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.fmrNetworking.AtechnosServerService;
import com.fmrnz.fmrNetworking.RetrofitRestController;
import com.fmrnz.interfaces.LocationInterface;
import com.fmrnz.model.LicenceModel;
import com.fmrnz.model.UserModel;
import com.fmrnz.pojo.RespomSIgnUpforOtpCheck;
import com.fmrnz.utils.AppConstant;
import com.fmrnz.utils.Config;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fmrnz.utils.WebTermActivity;
import com.fmrnz.utils.WebViewActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.iid.FirebaseInstanceId;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import retrofit2.Call;
import retrofit2.Response;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;


public class LoginActivithy extends LocationBaseActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    String email, password1;
    EditText editText, password;
    Button loginBtn;
    TextView skipNowTV, forgetPass;
    private static final String TAG = MainActivity.class.getSimpleName();
    AVLoadingIndicatorView progressIndicatorView;
    Button button;
    String fcmRegId;
    Bundle bundle;
    ImageView showPassword, hidePassword;
    String loginType;
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private SignInButton btnSignInGoogle;
    CallbackManager callbackManager;
    LoginButton loginButton;
    ImageButton customLoginButtonFacebook, customLoginButtonGPlus, customTwitterButton;
    String registrationId;
    static String socialUserId = "";



    private static String socialLoginName = "";
    private static String socialLoginEmail = "";
    private static String socialImageURL = "";

    private TwitterAuthClient client;
    String callingFrom;
    Dialog dialog;
    Dialog dialog_google;
    Dialog dialog_twittwer;
    CheckBox agreeCheckBox, agreeCheckBox1;
    CheckBox agreeCheckBox_google, agreeCheckBox1_google;
    CheckBox agreeCheckBox_Twitter, agreeCheckBox1_Twitter;
    boolean flag_term = false;
    boolean flag_term_receive = false;
    boolean flag_term_receive_google = false;
    boolean flag_term_google = false;
    boolean flag_term_receive_Twitter = false;
    boolean flag_term_Twitter = false;
    TextView diffText, diffTextGoogle, diffTextTwitter;
    TextView diff_terms_of_service,diff_privacy;
    Button dilaog_term_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();





        registrationId = sessionManager.getRegistrationId();
        if (registrationId == null) {
            registrationId = FirebaseInstanceId.getInstance().getToken();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        client = new TwitterAuthClient();

        callingFrom = getIntent().getStringExtra("CallingFrom");


        progressIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.tw__solid_white));

        editText = (EditText) findViewById(R.id.mobileNumberET);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);
        skipNowTV = (TextView) findViewById(R.id.skipNow);
        showPassword = (ImageView) findViewById(R.id.shoPassword);
        hidePassword = (ImageView) findViewById(R.id.hidePassword);
        forgetPass = (TextView) findViewById(R.id.forgetPassword);

        btnSignInGoogle = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignInGoogle.setScopes(gso.getScopeArray());

        loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions(Arrays.asList(
//                "public_profile", "email"));
        customLoginButtonFacebook = (ImageButton) findViewById(R.id.face);
        customLoginButtonGPlus = (ImageButton) findViewById(R.id.google);
        customTwitterButton = (ImageButton) findViewById(R.id.twitterLogin);

        showPassword.setOnClickListener(this);
        hidePassword.setOnClickListener(this);

        btnSignInGoogle.setOnClickListener(this);
        customLoginButtonFacebook.setOnClickListener(this);
        customLoginButtonGPlus.setOnClickListener(this);
        customTwitterButton.setOnClickListener(this);

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(LoginActivithy.this)
//                .addOnConnectionFailedListener(LoginActivithy.this)
//                .addApi(Plus.API)
//                .addScope(Plus.SCOPE_PLUS_LOGIN)
//                .build();


//        showPassword = (ImageView) findViewById(R.id.showPassword);
//        hidePassword = (ImageView) findViewById(R.id.hidePassword);

        (editText).setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                   // checkConnectivity();
                    return true; // consume.
                }
                return false;
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginType = "normal";
                checkConnectivity();
            }
        });

        TextView signUp = (TextView) findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivithy.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        skipNowTV.setOnClickListener(this);

        ((LocationBaseActivity) this).updateApi(new LocationInterface() {

            @Override
            public void fetchLocation() {

            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginActivithy.this, ForgetActivity.class);
                intent1.putExtra("Email", editText.getText().toString());
                startActivity(intent1);
            }
        });

        // dialog facebook
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_term_and_condition_dialog);
        dialog.setCanceledOnTouchOutside(false);

        agreeCheckBox = (CheckBox) dialog.findViewById(R.id.checkbox);
        agreeCheckBox1 = (CheckBox) dialog.findViewById(R.id.checkbox1);
        diffText = (TextView) dialog.findViewById(R.id.diffText);
        diff_terms_of_service = (TextView) dialog.findViewById(R.id.diff_terms_of_service);
        diff_privacy = (TextView) dialog.findViewById(R.id.diff_privacy);
        dilaog_term_ok = (Button) dialog.findViewById(R.id.dilaog_term_ok);

         diffText.setMovementMethod(LinkMovementMethod.getInstance());
        //dialog google
        dialog_google = new Dialog(context);
        dialog_google.setContentView(R.layout.custom_term_and_condition_dialog);
        agreeCheckBox_google = (CheckBox) dialog_google.findViewById(R.id.checkbox);
        agreeCheckBox1_google = (CheckBox) dialog_google.findViewById(R.id.checkbox1);
        diffTextGoogle = (TextView) dialog_google.findViewById(R.id.diffText);
        diffTextGoogle.setMovementMethod(LinkMovementMethod.getInstance());
        //dialog twittwer
        dialog_twittwer = new Dialog(context);
        dialog_twittwer.setContentView(R.layout.custom_term_and_condition_dialog);
        agreeCheckBox_Twitter = (CheckBox) dialog_twittwer.findViewById(R.id.checkbox);
        agreeCheckBox1_Twitter = (CheckBox) dialog_twittwer.findViewById(R.id.checkbox1);
        diffTextTwitter = (TextView) dialog_twittwer.findViewById(R.id.diffText);
        diffTextTwitter.setMovementMethod(LinkMovementMethod.getInstance());


    }



    private void checkConnectivity() {

//        Intent intent = new Intent();
//        intent.putExtra("loggedIn", true);
//        setResult(RESULT_OK, intent);
//        finish();

        if (ConnectionDetector.checkConnection(this)) {
            if (!validate()) {
                editText.setEnabled(true);
                password.setEnabled(true);
                loginBtn.setEnabled(true);
                Utils.hideProgressBar(progressIndicatorView, loginBtn);


            } else {
                editText.setEnabled(false);
                password.setEnabled(false);
                email = editText.getText().toString();
               callServiceRequest();
            }
        } else {
            editText.setEnabled(true);
            password.setEnabled(true);
            loginBtn.setEnabled(true);
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
            Utils.hideProgressBar(progressIndicatorView, loginBtn);

        }
    }


    public boolean validate() {
        boolean valid = true;
        email = editText.getText().toString();
        password1 = password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            valid = false;
        }

        if (editText.getText().toString().trim().equals("")) {
            editText.setError("Required field");
            valid = false;
        } else {
            final String m_no = editText.getText().toString();
            if (!isValidMobileNo(m_no) && !isValidEmail(m_no)) {
                editText.setError("Please provide a valid e-mail address");
                valid = false;
            }

        }

        if (password.getText().toString().trim().equals("")) {
            password.setError("Required field");
            valid = false;
        }

        return valid;
    }


    private void signUpRequest() {

        Utils.showProgressBar(progressIndicatorView);
        ESAppRequest esLoginRequest1= (ESAppRequest)
                networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.SIGNUP);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", socialLoginName);
        hashMap.put("email", socialLoginEmail);
        hashMap.put("mobile", "");
        hashMap.put("password", "");
        hashMap.put("fcm_id", registrationId);
        hashMap.put("login_type", loginType);
        if (!TextUtils.isEmpty(AppConstant.referalCode)) {
            hashMap.put("refer", AppConstant.referalCode);
        } else {
            hashMap.put("refer", "");
        }
        esLoginRequest1.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest1);

    }

    private void callServiceRequest() {
        Utils.showProgressBar(progressIndicatorView, loginBtn);

        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.LOGIN);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", password1);
        hashMap.put("fcm_id", registrationId);
        hashMap.put("login_type", loginType);

        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }


    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
      //  Utils.hideProgressBar(progressIndicatorView);
        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.LOGIN:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    Utils.hideProgressBar(progressIndicatorView, loginBtn);

                    String responseMessage = networkResponse.responseMessage;
                    UserModel userModel = networkResponse.userModel;
                    String userId = userModel.getId();
                     sessionManager.setLoggedIn(true);
                    sessionManager.setPayStatus(userModel.getPay_status());


                    if(userModel.getLogin_type().equalsIgnoreCase("social")){
                        if (!TextUtils.isEmpty(socialImageURL)) {
                            userModel.setImage(socialImageURL);
                            sessionManager.setImage(socialImageURL);
                        }
                    }
                    else {
                        sessionManager.setImage(userModel.getImage());
                    }

//                    if (TextUtils.isEmpty(userModel.getImage() )) {
//
//                    }

                    ArrayList<LicenceModel> modelArrayList = networkResponse.licenceModelArrayList;
                    if (modelArrayList != null && modelArrayList.size() > 0) {
                        sessionManager.createLicenceData(modelArrayList.get(0));
                    }


                    sessionManager.createLoginSession(userModel.getName(), userModel.getId(), userModel.getEmail(), userModel.getMobile(), userModel.getPassword(), userModel.getToken(), userModel.getLogin_type(), userModel.getAddress_user(), userModel.getGender(), userModel.getAbout(), userModel.getImage(), userModel.getMember(), userModel.getNationality(), userModel.getPay_status());


                    if (!TextUtils.isEmpty(callingFrom)) {
                        if (callingFrom.equalsIgnoreCase("DriverLicence")) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("ResponseCode", networkResponse.responseCode);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

                        } else {
                            Intent intent = new Intent(LoginActivithy.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(LoginActivithy.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }


                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.ERROR) {
                    Utils.hideProgressBar(progressIndicatorView, loginBtn);

                    failureSweetDialg("Error", networkResponse.responseMessage);
                    editText.setEnabled(true);
                    password.setEnabled(true);
                    loginBtn.setEnabled(true);

                } else {
                    failureSweetDialg("Failure", networkResponse.responseMessage);
                    editText.setEnabled(true);
                    password.setEnabled(true);
                    loginBtn.setEnabled(true);

                }
                break;
            case ESNetworkRequest.NetworkEventType.SIGNUP:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    Utils.hideProgressBar(progressIndicatorView, loginBtn);


                    String responseMessage = networkResponse.responseMessage;
                    UserModel userModel = networkResponse.userModel;
//                    String userId = userModel.getId();
                    sessionManager.setLoggedIn(true);
                    if (TextUtils.isEmpty(userModel.getImage())) {
                        if (!TextUtils.isEmpty(socialImageURL)) {
                            userModel.setImage(socialImageURL);
                            sessionManager.setImage(socialImageURL);
                        }
                    } else {
                        sessionManager.setImage(userModel.getImage());
                    }
                    sessionManager.setPayStatus(userModel.getPay_status());
                    ArrayList<LicenceModel> modelArrayList = networkResponse.licenceModelArrayList;
                    if (modelArrayList != null && modelArrayList.size() > 0) {
                        sessionManager.createLicenceData(modelArrayList.get(0));
                    }
//                    sessionManager.setLicenceID(userModel.getDriving_detail_id());
                    sessionManager.setImage(socialImageURL);
                    sessionManager.createLoginSession(userModel.getName(), userModel.getId(), userModel.getEmail(), userModel.getMobile(), userModel.getPassword(), userModel.getToken(), userModel.getLogin_type(), userModel.getAddress_user(), userModel.getGender(), userModel.getAbout(), userModel.getImage(), userModel.getMember(), userModel.getNationality(), userModel.getPay_status());
//                    Utils.userId = userId;
//                    Utils.userModel = userModel;
                    if (!TextUtils.isEmpty(callingFrom)) {
                        if (callingFrom.equalsIgnoreCase("DriverLicence")) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("ResponseCode", networkResponse.responseCode);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

                        } else {
                            Intent intent = new Intent(LoginActivithy.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(LoginActivithy.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }


                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.ERROR) {
                    Utils.hideProgressBar(progressIndicatorView, loginBtn);

                    String responseMessage =
                            networkResponse.responseMessage;
                    //  showDialogue("Error", responseMessage);
                    if (networkResponse.responseMessage.equals("This e-mail address is already in use by another user")) {
                        loginType = "social";
                        email = socialLoginEmail;
                        password1 = "";
                        callServiceRequest();
                    } else {
                        LoginManager.getInstance().logOut();
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(Status status) {

                                    }
                                });
                    }


                } else {
                    String responseMessage =
                            networkResponse.responseMessage;
                    showDialogue("Failure", responseMessage);
                    LoginManager.getInstance().logOut();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {

                                }
                            });

                }
                break;


        }
    }

    private void fetchFMRPoints() {
        if (ConnectionDetector.checkConnection(this)) {
            fetchFMRPointsRequest();
        } else {
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
        }
    }

    private void fetchFMRPointsRequest() {
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.FMR_POINTS);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getUserDetails().get(SessionManager.KEY_ID));
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skipNow:
                Intent i = new Intent(LoginActivithy.this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.shoPassword:
                showPassword.setVisibility(View.GONE);
                hidePassword.setVisibility(View.VISIBLE);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            case R.id.hidePassword:
                showPassword.setVisibility(View.VISIBLE);
                hidePassword.setVisibility(View.GONE);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;

            case R.id.face:

                // custom dialog
                dialog.show();
                diff_terms_of_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(LoginActivithy.this, WebTermActivity.class);
                        startActivity(intent);
                    }
                });
                diff_privacy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(LoginActivithy.this, WebViewActivity.class);
                        startActivity(intent);
                    }
                });
                agreeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                             @Override
                                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                               if(isChecked){
                                                                flag_term = true;
                                                               }
                                                               else{
                                                                   flag_term = false;
                                                               }

                                                               }
                                                         }

                );

                dilaog_term_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag_term ) {
                            dialog.dismiss();
                            agreeCheckBox.setChecked(false);
                            loginType = "social";
                            creaeRegistrationId("Facebook");
                        }
                    }
                });


                break;
            case R.id.google:

                dialog.show();
                diff_terms_of_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(LoginActivithy.this, WebTermActivity.class);
                        startActivity(intent);
                    }
                });
                diff_privacy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(LoginActivithy.this, WebViewActivity.class);
                        startActivity(intent);
                    }
                });
                agreeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                             @Override
                                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                                 if(isChecked){
                                                                     flag_term = true;
                                                                 }
                                                                 else{
                                                                     flag_term = false;
                                                                 }

                                                             }
                                                         }

                );

                dilaog_term_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag_term ) {
                            agreeCheckBox.setChecked(false);
                            dialog.dismiss();
                            loginType = "social";
                            creaeRegistrationId("GPlus");
                        }
                    }
                });




                break;
            case R.id.twitterLogin:

                dialog.show();
                diff_terms_of_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(LoginActivithy.this, WebTermActivity.class);
                        startActivity(intent);
                    }
                });
                diff_privacy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(LoginActivithy.this, WebViewActivity.class);
                        startActivity(intent);
                    }
                });
                agreeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                             @Override
                                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                                                                 if(isChecked){
                                                                     flag_term = true;
                                                                 }
                                                                 else{
                                                                     flag_term = false;
                                                                 }

                                                             }
                                                         }

                );

                dilaog_term_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag_term ) {
                            agreeCheckBox.setChecked(false);
                            dialog.dismiss();
                            loginType = "social";
                            creaeRegistrationId("Twitter");
                        }
                        flag_term= false;
                    }
                });

                break;
        }
    }


    private void creaeRegistrationId(String type) {
        if (!ConnectionDetector.checkConnection(this)) {
            showDialogue("Internet Connection Error",
                    "Please connect to working Internet connection");
        } else {
            if (type.equalsIgnoreCase("facebook")) {
                callFacebookIntegration();
            } else if (type.equalsIgnoreCase("GPlus")) {
                signInWithGplus();
            } else {
                customLoginTwitter();
            }

        }
    }

    public void customLoginTwitter() {


        //check if user is already authenticated or not

            //if user is not authenticated start authenticating
            client.authorize(this, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {

                    // Do something with result, which provides a TwitterSession for making API calls
                    TwitterSession twitterSession = result.data;

                    //call fetch email only when permission is granted
                    fetchTwitterEmail(twitterSession);

                }

                @Override
                public void failure(TwitterException e) {
                    // Do something on failure
                    Toast.makeText(LoginActivithy.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
//        }else {
//            //if user is already authenticated direct call fetch twitter email api
//            Toast.makeText(this, "User already authenticated", Toast.LENGTH_SHORT).show();
//            fetchTwitterEmail(getTwitterSession());
//        }
    }

    private TwitterSession getTwitterSession() {
        TwitterSession sessionManager = TwitterCore.getInstance().getSessionManager().getActiveSession();
        return sessionManager;
    }

    public void fetchTwitterEmail(final TwitterSession twitterSession) {
        client.requestEmail(twitterSession, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                //here it will give u only email and rest of other information u can get from TwitterSession
                socialLoginEmail = result.data;
                socialLoginName = twitterSession.getUserName();

                fetchTwitterData();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(LoginActivithy.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchTwitterData() {
        //check if user is already authenticated or not
        if (getTwitterSession() != null) {
            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
            Call<User> call = twitterApiClient.getAccountService().verifyCredentials(true, false, true);
            call.enqueue(new Callback<User>() {
                @Override
                public void success(Result<User> result) {
                    User user = result.data;

                    socialLoginEmail = user.email;
                    socialLoginName = user.name;
                    socialImageURL = user.profileImageUrl;
                    signUpRequest();

                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(LoginActivithy.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //if user is not authenticated first ask user to do authentication
            Toast.makeText(this, "First to Twitter auth to Verify Credentials.", Toast.LENGTH_SHORT).show();
        }

    }


    private void callFacebookIntegration() {
        flag_term = false;
        loginButton.setVisibility(View.VISIBLE);
        loginButton.performClick();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                Utils.showProgressBar(progressIndicatorView);
                Log.i(TAG, "on success");
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (AccessToken.getCurrentAccessToken() != null) {
                                    if (object != null) {
                                        Log.d(TAG, "on completed " + response);
                                        try {
                                            socialUserId = object.getString("id");
                                            socialImageURL = "https://graph.facebook.com/" + socialUserId + "/picture?type=large";
                                            socialLoginName = object.getString("name");
                                            if (object.has("email"))
                                                socialLoginEmail = object.getString("email");
                                            else
                                                socialLoginEmail = object.getString("id") + "@thepixel.xyz";

                                            signUpRequest();
                                        } catch (JSONException e) {
                                            Log.d(TAG, e + "");
                                        }
                                    }
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) {
                Log.i(TAG, error.toString());
                Toast.makeText(LoginActivithy.this,"Server error!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivithy.this,"Cancel!",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Cancel");
            }
        });
    }

    private void signInWithGplus() {
        Utils.showProgressBar(progressIndicatorView, loginBtn);
        agreeCheckBox1_google.setChecked(false);
        agreeCheckBox_google.setChecked(false);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
       /* if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }*/
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }


    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
    }

    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }

    }

    protected void onStop() {
        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
        if (!mIntentInProgress) {
            mConnectionResult = result;
            if (mSignInClicked) {
                resolveSignInError();
            }
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


        if (client != null)
            client.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct.getPhotoUrl() != null) {
                socialImageURL = acct.getPhotoUrl().toString();
            }
            else{
                socialImageURL= null;
            }
            socialLoginName = acct.getDisplayName();
            socialLoginEmail = acct.getEmail();
            Utils.hideProgressBar(progressIndicatorView, loginBtn);
            signUpRequest();

        } else {
            Utils.hideProgressBar(progressIndicatorView, loginBtn);
            flag_term= false;
          //  Toast.makeText(LoginActivithy.this,"Server error!",Toast.LENGTH_SHORT).show();
        }
    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        if (!TextUtils.isEmpty(regId)) {
            //txtRegId.setText("Firebase Reg Id: " + regId);

            fcmRegId = regId;
            //Log.e(TAG, "Firebase reg id: " + regId);
            Log.e("fff", fcmRegId);
        } else {
            //txtRegId.setText("Firebase Reg Id is not received yet!");
            String str = "id not received yet";
            Log.e("fff", str);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

//

}

