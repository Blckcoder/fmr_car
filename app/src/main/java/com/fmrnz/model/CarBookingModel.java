package com.fmrnz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eurysinfosystems on 20/04/17.
 */

public class CarBookingModel extends BaseModel implements Parcelable {

    private String book_id;
    private String booking_id;
    private String user_id;
    private String licence_id;
    private String car_id;
    private String book_from;
    private String book_to;
    private String drop_location;
    private String status;
    private String start_trip;
    private String end_trip;
    private String fcm_point;


    public CarBookingModel() {
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
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

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_trip() {
        return start_trip;
    }

    public void setStart_trip(String start_trip) {
        this.start_trip = start_trip;
    }

    public String getEnd_trip() {
        return end_trip;
    }

    public void setEnd_trip(String end_trip) {
        this.end_trip = end_trip;
    }






    private CarBookingModel(Parcel in) {
        // This order must match the order in writeToParcel()
        book_id = in.readString();
        booking_id = in.readString();
        user_id = in.readString();
        licence_id = in.readString();
        car_id = in.readString();
        book_from = in.readString();
        book_to = in.readString();
        drop_location = in.readString();
        status = in.readString();
        start_trip = in.readString();
        end_trip = in.readString();
        fcm_point = in.readString();







    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(book_id);
        parcel.writeString(booking_id);
        parcel.writeString(user_id);
        parcel.writeString(licence_id);
        parcel.writeString(car_id);
        parcel.writeString(book_from);
        parcel.writeString(book_to);
        parcel.writeString(drop_location);
        parcel.writeString(status);
        parcel.writeString(start_trip);
        parcel.writeString(end_trip);
        parcel.writeString(fcm_point);






    }

    // Just cut and paste this for now
    public static final Creator<CarBookingModel> CREATOR = new Creator<CarBookingModel>() {
        public CarBookingModel createFromParcel(Parcel in) {
            return new CarBookingModel(in);
        }

        public CarBookingModel[] newArray(int size) {
            return new CarBookingModel[size];
        }
    };
}









