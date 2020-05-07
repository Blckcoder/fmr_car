package com.fmrnz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.fragment.BaseFragment;
import com.fmrnz.fragment.CarPoolingFragment;
import com.fmrnz.fragment.CarRentalFragment;
import com.fmrnz.fragment.TaxiFragment;
import com.fmrnz.utils.AppConstant;
import com.fmrnz.utils.CircularDefaultImageView;
import com.fmrnz.utils.CircularNetworkImageView;
import com.fmrnz.utils.Utility;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends LocationBaseActivity implements NavigationView.OnNavigationItemSelectedListener,LocationListener/*,GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            LocationListener*/ {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Location mylocation;
    String locationAddress;
    GoogleApiClient mGoogleApiClient;
    ImageLoader imageLoader;
    String callingFrom;
    CircularDefaultImageView drawerImageView;
    CircularNetworkImageView circular;


    ImageView ivMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);




        imageLoader = networkController.getImageLoader();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
    //         getSupportActionBar().setTitle(null);


            viewPager = (ViewPager) findViewById(R.id.viewpager);
            addTabs(viewPager);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            // setupTabIcons();


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        MenuItem nav_logout = menu.findItem(R.id.nav_sign_out);
        if(!TextUtils.isEmpty(sessionManager.getUserDetails().get(SessionManager.KEY_ID))){

            nav_logout.setTitle("Log Out");
        }
        else{
            nav_logout.setTitle("Sign In");
        }

        // set new title to the MenuItem


        View headerLayout = navigationView.getHeaderView(0);

             drawerImageView = (CircularDefaultImageView) headerLayout.findViewById(R.id.drawerImageView);
            TextView drawerTextView = (TextView)headerLayout.findViewById(R.id.drawerUserName);
        circular = (CircularNetworkImageView)headerLayout.findViewById(R.id.circulardrawerImageView);
        HashMap<String, String> profileData = sessionManager.fetchProfileData();
        if (profileData.get(SessionManager.KEY_NAME) != null){
            drawerTextView.setText(profileData.get(SessionManager.KEY_NAME));
        }
        if(!TextUtils.isEmpty(sessionManager.getImageBitmap())){

            circular.setVisibility(View.GONE);
            drawerImageView.setVisibility(View.VISIBLE);
            drawerImageView.setImageBitmap(Utility.StringToBitMap(sessionManager.getImageBitmap()));
//                circularNetworkImageView.setImageUrl(profileData.get(SessionManager.KEY_IMAGE),imageLoader);
//                circularNetworkImageView.setImageBitmap(Utility.StringToBitMap(profileData.get(SessionManager.KEY_IMAGE_BITMAP)));
        }
        else if (!TextUtils.isEmpty(sessionManager.getImage())){
            circular.setVisibility(View.VISIBLE);
            drawerImageView.setVisibility(View.GONE);
            circular.setImageUrl(sessionManager.getImage(),imageLoader);
        }
        else{
            circular.setVisibility(View.GONE);
            drawerImageView.setVisibility(View.VISIBLE);

        }


        }



        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        private void addTabs(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFrag(new CarRentalFragment(), "CAR RENTAL");
            adapter.addFrag(new CarPoolingFragment(), "CAR POOLING");
            adapter.addFrag(new TaxiFragment(), "TAXI");
            viewPager.setAdapter(adapter);
        }



    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            double latitude=mylocation.getLatitude();
            double longitude=mylocation.getLongitude();
            AppConstant.latitude = latitude;
            AppConstant.longitude = longitude;
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getApplicationContext(),new GeocoderHandler());
            //Or Do whatever you want with your location
        }
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    AppConstant.user_current_location = locationAddress;
                    break;
                default:
                    locationAddress = null;
            }
            AppConstant.user_current_location = locationAddress;
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public ViewPagerAdapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFrag(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
        }


        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.

            int id = item.getItemId();

            if (id == R.id.nav_ern) {

                Intent inotification1 = new Intent(MainActivity.this, ReferActivity.class);
                startActivity(inotification1);
//                String app_share = "https://play.google.com/store/apps/details?id=com.nhp.bloodbank&referrer=ABCPQR";
//                Intent sendIntent = new Intent();
//                String msgString = app_share;
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, msgString);
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);
//
//                sendInvitesRequest();


        }

            if (id == R.id.nav_sign_out) {
                if(!TextUtils.isEmpty(sessionManager.getUserDetails().get(SessionManager.KEY_ID))){


                    new AlertDialog.Builder(this)
                            .setMessage("Are you sure you want to log out?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                            new ResultCallback<Status>() {
                                                @Override
                                                public void onResult(Status status) {
                                                    SessionManager sessionManager1 = new SessionManager(MainActivity.this);
                                                    sessionManager.logoutUser();
                                                    sessionManager.setLoggedIn(false);
                                                    LoginManager.getInstance().logOut();
                                                //    sessionManager1.setImage(null);
                                                //    sessionManager1.setImageBitmap(null);
                                                //    sessionManager.setImage(null);
                                                //    sessionManager.setImageBitmap(null);
                                                }
                                            });

                                    finishAffinity();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })

                            .show();
                }else{
                  Intent i= new Intent(this,LoginActivithy.class);
                  startActivity(i);
                }





           }


//         else if (id == R.id.nav_trip) {
//
//            Intent inotification = new Intent(MainActivity.this, OTPActivity.class);
//            startActivity(inotification);
           // finish();


       // }
        else if (id == R.id.nav_profile) {

                Intent inotification1 = new Intent(MainActivity.this, ProfileActivity.class);
                inotification1.putExtra("CallingFrom","SideMenu");
                startActivity(inotification1);
               // finish();

            }
            else if (id == R.id.nav_ride) {

                Intent inotification1 = new Intent(MainActivity.this, MyRideActivity.class);
                startActivity(inotification1);
              //  finish();

            }

            else if (id == R.id.nav_fmr) {

                Intent inotification1 = new Intent(MainActivity.this, FMRPointsActivity.class);
                startActivity(inotification1);
                //  finish();

            }

            else if (id == R.id.nav_fmr1) {

                Intent inotification1 = new Intent(MainActivity.this, FmrOfferActivity.class);
                startActivity(inotification1);
                //  finish();

            }

            else if (id == R.id.nav_support) {

                Intent inotification1 = new Intent(MainActivity.this, SupportActivity.class);
                startActivity(inotification1);
                //  finish();

            }

            else if (id == R.id.nav_how) {

                Intent inotification1 = new Intent(MainActivity.this, HowActivity.class);
                startActivity(inotification1);
                //  finish();

            }

            else if (id == R.id.nav_terms) {
                Intent inotification1 = new Intent(MainActivity.this, TermsActivity.class);
                startActivity(inotification1);
                //  finish();

            }

            else if (id == R.id.nav_about) {
                Intent inotification1 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(inotification1);
                //  finish();

            }

            else if (id == R.id.nav_faq) {
                Intent inotification1 = new Intent(MainActivity.this, FAQActivity.class);
                startActivity(inotification1);
                //  finish();

            }
            else if (id == R.id.nav_rate) {
                Intent inotification1 = new Intent(MainActivity.this, RateAppActivity.class);
                startActivity(inotification1);

                //  finish();

            }



            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


        @Override
        protected void onStop() {
            //sliderLayout.stopAutoCycle();
            super.onStop();
        }


        @Override
        protected void onStart() {
    //        sliderLayout.startAutoCycle();
            super.onStart();
        }

        public void onSliderClick(BaseSliderView slider) {

        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        public void onPageSelected(int position) {

        }

        public void onPageScrollStateChanged(int state) {

        }


        private void setNavMenuItemColor(MenuItem item, int color) {
            SpannableString span = new SpannableString(item.getTitle());
            span.setSpan(new ForegroundColorSpan(color), 0, span.length(), 0);
            item.setTitle(span);
        }

        private void sendInvitesRequest(){
            ESAppRequest esAppRequest = (ESAppRequest)networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.SENT_INVITES);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", sessionManager.fetchProfileData().get(SessionManager.KEY_ID));
            hashMap.put("refer", "ABCPQR");
            esAppRequest.requestMap = hashMap;
            networkController.sendNetworkRequest(esAppRequest);
        }



        public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
            switch (eventType){
                case ESNetworkRequest.NetworkEventType.SENT_INVITES:
                    if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                        String responseMessage = networkResponse.responseMessage;
                      //  showDialogue("Succes", responseMessage);
                    }
                    else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                        String responseMessage = networkResponse.responseMessage;
                        showDialogue("NO DATA", responseMessage);
                    }
                    else{
                        String responseMessage = networkResponse.responseMessage;
                        showDialogue("Failure", responseMessage);
                    }
            }
            if (networkResponse != null) {
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS || networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                    @SuppressLint("RestrictedApi") List<Fragment> frg = getSupportFragmentManager().getFragments();
                    for (int i = 0; i < frg.size(); i++) {
                        Fragment frgmnt = frg.get(i);
                        if(frgmnt instanceof BaseFragment){
                            BaseFragment baseFragment = (BaseFragment) frgmnt;
                            if (baseFragment != null) {
                                switch (baseFragment.type) {
                                    case BaseFragment.FragemntType.CAR_RENTAL_FRAGMENT:
                                        CarRentalFragment articleFragment = (CarRentalFragment) baseFragment;
                                        articleFragment.updateDataonOnNetworkResponse(eventType, networkResponse);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }


                    }
                }
            }
        }
    }