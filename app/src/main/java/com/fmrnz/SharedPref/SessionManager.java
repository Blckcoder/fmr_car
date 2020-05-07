package com.fmrnz.SharedPref;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.fmrnz.LoginActivithy;
import com.fmrnz.model.LicenceModel;

import java.util.HashMap;

/**
 * Created by upen on 18/04/17.
 */

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "NHPDATA";

    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String IS_Dailog = "IsDialog";

    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FCM = "fcmID";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_LOGIN_TYPE = "logintype";

    public static final String KEY_ADDRESS = "address";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ABOUT = "about";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_NATIONALITY = "nationality";

    public static final String KEY_IMAGE_BITMAP = "imagebitmap";
    public static final String KEY_IMAGE_BITMAP_LICENCE = "imagebitmaplicence";

    public static final String IS_LICENCE_FILLED = "licencefilled";
    public static final String LICENCE_ID = "licenceID";
    public static final String KEY_MEMBER = "member";
    public static final String KEY_PAY_STATUS = "paystatus";


    public static final String KEY_DRI_DETAIL = "driving_detail_id";
    public static final String KEY_DL_COUNTRY = "dl_country";
    public static final String KEY_DL_COUNTRY_ISSUE = "dl_country_issue";
    public static final String KEY_DL_ISSUE_DATE = "dl_issue_date";
    public static final String KEY_DL_EXPIRY_DATE = "dlexpiry_date";
    public static final String KEY_CUSTOMER_NAME = "customer_name";
    public static final String KEY_DL_NUMBER = "dl_number";
    public static final String KEY_COMPLETE_ADDRESS = "complete_address";
    public static final String KEY_ALTERNATE_NUMBER ="alternate_number";
    public static final String KEY_CUSTOMER_DOB ="customer_dob";
    public static final String KEY_DRI_CUSTOMER_IMAGE ="licenceimage";




    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setDialogStatus(boolean status) {
        editor.putBoolean(IS_Dailog, status);
        editor.commit();
    }

    public boolean getDialogStatus() {

        return pref.getBoolean(IS_Dailog, false);
    }

    public void setLoggedIn(boolean status) {
        editor.putBoolean(IS_LOGIN, status);
        editor.commit();
    }

    public void createLoginSession(String name, String id, String email, String mobile, String password, String token,String loginType,
                                   String address, String gender, String about,String image,String member,String nationality,String payStatus ) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_LOGIN_TYPE, loginType);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_ABOUT, about);
        editor.putString(KEY_IMAGE, image);
        editor.putString(KEY_MEMBER, member);
        editor.putString(KEY_NATIONALITY, nationality);
        editor.commit();
    }

//    public void createLicenceData(String driving_detail_id,String dl_country,String dl_country_issue,String dl_issue_date,String dlexpiry_date,String customer_name, String dl_number,String customer_dob,String alternate_number,String complete_address,String image){
public void createLicenceData(LicenceModel licenceModel){

    editor.putString(KEY_DRI_DETAIL, licenceModel.getDriving_detail_id());
        editor.putString(KEY_DL_COUNTRY, licenceModel.getDl_country());
        editor.putString(KEY_DL_COUNTRY_ISSUE, licenceModel.getDl_country_issue());
        editor.putString(KEY_DL_ISSUE_DATE, licenceModel.getDl_issue_date());
        editor.putString(KEY_DL_EXPIRY_DATE, licenceModel.getDlexpiry_date());
        editor.putString(KEY_CUSTOMER_NAME, licenceModel.getCustomer_name());
        editor.putString(KEY_DL_NUMBER, licenceModel.getDl_number());
        editor.putString(KEY_CUSTOMER_DOB, licenceModel.getCustomer_dob());
        editor.putString(KEY_ALTERNATE_NUMBER, licenceModel.getAlternate_number());
        editor.putString(KEY_COMPLETE_ADDRESS, licenceModel.getComplete_address());
        editor.putString(KEY_DRI_CUSTOMER_IMAGE, licenceModel.getLicenceImage());
        editor.commit();
    }

    public HashMap<String, String> getLicenceDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_DRI_DETAIL, pref.getString(KEY_DRI_DETAIL, null));
        user.put(KEY_DL_COUNTRY, pref.getString(KEY_DL_COUNTRY, null));
        user.put(KEY_DL_COUNTRY_ISSUE, pref.getString(KEY_DL_COUNTRY_ISSUE, null));
        user.put(KEY_DL_ISSUE_DATE, pref.getString(KEY_DL_ISSUE_DATE, null));
        user.put(KEY_DL_EXPIRY_DATE, pref.getString(KEY_DL_EXPIRY_DATE, null));
        user.put(KEY_CUSTOMER_NAME, pref.getString(KEY_CUSTOMER_NAME, null));
        user.put(KEY_DL_NUMBER, pref.getString(KEY_DL_NUMBER, null));
        user.put(KEY_CUSTOMER_DOB, pref.getString(KEY_CUSTOMER_DOB, null));
        user.put(KEY_ALTERNATE_NUMBER, pref.getString(KEY_ALTERNATE_NUMBER, null));
        user.put(KEY_COMPLETE_ADDRESS, pref.getString(KEY_COMPLETE_ADDRESS, null));
        user.put(KEY_DRI_CUSTOMER_IMAGE, pref.getString(KEY_DRI_CUSTOMER_IMAGE, null));
        return user;
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        editor.putBoolean(IS_LOGIN, true);
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_LOGIN_TYPE, pref.getString(KEY_LOGIN_TYPE, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));
        user.put(KEY_ABOUT, pref.getString(KEY_ABOUT, null));
        user.put(KEY_IMAGE, pref.getString(KEY_IMAGE, null));
        user.put(KEY_MEMBER, pref.getString(KEY_MEMBER, null));
        user.put(KEY_NATIONALITY, pref.getString(KEY_NATIONALITY, null));



        return user;
    }

    public String getRegistrationId()
    {
        return pref.getString(KEY_FCM, null);
    }

    public void setRegistrationId(String fcmID)
    {
        editor.putString(KEY_FCM, fcmID);
        editor.commit();
    }

    public String getImage()
    {
        return pref.getString(KEY_IMAGE, null);
    }

    public void setImage(String imageURL)
    {
        editor.putString(KEY_IMAGE, imageURL);
        editor.commit();
    }


    public String getImageBitmap()
    {
        return pref.getString(KEY_IMAGE_BITMAP, null);
    }

    public void setImageBitmap(String imageBitmap)
    {
        editor.putString(KEY_IMAGE_BITMAP, imageBitmap);
        editor.commit();
    }


    public String getImageBitmapLicence()
    {
        return pref.getString(KEY_IMAGE_BITMAP_LICENCE, null);
    }

    public void setImageBitmapLicence(String imageBitmap)
    {
        editor.putString(KEY_IMAGE_BITMAP_LICENCE, imageBitmap);
        editor.commit();
    }

    public boolean getLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean getLicenceFilled(){
        return pref.getBoolean(IS_LICENCE_FILLED,false);
    }

    public void setLicenceFilled(boolean licenceFilled) {
        editor.putBoolean(IS_LICENCE_FILLED, licenceFilled);
        editor.commit();
    }


    public String getLicenceID(){
        return pref.getString(KEY_DRI_DETAIL,null);
    }

    public void setLicenceID(String paymentStatus) {
        editor.putString(KEY_DRI_DETAIL, paymentStatus);
        editor.commit();
    }

    public String getPayStatus(){
        return pref.getString(KEY_PAY_STATUS,null);
    }

    public void setPayStatus(String licenceID) {
        editor.putString(KEY_PAY_STATUS, licenceID);
        editor.commit();
    }


    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivithy.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);

    }


    public HashMap<String, String> fetchProfileData() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_LOGIN_TYPE, pref.getString(KEY_LOGIN_TYPE, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_ABOUT, pref.getString(KEY_ABOUT, null));
        user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));
        user.put(KEY_IMAGE, pref.getString(KEY_IMAGE, null));
        user.put(KEY_MEMBER, pref.getString(KEY_MEMBER, null));
        user.put(KEY_NATIONALITY, pref.getString(KEY_NATIONALITY, null));
        return user;
    }
    public  void clear(){

    }


}
