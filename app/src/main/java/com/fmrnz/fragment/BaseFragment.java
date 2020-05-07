package com.fmrnz.fragment;


import android.support.v4.app.Fragment;

import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESNetworkController;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.interfaces.FragmentBackListener;


/**
 * Created by upender on 14/08/15.
 */
public class BaseFragment extends Fragment implements FragmentBackListener {
    public int type;
    public ESNetworkController networkController = ESNetworkController.getInstance();
    SessionManager sessionManager;



    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onBack(int uiEventType) {
        getActivity().finish();

    }


    public interface FragemntType {
        int CAR_RENTAL_FRAGMENT  = 100;
        int ONGOING_FRAGMENT = 101;
        int UPCOMING_FRAGMENT = 102;
        int FINISHED_FRAGMENT = 103;
        int CANCELLED_FRAGMENT = 104;



    }

    public void handleFragmentNetworkEvent(int eventType, ESNetworkResponse networkResponse)
    {

    }

    public void failureSweetDialg(String title, String msg){
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .show();
    }

    public void failureNotifySweetDialg(String title, String msg){
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                     //   finish();

                    }
                })
                .show();
    }



    public void failureSweetDialgForNoConnection(String title, String msg){
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .show();
    }



    public void suuccessSweetDialgofForSuccess(String title, String msg){
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                       // finish();

                    }
                })
                .show();
    }


}
