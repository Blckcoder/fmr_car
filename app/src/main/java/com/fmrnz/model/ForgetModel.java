package com.fmrnz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eurysinfosystems on 20/04/17.
 */

public class ForgetModel extends BaseModel implements Parcelable {

    private String id;
    private String email;
    private String name;
    private String password;
    private String mobile;
    private String refer_user_id;


    public ForgetModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRefer_user_id() {
        return refer_user_id;
    }

    public void setRefer_user_id(String refer_user_id) {
        this.refer_user_id = refer_user_id;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getFcm_id() {
        return fcm_id;
    }

    public void setFcm_id(String fcm_id) {
        this.fcm_id = fcm_id;
    }

    private String login_type;
    private String fcm_id;






    private ForgetModel(Parcel in) {
        // This order must match the order in writeToParcel()
        id = in.readString();
        email = in.readString();
        name = in.readString();
        password = in.readString();
        mobile = in.readString();
        refer_user_id = in.readString();
        login_type = in.readString();
        fcm_id = in.readString();







    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(password);
        parcel.writeString(mobile);
        parcel.writeString(refer_user_id);
        parcel.writeString(login_type);
        parcel.writeString(fcm_id);







    }

    // Just cut and paste this for now
    public static final Creator<ForgetModel> CREATOR = new Creator<ForgetModel>() {
        public ForgetModel createFromParcel(Parcel in) {
            return new ForgetModel(in);
        }

        public ForgetModel[] newArray(int size) {
            return new ForgetModel[size];
        }
    };
}









