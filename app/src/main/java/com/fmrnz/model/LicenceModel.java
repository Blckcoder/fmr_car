package com.fmrnz.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LicenceModel implements Parcelable{

    private String driving_detail_id;
    private String dl_country;
    private String dl_country_issue;
    private String dl_issue_date;
    private String dlexpiry_date;
    private String customer_name;
    private String dl_number;
    private String customer_dob;
    private String alternate_number;
    private String complete_address;
    private String user_id;
    private String licenceImage;

    public String getLicenceImage() {
        return licenceImage;
    }

    public void setLicenceImage(String licenceImage) {
        this.licenceImage = licenceImage;
    }

    public LicenceModel() {
    }

    protected LicenceModel(Parcel in) {
        driving_detail_id = in.readString();
        dl_country = in.readString();
        dl_country_issue = in.readString();
        dl_issue_date = in.readString();
        dlexpiry_date = in.readString();
        customer_name = in.readString();
        dl_number = in.readString();
        customer_dob = in.readString();
        alternate_number = in.readString();
        complete_address = in.readString();
        user_id = in.readString();
        licenceImage = in.readString();
    }

    public static final Creator<LicenceModel> CREATOR = new Creator<LicenceModel>() {
        @Override
        public LicenceModel createFromParcel(Parcel in) {
            return new LicenceModel(in);
        }

        @Override
        public LicenceModel[] newArray(int size) {
            return new LicenceModel[size];
        }
    };

    public String getDriving_detail_id() {
        return driving_detail_id;
    }

    public void setDriving_detail_id(String driving_detail_id) {
        this.driving_detail_id = driving_detail_id;
    }

    public String getDl_country() {
        return dl_country;
    }

    public void setDl_country(String dl_country) {
        this.dl_country = dl_country;
    }

    public String getDl_country_issue() {
        return dl_country_issue;
    }

    public void setDl_country_issue(String dl_country_issue) {
        this.dl_country_issue = dl_country_issue;
    }

    public String getDl_issue_date() {
        return dl_issue_date;
    }

    public void setDl_issue_date(String dl_issue_date) {
        this.dl_issue_date = dl_issue_date;
    }

    public String getDlexpiry_date() {
        return dlexpiry_date;
    }

    public void setDlexpiry_date(String dlexpiry_date) {
        this.dlexpiry_date = dlexpiry_date;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDl_number() {
        return dl_number;
    }

    public void setDl_number(String dl_number) {
        this.dl_number = dl_number;
    }

    public String getCustomer_dob() {
        return customer_dob;
    }

    public void setCustomer_dob(String customer_dob) {
        this.customer_dob = customer_dob;
    }

    public String getAlternate_number() {
        return alternate_number;
    }

    public void setAlternate_number(String alternate_number) {
        this.alternate_number = alternate_number;
    }

    public String getComplete_address() {
        return complete_address;
    }

    public void setComplete_address(String complete_address) {
        this.complete_address = complete_address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(driving_detail_id);
        dest.writeString(dl_country);
        dest.writeString(dl_country_issue);
        dest.writeString(dl_issue_date);
        dest.writeString(dlexpiry_date);
        dest.writeString(customer_name);
        dest.writeString(dl_number);
        dest.writeString(customer_dob);
        dest.writeString(alternate_number);
        dest.writeString(complete_address);
        dest.writeString(user_id);
        dest.writeString(licenceImage);
    }
}
