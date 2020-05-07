package com.fmrnz;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmrnz.SharedPref.SessionManager;
import com.google.android.gms.common.api.GoogleApiClient;


public class SplashActivity extends BaseActivity  {

    SessionManager sessionManager;
    Button get_start;
    ImageView image;

    Animation animFadein;
    TextView textView;

    private Location mylocation;
    GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    String locationAddress;


    private static final int SPLASH_DISPLAY_TIME = 2000;  /* 2 seconds */

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //   Utils.addGoogleAnalytics(SplashActivity.this, AppConstant.MAINACTIVITY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sessionManager = new SessionManager(getApplicationContext());
        //  get_start = (Button) findViewById(R.id.btnnext);
        //   get_start.setOnClickListener(new View.OnClickListener() {

        openCarApp();

        image = (ImageView) findViewById(R.id.imahe);
        textView = (TextView)findViewById(R.id.textView);

//        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.zoom_in);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.zoom_in);
        image.startAnimation(animation);

        sessionManager = new SessionManager(getApplicationContext());
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        textView.startAnimation(animation1);


    }

//
    private void openCarApp(){
//        sessionManager = new SessionManager(getApplicationContext());
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
//        image.startAnimation(animation);
//        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
//        textView.startAnimation(animation1);
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(5000);
//                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
//                    image.startAnimation(animation);
//                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
//                    textView.startAnimation(animation1);
                    boolean sessionBool = sessionManager.getLoggedIn();
                    if(sessionBool)
                    {

                        Intent i = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Intent i = new Intent(SplashActivity.this,LoginActivithy.class);
//                        i.putExtra("CallingFrom","Splash");
                        startActivity(i);
                        finish();
                    }

                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally
                {

                }
            }
        };
        timerThread.start();


     /*   Thread timer = new Thread() {
            public void run() {
                try {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                    image.startAnimation(animation);
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                    textView.startAnimation(animation1);
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(i);
                }
            }
        };
        timer.start();*/
    }

    @Override    protected void onPause() {
        super.onPause();
        finish();

    }


}