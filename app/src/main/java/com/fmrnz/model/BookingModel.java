package com.fmrnz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eurysinfosystems on 20/04/17.
 */

public class BookingModel implements Parcelable {

    private String book_id;
    private String user_id;
    private String licence_id;
    private String car_id;
    private String book_from;
    private String book_to;
    private String booking_id;
    private String drop_location;
    private String end_trip;
    private String pay_amount;
    private String pay_id;
    private String pay_status;
    private String pickup_location;
    private String start_trip;
    private String status;


    public BookingModel() {
    }

    protected BookingModel(Parcel in) {
        book_id = in.readString();
        user_id = in.readString();
        licence_id = in.readString();
        car_id = in.readString();
        book_from = in.readString();
        book_to = in.readString();
        booking_id = in.readString();
        drop_location = in.readString();
        end_trip = in.readString();
        pay_amount = in.readString();
        pay_id = in.readString();
        pay_status = in.readString();
        pickup_location = in.readString();
        start_trip = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(book_id);
        dest.writeString(user_id);
        dest.writeString(licence_id);
        dest.writeString(car_id);
        dest.writeString(book_from);
        dest.writeString(book_to);
        dest.writeString(booking_id);
        dest.writeString(drop_location);
        dest.writeString(end_trip);
        dest.writeString(pay_amount);
        dest.writeString(pay_id);
        dest.writeString(pay_status);
        dest.writeString(pickup_location);
        dest.writeString(start_trip);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingModel> CREATOR = new Creator<BookingModel>() {
        @Override
        public BookingModel createFromParcel(Parcel in) {
            return new BookingModel(in);
        }

        @Override
        public BookingModel[] newArray(int size) {
            return new BookingModel[size];
        }
    };

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLicence_id() {
        return licence_id;
    }

    public void setLicence_id(String licence_id) {
        this.licence_id = licence_id;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getBook_from() {
        return book_from;
    }

    public void setBook_from(String book_from) {
        this.book_from = book_from;
    }

    public String getBook_to() {
        return book_to;
    }

    public void setBook_to(String book_to) {
        this.book_to = book_to;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public String getEnd_trip() {
        return end_trip;
    }

    public void setEnd_trip(String end_trip) {
        this.end_trip = end_trip;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getStart_trip() {
        return start_trip;
    }

    public void setStart_trip(String start_trip) {
        this.start_trip = start_trip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}









