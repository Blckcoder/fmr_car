package com.fmrnz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.fragment.BaseFragment;
import com.fmrnz.fragment.CancelledFragment;
import com.fmrnz.fragment.FinishedFragment;
import com.fmrnz.fragment.OngoingFragment;
import com.fmrnz.fragment.UpcomingFragment;

import java.util.ArrayList;
import java.util.List;


public class MyRideActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String userID;
    LinearLayout llMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ride);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        loginHashMap = sessionManager.getUserDetails();
        userID = loginHashMap.get(SessionManager.KEY_ID);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(TextUtils.isEmpty(sessionManager.getUserDetails().get(SessionManager.KEY_ID)))
        {
            appNotLogin();
            return;
        }
        else{
            addTabs(viewPager);
        }


        llMenu = (LinearLayout)findViewById(R.id.llMenu);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(MyRideActivity.this, MainActivity.class);
                backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backIntent);
            }
        });


    }

    private void appNotLogin(){
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)

                .setContentText("Please login to access My Rides!!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        finish();
                        return;


                    }
                })
                .show();
    }


    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OngoingFragment(), "Ongoing");//1
        adapter.addFrag(new UpcomingFragment(), "Upcoming");//0
        adapter.addFrag(new FinishedFragment(), "Finished");//2
        adapter.addFrag(new CancelledFragment(), "Cancelled");//2

        viewPager.setAdapter(adapter);
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

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        if (networkResponse != null) {
            if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS || networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                @SuppressLint("RestrictedApi") List<Fragment> frg = getSupportFragmentManager().getFragments();
                for (int i = 0; i < frg.size(); i++) {
                    Fragment frgmnt = frg.get(i);
                    if(frgmnt instanceof BaseFragment){
                        BaseFragment baseFragment = (BaseFragment) frgmnt;
                        if (baseFragment != null) {
                            switch (baseFragment.type) {
                                case BaseFragment.FragemntType.ONGOING_FRAGMENT:
                                    OngoingFragment ongoingFragment = (OngoingFragment) baseFragment;
                                    ongoingFragment.updateDataonOnNetworkResponse1(eventType, networkResponse);
                                    break;

                                case BaseFragment.FragemntType.UPCOMING_FRAGMENT:
                                    UpcomingFragment upcomingFragment = (UpcomingFragment) baseFragment;
                                    upcomingFragment.updateDataonOnNetworkResponse1(eventType, networkResponse);
                                    break;

                                case BaseFragment.FragemntType.FINISHED_FRAGMENT:
                                    FinishedFragment finishedFragment = (FinishedFragment) baseFragment;
                                    finishedFragment.updateDataonOnNetworkResponse1(eventType, networkResponse);
                                    break;

                                case BaseFragment.FragemntType.CANCELLED_FRAGMENT:
                                    CancelledFragment cancelledFragment = (CancelledFragment) baseFragment;
                                    cancelledFragment.updateDataonOnNetworkResponse1(eventType, networkResponse);
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