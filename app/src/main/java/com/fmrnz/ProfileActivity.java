package com.fmrnz;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.UserModel;
import com.fmrnz.utils.CircularDefaultImageView;
import com.fmrnz.utils.CircularNetworkImageView;
import com.fmrnz.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    EditText mobileNumber, tvEmail, addressEditText, aboutEditText, userName;
    CircularNetworkImageView circularNetworkImageView;
    RadioButton radio1, radio2, radio3;
    CircularDefaultImageView defaultImageView;

    TextView member1;
    String phone, tEmail, user, address, aboutText, gender, member, spinnerValue;
    ImageView ivBack;
    LinearLayout llMenu;
    Button editProfile, saveProfile, updateButton;
    boolean isProfileEdit = false;
    ArrayList<UserModel> userListModelArrayList;
    private String profileID;
    HashMap<String, String> profileData;
    ImageLoader imageLoader;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    byte[] byteArray;
    RadioGroup radioGroup;
    Bitmap bitmap;
    String callingFrom;
    Spinner nanlaitySpinner;
    String[] items;

    SweetAlertDialog globalDialog;
    SweetAlertDialog SweetAlertDialog_skipLogin;
    View licenceView;
    LinearLayout licenceLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        if (!TextUtils.isEmpty(sessionManager.getUserDetails().get(SessionManager.KEY_ID)))
            profileDataDisplay();
        else
            appNotLogin();


    }

    private void profileDataDisplay() {
        imageLoader = networkController.getImageLoader();
        myProfileDataSetUpUI();
        setUpListener();
        gender = "Male";
        profileData = sessionManager.fetchProfileData();

        setUpData();

        callingFrom = getIntent().getStringExtra("CallingFrom");
        if (callingFrom.equalsIgnoreCase("BookingDetail")) {
            proceedFurtherProfile();

            isProfileEdit = true;
            editProfile.setVisibility(View.GONE);
            saveProfile.setVisibility(View.VISIBLE);


            circularNetworkImageView.setEnabled(true);
            defaultImageView.setEnabled(true);

            circularNetworkImageView.setClickable(true);
            defaultImageView.setClickable(true);

            if (!TextUtils.isEmpty(profileData.get(SessionManager.KEY_ADDRESS))) {
                addressEditText.setEnabled(false);
                addressEditText.setClickable(false);
            } else {
                addressEditText.setEnabled(true);
                addressEditText.setClickable(true);
            }

            if (!TextUtils.isEmpty(profileData.get(SessionManager.KEY_ABOUT))) {
                aboutEditText.setEnabled(false);
                aboutEditText.setClickable(false);
            } else {
                aboutEditText.setEnabled(true);
                aboutEditText.setClickable(true);
            }

            nanlaitySpinner.setEnabled(true);
            radioGroup.setEnabled(true);
            radio1.setEnabled(true);
            radio2.setEnabled(true);
            radio3.setEnabled(true);

            updateButton.setEnabled(true);

        } else {

            editProfile.setVisibility(View.VISIBLE);
            saveProfile.setVisibility(View.GONE);

            circularNetworkImageView.setEnabled(false);
            defaultImageView.setEnabled(false);

            circularNetworkImageView.setClickable(false);
            defaultImageView.setClickable(false);

            userName.setEnabled(false);
            tvEmail.setEnabled(false);
            mobileNumber.setEnabled(false);
            addressEditText.setEnabled(false);
            aboutEditText.setEnabled(false);
            member1.setClickable(false);
            mobileNumber.setClickable(false);
            addressEditText.setClickable(false);
            aboutEditText.setClickable(false);

            nanlaitySpinner.setEnabled(false);
            radioGroup.setEnabled(false);
            radio1.setEnabled(false);
            radio2.setEnabled(false);
            radio3.setEnabled(false);

            updateButton.setEnabled(false);
        }

    }

    private void appNotLogin() {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)


                .setContentText("Please login to access My Profile!!")

                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        SweetAlertDialog_skipLogin = sweetAlertDialog;
                        sweetAlertDialog.dismiss();
                        finish();


                    }
                })
                .show();
    }

    private void myProfileDataSetUpUI() {
        userName = (EditText) findViewById(R.id.tvName);

//        Typeface fontHindi = Typeface.createFromAsset(getAssets(),
//                "fonts/Ananda Lipi Bold Cn Bt.ttf");
//        userName.setTypeface(fontHindi);
        licenceView = (View) findViewById(R.id.licenceView);
        licenceLayout = (LinearLayout) findViewById(R.id.licenceLayout);
        tvEmail = (EditText) findViewById(R.id.tvEmail);
        mobileNumber = (EditText) findViewById(R.id.tvMobile);
        saveProfile = (Button) findViewById(R.id.saveProfile);
        editProfile = (Button) findViewById(R.id.editProfile);
        updateButton = (Button) findViewById(R.id.updateButton);
        ivBack = (ImageView) findViewById(R.id.ivback);
        llMenu = (LinearLayout) findViewById(R.id.llMenu);
        circularNetworkImageView = (CircularNetworkImageView) findViewById(R.id.updateImageView);
        defaultImageView = (CircularDefaultImageView) findViewById(R.id.normalImageView);
        addressEditText = (EditText) findViewById(R.id.tvAddress);
        aboutEditText = (EditText) findViewById(R.id.aboutEdit);
        member1 = (TextView) findViewById(R.id.member);


        nanlaitySpinner = (Spinner) findViewById(R.id.nanlaitySpinner);

        radioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        updateButton = (Button) findViewById(R.id.updateButton);

        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);


        circularNetworkImageView.setOnClickListener(this);
        defaultImageView.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);

        items = getResources().getStringArray(R.array.nationality_array);

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, items) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        nanlaitySpinner.setAdapter(spinnerArrayAdapter);

        nanlaitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                spinnerValue = selectedItemText;

                if (position > 0) {
//                    textView.setText("Selected : " + selectedItemText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        nanlaitySpinner.setAdapter(spinnerArrayAdapter);
    }

    private void setUpData() {

        //Need to remove as per requirement
        HashMap<String, String> licenceHashMap = sessionManager.getLicenceDetails();
        if (licenceHashMap.get(SessionManager.KEY_DRI_DETAIL) != null) {
            licenceView.setVisibility(View.VISIBLE);
            licenceLayout.setVisibility(View.VISIBLE);
        } else {
            licenceView.setVisibility(View.GONE);
            licenceLayout.setVisibility(View.GONE);
        }


        if (profileData.get(SessionManager.KEY_NAME) != null) {
            userName.setText(profileData.get(SessionManager.KEY_NAME));
            tvEmail.setText(profileData.get(SessionManager.KEY_EMAIL));
            mobileNumber.setText(profileData.get(SessionManager.KEY_MOBILE));
            addressEditText.setText(profileData.get(SessionManager.KEY_ADDRESS));
            aboutEditText.setText(profileData.get(SessionManager.KEY_ABOUT));
            member1.setText(Utility.convertStringIntoData2(profileData.get(SessionManager.KEY_MEMBER)));
            gender = profileData.get(SessionManager.KEY_GENDER);
            if (!TextUtils.isEmpty(gender)) {
                if (gender.equalsIgnoreCase("male")) {
                    radio1.setText(gender);
                    radio1.setChecked(true);

                } else if (gender.equalsIgnoreCase("female")) {
                    radio2.setText(gender);
                    radio2.setChecked(true);
                } else {
                    radio3.setText(gender);
                    radio3.setChecked(true);
                }
            } else {
                gender = "male";
                radio1.setText(gender);
                radio1.setChecked(true);
            }

            if (!TextUtils.isEmpty(profileData.get(SessionManager.KEY_NATIONALITY))) {
                String savedNationality = profileData.get(SessionManager.KEY_NATIONALITY);
                int index = -1;
                for (int i = 0; i < items.length; i++) {
                    if (items[i].equals(savedNationality)) {
                        index = i;
                        break;
                    }
                }

                nanlaitySpinner.setSelection(index);
            }


            if (!TextUtils.isEmpty(sessionManager.getImageBitmap())) {

                circularNetworkImageView.setVisibility(View.GONE);
                defaultImageView.setVisibility(View.VISIBLE);
                defaultImageView.setImageBitmap(Utility.StringToBitMap(sessionManager.getImageBitmap()));
            } else if (!TextUtils.isEmpty(profileData.get(SessionManager.KEY_IMAGE))) {
                circularNetworkImageView.setVisibility(View.VISIBLE);
                defaultImageView.setVisibility(View.GONE);
                circularNetworkImageView.setImageUrl(profileData.get(SessionManager.KEY_IMAGE), imageLoader);
            } else {
                circularNetworkImageView.setVisibility(View.GONE);
                defaultImageView.setVisibility(View.VISIBLE);
                circularNetworkImageView.setImageUrl(profileData.get(SessionManager.KEY_IMAGE), imageLoader);

            }
            profileID = profileData.get(SessionManager.KEY_ID);

        }
    }

    private void proceedFurtherProfile() {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Profile Update")
                .setContentText("Please fill the Profile Detail for proceed further!!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        globalDialog = sweetAlertDialog;
                        sweetAlertDialog.dismiss();


                    }
                })
                .show();
    }


    private void setUpListener() {
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isProfileEdit = true;
                tvEmail.setEnabled(false);
                member1.setEnabled(false);
                mobileNumber.setEnabled(true);
                userName.setEnabled(true);
                tvEmail.setClickable(false);
                member1.setEnabled(false);
                mobileNumber.setClickable(true);
                userName.setEnabled(true);

                addressEditText.setEnabled(true);
                aboutEditText.setEnabled(true);

                addressEditText.setClickable(true);
                aboutEditText.setClickable(true);
                nanlaitySpinner.setEnabled(true);
                radioGroup.setEnabled(true);
                radio1.setEnabled(true);
                radio2.setEnabled(true);
                radio3.setEnabled(true);

                updateButton.setEnabled(true);

                user = userName.getText().toString();
                phone = mobileNumber.getText().toString();
                tEmail = tvEmail.getText().toString();
                member = member1.getText().toString();
                address = addressEditText.getText().toString();
                aboutText = aboutEditText.getText().toString();


                saveProfile.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.GONE);

                circularNetworkImageView.setEnabled(true);
                defaultImageView.setEnabled(true);

                circularNetworkImageView.setClickable(true);
                defaultImageView.setClickable(true);
            }
        });


        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    user = userName.getText().toString();
                    phone = mobileNumber.getText().toString();
                    tEmail = tvEmail.getText().toString();
                    member = member1.getText().toString();
                    address = addressEditText.getText().toString();
                    aboutText = aboutEditText.getText().toString();

                    if (isProfileEdit) {
                        checkConnectivity1();
                    } else {

                    }


                }
            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DriverLicenceUpdateActivity.class);
                startActivity(intent);
            }
        });

        llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

//        if (mobileNumber.getText().toString().trim().equals(""))
//        {
//            mobileNumber.setError("Required field");
//            valid= false;
//        }

        if (mobileNumber.getText().toString().trim().equals("")) {
            mobileNumber.setError("Required field");
            valid = false;
        }

        if (addressEditText.getText().toString().trim().equals("")) {
            addressEditText.setError("Required field");
            valid = false;
        }
        if (aboutEditText.getText().toString().trim().equals("")) {
            aboutEditText.setError("Required field");
            valid = false;
        }


        return valid;
    }


    private void checkConnectivity1() {
        if (ConnectionDetector.checkConnection(this)) {
            updateProfileRequest();

        } else {

        }
    }

    private void updateProfileRequest() {
        // Utils.showProgressBar(progressIndicatorView1);
        ESAppRequest esLoginRequest = (ESAppRequest)
                networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.UPDATE_PROFILE);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile", phone);
        hashMap.put("name", user);
        hashMap.put("user_id", profileID);
        hashMap.put("address", address);
        hashMap.put("gender", gender);
        hashMap.put("about", aboutText);
        hashMap.put("nationality", spinnerValue);
        if (byteArray != null) {
            esLoginRequest.byteArray = byteArray;
        }


        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }


    public void handleNetworkEvent(int eventType, final ESNetworkResponse networkResponse) {
        {
            // Utils.hideProgressBar(progressIndicatorView, signUPBtn);
            switch (eventType) {
                case ESNetworkRequest.NetworkEventType.UPDATE_PROFILE:
                    if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                        String responseMessage = networkResponse.responseMessage;
                        suuccessSweetDialgofForSuccess("CarRental", responseMessage, networkResponse.responseCode);



                       /* saveProfile.setVisibility(View.GONE);
                        editProfile.setVisibility(View.VISIBLE);

                        tvEmail.setEnabled(false);
                        mobileNumber.setEnabled(false);
                        addressEditText.setEnabled(false);
                        aboutEditText.setEnabled(false);

                        tvEmail.setClickable(false);
                        mobileNumber.setClickable(false);
                        addressEditText.setClickable(false);
                        aboutEditText.setClickable(false);*/


                    } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.ERROR) {
                        String responseMessage =
                                networkResponse.responseMessage;
                        showDialogue("CarRentalApp", responseMessage);

                        if (callingFrom.equalsIgnoreCase("BookingDetail")) {
                            globalDialog
                                    .setTitleText("Error!")
                                    .setContentText("Your Profile Data has not been updated successfully!")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Intent returnIntent = new Intent();
                                            returnIntent.putExtra("ResponseCode", networkResponse.responseCode);
                                            setResult(Activity.RESULT_OK, returnIntent);
                                            finish();

                                        }
                                    })
                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                        } else {
                            finish();
                        }
                        /*tvEmail.setEnabled(true);
                        mobileNumber.setEnabled(true);
                        saveProfile.setEnabled(true);
                        addressEditText.setEnabled(true);
                        aboutEditText.setEnabled(true);
                        saveProfile.setVisibility(View.VISIBLE);
                        editProfile.setVisibility(View.GONE);*/

                    }
                    break;
            }

        }
    }

    public void suuccessSweetDialgofForSuccess(String title, String msg, final int code) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (bitmap != null) {
                            String bitmapImage = Utility.BitMapToString(bitmap);
                            sessionManager.setImageBitmap(bitmapImage);
                        }
                        sessionManager.createLoginSession(user, profileID, tEmail, phone, profileData.get(SessionManager.KEY_PASSWORD), profileData.get(SessionManager.KEY_TOKEN), profileData.get(SessionManager.KEY_LOGIN_TYPE)
                                , address, gender, aboutText, profileData.get(SessionManager.KEY_IMAGE), member, spinnerValue, profileData.get(SessionManager.KEY_PAY_STATUS));


                        if (callingFrom.equalsIgnoreCase("BookingDetail")) {

                            new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("Profile Update Sucessfull")
                                    .setContentText("Your profile has been updated succesfully!!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Intent returnIntent = new Intent();
//                                            returnIntent.putExtra("ResponseCode",code);
                                            returnIntent.putExtra("ResponseCode", "ProfileUpdate");
                                            setResult(2000, returnIntent);
                                            setResult(Activity.RESULT_OK, returnIntent);
                                            finish();
                                            sweetAlertDialog.dismiss();


                                        }
                                    })
                                    .show();


                        } else {
                            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                        }


                    }
                })
                .show();
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
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher matcher = pattern.matcher(mobileNo);
        return matcher.matches();
    }


    private void suuccessSweetDialgofForAddRequest(String title, String msg, final String type) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (type.equalsIgnoreCase("Request")) {
//                            etlocation.getText().clear();
//                            etDescription.getText().clear();
                            sweetAlertDialog.dismiss();
                        } else if (type.equalsIgnoreCase("Profile")) {
                            sweetAlertDialog.dismiss();

                        }
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility1.checkPermission(ProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {

        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byteArray = stream.toByteArray();
        circularNetworkImageView.setVisibility(View.GONE);
        defaultImageView.setVisibility(View.VISIBLE);
        defaultImageView.setImageBitmap(bitmap);


//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//
//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        imageCar.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byteArray = stream.toByteArray();
                circularNetworkImageView.setVisibility(View.GONE);
                defaultImageView.setVisibility(View.VISIBLE);
                defaultImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        circularNetworkImageView.setVisibility(View.GONE);
        defaultImageView.setVisibility(View.VISIBLE);
        defaultImageView.setImageBitmap(bitmap);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateImageView:
            case R.id.normalImageView:
                cameraPermission();
                break;


        }
    }

    private void cameraPermission() {
        if (ContextCompat.checkSelfPermission(ProfileActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
                Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
        }
    }


    private void dispatchTakenPictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        gender = radioButton.getText().toString();

    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();

    }
}
