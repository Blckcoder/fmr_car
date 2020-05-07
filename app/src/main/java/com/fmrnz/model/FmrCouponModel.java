package com.fmrnz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eurysinfosystems on 20/04/17.
 */

public class FmrCouponModel extends BaseModel implements Parcelable {

    private String id;
    private String coupon_name;
    private String coupon_percent;
    private String max_discount;

    public FmrCouponModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getCoupon_percent() {
        return coupon_percent;
    }

    public void setCoupon_percent(String coupon_percent) {
        this.coupon_percent = coupon_percent;
    }

    public String getMax_discount() {
        return max_discount;
    }

    public void setMax_discount(String max_discount) {
        this.max_discount = max_discount;
    }




    private FmrCouponModel(Parcel in) {
        // This order must match the order in writeToParcel()
        id = in.readString();
        coupon_name = in.readString();
        coupon_percent = in.readString();
        max_discount = in.readString();








    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(coupon_name);
        parcel.writeString(coupon_percent);
        parcel.writeString(max_discount);







    }

    // Just cut and paste this for now
    public static final Creator<FmrCouponModel> CREATOR = new Creator<FmrCouponModel>() {
        public FmrCouponModel createFromParcel(Parcel in) {
            return new FmrCouponModel(in);
        }

        public FmrCouponModel[] newArray(int size) {
            return new FmrCouponModel[size];
        }
    };
}









