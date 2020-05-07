package com.fmrnz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.fmrnz.model.LicenceModel;
import com.fmrnz.model.UserModel;
import com.fmrnz.pojo.RespomSIgnUpforOtpCheck;
import com.fmrnz.utils.AppConstant;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;

public class SignUpActivity extends BaseActivity implements
        AdapterView.OnItemSelectedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    EditText userNameET, emailET, mobileET, passwordET;
    Button signUPBtn;

    SessionManager session;

    AVLoadingIndicatorView progressIndicatorView;
    String userName, email, mobile, password;

    final Context context = this;
    private static final String TAG = MainActivity.class.getSimpleName();

    TextView diffText,skipNowSignUpTV;
    ImageView showPassword,hidePassword;
    CheckBox agreeCheckBox,agreeCheckBox1;
    private boolean mIntentInProgress;
    private GoogleApiClient mGoogleApiClient;


    private static final int RC_SIGN_IN = 0;

    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private SignInButton btnSignInGoogle;
    CallbackManager callbackManager;
    LoginButton loginButton;
    ImageButton customLoginButtonFacebook,customLoginButtonGPlus,customTwitterButton;
    String registrationId;
    static String socialUserId = "";

    private static boolean flag1 = false;

    private static String socialLoginName = "";
    private static String socialLoginEmail = "";
    private static String socialImageURL = "";

    private TwitterAuthClient client;
    String callingFrom;

    boolean isSocialLogin = false;
    String userType;
    public static int APP_REQUEST_CODE = 99;

    String password1;
    TextView diff_terms_of_service,diff_privacy;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_signup);

        alertDialog = new AlertDialog.Builder(this);
        registrationId = sessionManager.getRegistrationId();
        if(registrationId == null){
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

        session = new SessionManager(getApplicationContext());


        progressIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));

        userNameET = (EditText) findViewById(R.id.userName);
        emailET = (EditText) findViewById(R.id.email);
        mobileET = (EditText) findViewById(R.id.mobile);
        passwordET = (EditText) findViewById(R.id.password);
        diffText = (TextView) findViewById(R.id.diffText);
        skipNowSignUpTV = (TextView)findViewById(R.id.skipNowSignUp);
        showPassword = (ImageView) findViewById(R.id.shoPassword);
        hidePassword = (ImageView) findViewById(R.id.hidePassword);
        agreeCheckBox = (CheckBox)findViewById(R.id.checkbox);
        agreeCheckBox1 = (CheckBox)findViewById(R.id.checkbox1);
        diffText.setMovementMethod(LinkMovementMethod.getInstance());

        btnSignInGoogle = (SignInButton) findViewById(R.id.btn_sign_in1);
        btnSignInGoogle.setScopes(gso.getScopeArray());

        loginButton = (LoginButton)findViewById(R.id.login_button1);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        customLoginButtonFacebook = (ImageButton)findViewById(R.id.face1);
        customLoginButtonGPlus = (ImageButton)findViewById(R.id.google1);
        customTwitterButton = (ImageButton)findViewById(R.id.twitterLogin1);



        showPassword.setOnClickListener(this);
        hidePassword.setOnClickListener(this);
        skipNowSignUpTV.setOnClickListener(this);


        btnSignInGoogle.setOnClickListener(this);
        customLoginButtonFacebook.setOnClickListener(this);
        customLoginButtonGPlus.setOnClickListener(this);
        customTwitterButton.setOnClickListener(this);








        TextView signIn = (TextView) findViewById(R.id.signUp1);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivithy.class);
                startActivity(intent);
            }
        });

        signUPBtn = (Button) findViewById(R.id.signUp);
        signUPBtn.setOnClickListener(this);
//        signUPBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkConnectivity();


        //signUpRequest();
//                String rb_user_name = userNameET.getText().toString();
//
//                String phone = mobileET.getText().toString();
//
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("RBName", rb_user_name);
//                editor.putString("Contact", phone);
//                //    editor.putString("RBLOC",rb_user_location);
//             //   editor.putString("RBBG", bloodgroup);
//                editor.commit();
//            }
//        });


        diff_terms_of_service = (TextView) findViewById(R.id.diff_terms_of_service);
        diff_privacy = (TextView) findViewById(R.id.diff_privacy);
        diff_terms_of_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this, WebTermActivity.class);
                startActivity(intent);
            }
        });
        diff_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(SignUpActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUp:
                isSocialLogin = false;
                userType = "normal";
                checkConnectivity();

                break;
            case R.id.shoPassword:
                showPassword.setVisibility(View.GONE);
                hidePassword.setVisibility(View.VISIBLE);
                passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            case R.id.hidePassword:
                showPassword.setVisibility(View.VISIBLE);
                hidePassword.setVisibility(View.GONE);
                passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case R.id.skipNowSignUp:
                Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(i);
                break;
            case R.id.face1:
                if(agreeCheckBox.isChecked())
                {
                    isSocialLogin = true;
                    userType = "social";
                    creaeRegistrationId("Facebook");
                }
                else
                {
                    failureSweetDialg("CarRentalApp","User Membership Agreement; Please accept our terms of service and privacy policy to use Find My Ride NZ APP");
                }

                break;
            case R.id.google1:
                if(agreeCheckBox.isChecked()) {
                    isSocialLogin = true;
                    userType = "social";
                    creaeRegistrationId("GPlus");
                }
                else
                    failureSweetDialg("CarRentalApp","User Membership Agreement; Please accept our terms of service and privacy policy to use Find My Ride NZ APP");
                break;
            case R.id.twitterLogin1:
                if(agreeCheckBox.isChecked()) {
                    isSocialLogin = true;
                    userType = "social";
                    creaeRegistrationId("Twitter");
                }
                else
                    failureSweetDialg("CarRentalApp","User Membership Agreement; Please accept our terms of service and privacy policy to use Find My Ride NZ APP");
                break;
        }
    }


    private void creaeRegistrationId(String type) {
        if (!ConnectionDetector.checkConnection(this)) {
            showDialogue("Internet Connection Error",
                    "Please connect to working Internet connection");
        } else {
            if(type.equalsIgnoreCase("facebook"))
            {
                callFacebookIntegration();
            }
            else if(type.equalsIgnoreCase("GPlus"))
            {
                signInWithGplus();
            }
            else{
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
                    Toast.makeText(SignUpActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
//        } else {
//            //if user is already authenticated direct call fetch twitter email api
//            Toast.makeText(this, "User already authenticated", Toast.LENGTH_SHORT).show();
//            fetchTwitterEmail(getTwitterSession());
//        }
    }

    private TwitterSession getTwitterSession() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        return session;
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
                Toast.makeText(SignUpActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
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
                    mobile = "";
                    password = "";
                    //signUpRequest();
                    socialSignUpRequest();

                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(SignUpActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //if user is not authenticated first ask user to do authentication
            Toast.makeText(this, "First to Twitter auth to Verify Credentials.", Toast.LENGTH_SHORT).show();
        }

    }


    private void callFacebookIntegration() {
        loginButton.performClick();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                Utils.showProgressBar(progressIndicatorView);
                Log.i(TAG, "on success");
                GraphRequest request = GraphRequest.newMeRequest(
                        com.facebook.AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override

                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (com.facebook.AccessToken.getCurrentAccessToken() != null) {
                                    if (object != null) {
                                        Log.d(TAG, "on completed " + response);
                                        try {
                                            socialUserId = object.getString("id");
                                            socialImageURL = "https://graph.facebook.com/" + socialUserId + "/picture?type=large";
                                            socialLoginName = object.getString("name");
                                            if (object.has("email"))
                                                socialLoginEmail = object.getString("email");
                                            else
                                                socialLoginEmail= object.getString("id") + "@thepixel.xyz";
                                            flag1 = true;
                                            mobile = "";
                                            password = "";
                                            //signUpRequest();
                                            socialSignUpRequest();
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
            public void onError(FacebookException error)
            {
                Log.i(TAG, error.toString());
            }
            @Override
            public void onCancel() {
                Log.i(TAG, "Cancel");
            }
        });
    }

    private void signInWithGplus() {
        Utils.showProgressBar(progressIndicatorView);
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

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if(acct.getPhotoUrl() != null){
                socialImageURL = acct.getPhotoUrl().toString();
            }
            else{
                socialImageURL= null;
            }
            socialLoginName = acct.getDisplayName();
            socialLoginEmail = acct.getEmail();
            mobile = "";
            password = "";
            Utils.hideProgressBar(progressIndicatorView);
            socialSignUpRequest();

        } else {
        }
    }

    protected void onStop() {
        super.onStop();
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


    @Override
    protected void onResume() {
        super.onResume();
      //  passwordET.setEnabled(true);
     //   userNameET.setEnabled(true);
     //   mobileET.setEnabled(true);
    //    signUPBtn.setEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void checkConnectivity() {

        if (ConnectionDetector.checkConnection(this)) {
            if (!validate()) {
             //   userNameET.setEnabled(true);
             // //  emailET.setEnabled(true);
             //   passwordET.setEnabled(true);
              //  mobileET.setEnabled(true);
              //  signUPBtn.setEnabled(true);
                //      etAddress.setEnabled(false);
                Utils.hideProgressBar(progressIndicatorView, signUPBtn);

            } else {
                if(agreeCheckBox.isChecked() )
                {
                   // userNameET.setEnabled(false);
                    //googleSignInButton.setEnabled(false);
                  //  emailET.setEnabled(true);
                  //  passwordET.setEnabled(false);
                  //  mobileET.setEnabled(false);
                  //  signUPBtn.setEnabled(false);
                    //    etAddress.setEnabled(false);
                    signUpRequest();
                }
                else
                    Toast.makeText(this,"User Membership Agreement; Please accept our terms of service and privacy policy to use Find My Ride NZ APP",Toast.LENGTH_SHORT).show();


            }
        } else {
           // userNameET.setEnabled(true);
          //  emailET.setEnabled(true);
           // passwordET.setEnabled(true);
         //   mobileET.setEnabled(true);
         //   signUPBtn.setEnabled(true);
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
            Utils.hideProgressBar(progressIndicatorView, signUPBtn);
        }
    }

    public boolean validate() {

        boolean valid = true;

        userName = userNameET.getText().toString();
        socialLoginName = userName;
        email = emailET.getText().toString();
        socialLoginEmail = email;
        password = passwordET.getText().toString();
        mobile = mobileET.getText().toString();
        //address = etAddress.getText().toString();

        if (userNameET.getText().toString().trim().equals("")) {
            userNameET.setError("Required field");
            userNameET.requestFocus();
            valid = false;
        } else {
            if (!isValidName(userNameET.getText().toString())) {
                userNameET.setError("Please provide only alphabets");
                userNameET.requestFocus();
                valid = false;
            }
        }

        if (mobileET.getText().toString().trim().equals("")) {
            mobileET.setError("Required field");
            mobileET.requestFocus();

            valid = false;
        }
//        else {
//            final String m_no = mobileET.getText().toString();
//            if (!isValidMobileNo(m_no)) {
//                mobileET.setError("Please enter 12 digit Mobile number");
//                valid = false;
//            }
//        }

        if (passwordET.getText().toString().trim().equals("")) {
            passwordET.setError("Required field");
            passwordET.requestFocus();
            valid = false;
        }

        if (emailET.getText().toString().trim().equals("")) {
            emailET.setError("Required field");
            valid = false;
        } else {
            final String email = emailET.getText().toString();
            if (!isValidEmail(email)) {
                emailET.setError("Please provide a valid e-mail address");
                emailET.requestFocus();
                valid = false;
            }
        }




        return valid;
    }

    private  void socialSignUpRequest(){
        Utils.hideProgressBar(progressIndicatorView);
        Utils.showProgressBar(progressIndicatorView, signUPBtn);
        ESAppRequest esLoginRequest = (ESAppRequest)
                networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.SIGNUP);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", socialLoginName);
        hashMap.put("email", socialLoginEmail);
        hashMap.put("mobile", mobile);
        hashMap.put("password", password);
        hashMap.put("fcm_id", registrationId);
        hashMap.put("login_type", userType);
        if(!TextUtils.isEmpty(AppConstant.referalCode)){
            hashMap.put("refer", AppConstant.referalCode);
        }
        else{
            hashMap.put("refer", "");
        }
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }


    private void signUpRequest() {
        if(passwordET.getText().toString().length() < 8){
            passwordET.setError("Password length should be minimum 8 characters");
            passwordET.requestFocus();
           // Toast.makeText(this,"Password length should be minimum 8 characters",Toast.LENGTH_SHORT).show();
          //  passwordET.setEnabled(true);
         //   userNameET.setEnabled(true);
        //    mobileET.setEnabled(true);
         //   signUPBtn.setEnabled(true);
            return;
        }
        if(mobileET.getText().toString().length()<6 ){
            mobileET.setError("Please provide a valid mobile number consisting of 6 to 15 digits");
            mobileET.requestFocus();
           // passwordET.setEnabled(true);
          //  userNameET.setEnabled(true);
         //   mobileET.setEnabled(true);
         //   signUPBtn.setEnabled(true);
            return;
        }
            //checkForSignupActivity();
        //    phoneLogin();
      //  socialSignUpRequest();
        postdatawithformForOtpCjeck();



      /*  Utils.showProgressBar(progressIndicatorView, signUPBtn);
        ESAppRequest esLoginRequest = (ESAppRequest)
        networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.SIGNUP);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", userName);
        hashMap.put("email", email);
        hashMap.put("mobile", mobile);
        hashMap.put("password", password);
        hashMap.put("fcm_id", "12345");
        hashMap.put("login_type", "normal");
        if(!TextUtils.isEmpty(AppConstant.referalCode)){
            hashMap.put("refer", AppConstant.referalCode);
        }
        else{
            hashMap.put("refer", "");
        }
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);*/

    }
    private void postdatawithformForOtpCjeck() {

        Utils.showProgressBar(progressIndicatorView);

        AtechnosServerService Service = RetrofitRestController.getClient().create(AtechnosServerService.class);
        Call<RespomSIgnUpforOtpCheck> call = Service.postSignupRequest(socialLoginEmail,mobile);
        // TODO: 01/12/17 Below mentioned code is for aSynchronus call
        call.enqueue(new retrofit2.Callback<RespomSIgnUpforOtpCheck>() {
            @Override
            public void onResponse(Call<RespomSIgnUpforOtpCheck> call, Response<RespomSIgnUpforOtpCheck> response) {
                Utils.hideProgressBar(progressIndicatorView);
                if(response.body().getResponseCode().equalsIgnoreCase("0")){

                    phoneLogin();
                   // showAlertdialogforOtp(response.body().getResponseResult());
                    }
                if(response.body().getResponseCode().equalsIgnoreCase("1")){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);

                    // Setting Dialog Title
                    alertDialog.setTitle("Error");
                    // Setting Dialog Message
                    alertDialog.setMessage(response.body().getResponseResult());



                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.dismiss();
                            signUPBtn.setEnabled(true);

                            // Write your code here to invoke YES event

                        }
                    });
                    alertDialog.show();

                }


            }

            @Override
            public void onFailure(Call<RespomSIgnUpforOtpCheck> call, Throwable t) {
                Utils.hideProgressBar(progressIndicatorView);
            }
        });
    }

    private void showAlertdialogforOtp(String responseMessage) {

        // Setting Dialog Title
        alertDialog.setTitle("         Otp Verify!");

        // Setting Dialog Message
        alertDialog.setMessage("You have to verify phone number for sign up!");
        alertDialog.setCancelable(false);

        // Setting Icon to Dialog

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
                phoneLogin();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void phoneLogin() {
        PhoneNumber phoneNumber = new PhoneNumber("+91",mobile); // country code, phone number, country code

        String phonenumber;
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder = new AccountKitConfiguration.AccountKitConfigurationBuilder(
                LoginType.PHONE, AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.CODE
        UIManager uiManager = new SkinManager(
                LoginType.PHONE,
                SkinManager.Skin.TRANSLUCENT,
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? getResources().getColor(R.color.colorPrimary, null) : getResources().getColor(R.color.colorPrimary)),
                R.drawable.bacground_gradient,
                SkinManager.Tint.WHITE,
                0.55
        );

        /*If you want default country code*/
        // configurationBuilder.setDefaultCountryCode("IN");
        configurationBuilder.setUIManager(uiManager);

        configurationBuilder.setInitialPhoneNumber(phoneNumber);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
        if (client != null)
            client.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE && resultCode == RESULT_OK) {
            getCurrentAccount();
        }
    }

    private void getCurrentAccount() {
        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        if (accessToken != null) {
            //Handle Returning User
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {

                @Override
                public void onSuccess(final Account account) {

                    // Get Account Kit ID
                    String accountKitId = account.getId();
                    Log.e("Account Kit Id", accountKitId);

                    if (account.getPhoneNumber() != null) {
                        Log.e("CountryCode", "" + account.getPhoneNumber().getCountryCode());
                        Log.e("PhoneNumber", "" + account.getPhoneNumber().getPhoneNumber());

                        // Get phone number
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        String phoneNumberString = phoneNumber.toString();
                        Log.e("NumberString", phoneNumberString);

                        Utils.showProgressBar(progressIndicatorView, signUPBtn);
                        ESAppRequest esLoginRequest = (ESAppRequest)
                                networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.SIGNUP);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("name", socialLoginName);
                        hashMap.put("email", socialLoginEmail);
                        hashMap.put("mobile", String.valueOf(account.getPhoneNumber()));
                        hashMap.put("password", password);
                        hashMap.put("fcm_id", "12345");
                        hashMap.put("login_type", userType);
                        if(!TextUtils.isEmpty(AppConstant.referalCode)){
                            hashMap.put("refer", AppConstant.referalCode);
                        }
                        else{
                            hashMap.put("refer", "");
                        }
                        esLoginRequest.requestMap = hashMap;
                        networkController.sendNetworkRequest(esLoginRequest);


                    }

                    if (account.getEmail() != null)
                        Log.e("Email", account.getEmail());
                }

                @Override
                public void onError(final AccountKitError error) {
                    // Handle Error
                    Log.e(TAG, error.toString());
                }
            });

        } else {
            //Handle new or logged out user
            Log.e(TAG, "Logged Out");
            Toast.makeText(this, "Logged Out User", Toast.LENGTH_SHORT).show();
        }
    }


    public void handleNetworkEvent(int eventType, ESNetworkResponse
            networkResponse) {
        {
            Utils.hideProgressBar(progressIndicatorView, signUPBtn);
            switch (eventType) {
                case ESNetworkRequest.NetworkEventType.SIGNUP:
                    if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                        String responseMessage = networkResponse.responseMessage;
                        showDialogue("Suucess", responseMessage);
                        if (isSocialLogin){
                            UserModel userModel = networkResponse.userModel;
                            String userId = userModel.getId();
                            if(TextUtils.isEmpty(userModel.getImage())){
                                if(!TextUtils.isEmpty(socialImageURL)){
                                    userModel.setImage(socialImageURL);
                                    session.setImage(socialImageURL);
                                }
                            }
                            else{
                                session.setImage(userModel.getImage());
                            }
                            session.setImage(socialImageURL);
                            sessionManager.setPayStatus(userModel.getPay_status());
                            session.setLoggedIn(true);

                            ArrayList<LicenceModel> modelArrayList = networkResponse.licenceModelArrayList;
                            if(modelArrayList != null && modelArrayList.size() >0){
                                sessionManager.createLicenceData(modelArrayList.get(0));
                            }

                            session.createLoginSession(userModel.getName(), userModel.getId(), userModel.getEmail(), userModel.getMobile(), userModel.getPassword(), userModel.getToken(),userModel.getLogin_type(),userModel.getAddress_user(),userModel.getGender(),userModel.getAbout(),userModel.getImage(),userModel.getMember(),userModel.getNationality(),userModel.getPay_status());
                            if(!TextUtils.isEmpty(callingFrom)) {
                                if(callingFrom.equalsIgnoreCase("DriverLicence")){
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("ResponseCode",networkResponse.responseCode);
                                    setResult(Activity.RESULT_OK,returnIntent);

                                    finish();

                                }
                                else {
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else{
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else{
                            Intent intent = new Intent(SignUpActivity.this, LoginActivithy.class);
                            intent.putExtra("CallingFrom","SignUp");
                            startActivity(intent);
                            finish();
                        }

                    } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.ERROR) {
                        String responseMessage = networkResponse.responseMessage;

                        if(networkResponse.responseMessage.equalsIgnoreCase("This e-mail address is already in use by another user") && userType.equals("social")){
                            userType = "social";
                            email = socialLoginEmail;
                            password1 = "";
                            callServiceRequest();
                        }
                        else{
                            failureSweetDialg("CarRental",networkResponse.responseMessage);
                            LoginManager.getInstance().logOut();
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {

                                        }
                                    });
                        }

                      //  userNameET.setEnabled(true);
                      //  emailET.setEnabled(true);
                     //   passwordET.setEnabled(true);
                     //   mobileET.setEnabled(true);
                    //    signUPBtn.setEnabled(true);

                    }
                    else{
                        String responseMessage =
                                networkResponse.responseMessage;
                        showDialogue("CarRental", responseMessage);
                     //   userNameET.setEnabled(true);
                     //   emailET.setEnabled(true);
                      //  passwordET.setEnabled(true);
                      //  mobileET.setEnabled(true);
                      //  signUPBtn.setEnabled(true);
                    }
                    break;
                case ESNetworkRequest.NetworkEventType.LOGIN:
                    if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                        String responseMessage = networkResponse.responseMessage;
                        UserModel userModel = networkResponse.userModel;
                        String userId = userModel.getId();
                        sessionManager.setLoggedIn(true);
                        sessionManager.setPayStatus(userModel.getPay_status());
                        ArrayList<LicenceModel> modelArrayList = networkResponse.licenceModelArrayList;
                        if(modelArrayList != null && modelArrayList.size() >0){
                            sessionManager.createLicenceData(modelArrayList.get(0));
                        }

                        if(TextUtils.isEmpty(userModel.getImage())){
                            if(!TextUtils.isEmpty(socialImageURL)){
                                userModel.setImage(socialImageURL);
                                sessionManager.setImage(socialImageURL);
                            }
                        }
                        else{
                            sessionManager.setImage(userModel.getImage());
                        }


                        sessionManager.createLoginSession(userModel.getName(), userModel.getId(), userModel.getEmail(), userModel.getMobile(), userModel.getPassword(), userModel.getToken(),userModel.getLogin_type(),userModel.getAddress_user(),userModel.getGender(),userModel.getAbout(),userModel.getImage(),userModel.getMember(),userModel.getNationality(),userModel.getPay_status());


                        if(!TextUtils.isEmpty(callingFrom)) {
                            if(callingFrom.equalsIgnoreCase("DriverLicence")){
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("ResponseCode",networkResponse.responseCode);
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();

                            }
                            else {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else{
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }


                    }
                    else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.ERROR) {
                        failureSweetDialg("", networkResponse.responseMessage);

                    }
                    else{
                        failureSweetDialg("", networkResponse.responseMessage);

                    }
                    break;


            }

        }
    }

    private void callServiceRequest() {
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.LOGIN);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", password1);
        hashMap.put("fcm_id", registrationId);
        hashMap.put("login_type", userType);

        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }




    public void showDialogue(String title, String msg) {
        //new AlertDialog.Builder(this,R.style.AlertDialog)
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();

    }


    private boolean isValidName(String name) {
        Pattern pattern;
        pattern = Pattern.compile("[a-zA-Z ]{2,40}");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidMobileNo(String mobileNo) {
        Pattern pattern = Pattern.compile("^\\+[0-9]{6,15}$}");
        Matcher matcher = pattern.matcher(mobileNo);
        return matcher.matches();
    }

    private boolean isValidDob(String dob) {

        Date today = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            today = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*if (etDob.equals(today)) {
            etDob.setError("Future date and Current date is not allowed");

        }*/


        String dob_pattern = "";
        Pattern pattern = Pattern.compile(dob_pattern);
        Matcher matcher = pattern.matcher(dob);
        return matcher.matches();
    }

    private boolean isValidAddress(String add) {
        String address = "";
        Pattern pattern = Pattern.compile(address);
        Matcher matcher = pattern.matcher(add);
        return matcher.matches();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}


