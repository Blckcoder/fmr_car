package com.fmrnz;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.utils.Utility;

public class DialogActivity extends BaseActivity implements View.OnClickListener {

    TextView ownerNameDialog, ownerTypeDialog, ownerAboutDialog,ownerAddDialog,memberDialog;
    CarRentalListModel carRentalListModel;
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
        memberDialog = (TextView) findViewById(R.id.memberDialog);
        userrImageView = (NetworkImageView) findViewById(R.id.userrImageView1);
        imageLoader = networkController.getImageLoader();
    }

    private void setUpListners(){
        next.setOnClickListener(this);
    }


    private void fetchIntentData() {
        carID = getIntent().getStringExtra("carID");
        carRentalListModel = (CarRentalListModel) getIntent().getParcelableExtra("RentalData");
        startDate = getIntent().getStringExtra("Start Date");
        endDate = getIntent().getStringExtra("End Date");

    }


    private void setUpData() {


        if (!TextUtils.isEmpty(carRentalListModel.getOwner_type())) {
            ownerTypeDialog.setText(carRentalListModel.getOwner_type());
        }
        if (!TextUtils.isEmpty(carRentalListModel.getOwner_name())) {
            ownerNameDialog.setText(carRentalListModel.getOwner_name());
        }

        if (!TextUtils.isEmpty(carRentalListModel.getOwner_image())) {
            userrImageView.setImageUrl(carRentalListModel.getOwner_image(),imageLoader);
        }

        if (!TextUtils.isEmpty(carRentalListModel.getMember_since())) {
            memberDialog.setText(Utility.convertStringIntoData2(carRentalListModel.getMember_since()));
        }

        if (!TextUtils.isEmpty(carRentalListModel.getDescription())) {
            ownerAboutDialog.setText(carRentalListModel.getDescription());
        }

        if (!TextUtils.isEmpty(carRentalListModel.getAddress())) {
            ownerAddDialog.setText(carRentalListModel.getAddress());
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
