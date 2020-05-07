package com.fmrnz;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.model.RideDetailModel;
import com.fmrnz.utils.Utility;

public class DialogActivity2 extends BaseActivity implements View.OnClickListener {

    TextView ownerNameDialog, ownerTypeDialog, ownerAboutDialog,ownerAddDialog,memberDialog,ownContactDialog;
    CarRentalListModel rentalListModel;
    RideDetailModel rideDetailModel;

    ImageLoader imageLoader;
    NetworkImageView userrImageView;
    Button next;
    String carID;
    String startDate, endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        fetchIntentData();
        setUpView();
        setUpListners();
        setUpData();

    }

    private void setUpView() {
        next = (Button) findViewById(R.id.customDialogOk);
        ownerNameDialog = (TextView) findViewById(R.id.ownerNameDialog);
        ownerTypeDialog = (TextView) findViewById(R.id.ownerTypeDialog);
        ownerAddDialog = (TextView) findViewById(R.id.ownerAddDialog);
        ownerAboutDialog = (TextView) findViewById(R.id.ownAboutDialog);
        ownContactDialog = (TextView) findViewById(R.id.ownContactDialog);

        memberDialog = (TextView) findViewById(R.id.memberDialog);
        userrImageView = (NetworkImageView) findViewById(R.id.userrImageView1);
        imageLoader = networkController.getImageLoader();
    }

    private void setUpListners(){
        next.setOnClickListener(this);
    }


    private void fetchIntentData() {
        carID = getIntent().getStringExtra("carID");
        rentalListModel = (CarRentalListModel) getIntent().getParcelableExtra("RentalData");
//        rideDetailModel = (RideDetailModel) getIntent().getParcelableExtra("RideDetail");

        startDate = getIntent().getStringExtra("Start Date");
        endDate = getIntent().getStringExtra("End Date");

    }


    private void setUpData() {


        if (!TextUtils.isEmpty(rentalListModel.getOwner_type())) {
            ownerTypeDialog.setText(rentalListModel.getOwner_type());
        }
        if (!TextUtils.isEmpty(rentalListModel.getOwner_name())) {
            ownerNameDialog.setText(rentalListModel.getOwner_name());
        }

        if (!TextUtils.isEmpty(rentalListModel.getOwner_image())) {
            userrImageView.setImageUrl(rentalListModel.getOwner_image(),imageLoader);
        }

        if (!TextUtils.isEmpty(rentalListModel.getMember_since())) {
            memberDialog.setText(Utility.convertStringIntoData2(rentalListModel.getMember_since()));
        }

        if (!TextUtils.isEmpty(rentalListModel.getDescription())) {
            ownerAboutDialog.setText(rentalListModel.getDescription());
        }

        if (!TextUtils.isEmpty(rentalListModel.getAddress())) {
            ownerAddDialog.setText(rentalListModel.getAddress());
        }

        if (!TextUtils.isEmpty(rentalListModel.getPhone_number())) {
            ownContactDialog.setText(rentalListModel.getPhone_number());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.customDialogOk:
                finish();
                break;
        }
    }
}
