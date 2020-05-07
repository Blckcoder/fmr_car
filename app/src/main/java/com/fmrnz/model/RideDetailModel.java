package com.fmrnz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 10/11/2017.
 */

public class RideDetailModel extends BaseModel implements Parcelable {

    private String book_id;
    private String booking_id;
    private String user_id;
    private String licence_id;
    private String status;
    private String start_trip;
    private String end_trip;
    private String car_id;
    private String book_from;
    private String book_to;
    private String drop_location;
    private String car_name;
    private String car_model;
    private String car_lat;
    private String car_long;
    private String fuel_type;
    private String car_type;
    private String car_seater;
    private String hourly_price;
    private String daily_price;
    private String weekly_price;
    private String vehicle_type;
    private String vehicle_make;
    private String vehicle_model;
    private String consumption;
    private String registration_year;
    private String doors;
    private String odometer;
    private String drive_train;
    private String feature;
    private String description;
    private String car_image1;
    private String car_image2;
    private String car_image3;
    private String car_image4;
    private String car_image5;
    private String car_image6;
    private String car_image7;
    private String car_image8;
    private String car_image9;
    private String car_image10;
    private String car_owner_id;
    private String drop_location1;
    private String drop_location2;
    private String owner_id;
    private String owner_name;
    private String owner_type;
    private String owner_image;
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
    private String image;
    private String phone_number;
    private String address;
    private String member_since;
    private String email;
    private String pickup_location;
    private String book_amount;


    public String getBook_amount() {
        return book_amount;
    }

    public void setBook_amount(String book_amount) {
        this.book_amount = book_amount;
    }

    public RideDetailModel() {

    }


    protected RideDetailModel(Parcel in) {
        book_id = in.readString();
        booking_id = in.readString();
        user_id = in.readString();
        licence_id = in.readString();
        status = in.readString();
        start_trip = in.readString();
        end_trip = in.readString();
        car_id = in.readString();
        book_from = in.readString();
        book_to = in.readString();
        drop_location = in.readString();
        car_name = in.readString();
        car_model = in.readString();
        car_lat = in.readString();
        car_long = in.readString();
        fuel_type = in.readString();
        car_type = in.readString();
        car_seater = in.readString();
        hourly_price = in.readString();
        daily_price = in.readString();
        weekly_price = in.readString();
        vehicle_type = in.readString();
        vehicle_make = in.readString();
        vehicle_model = in.readString();
        consumption = in.readString();
        registration_year = in.readString();
        doors = in.readString();
        odometer = in.readString();
        drive_train = in.readString();
        feature = in.readString();
        description = in.readString();
        car_image1 = in.readString();
        car_image2 = in.readString();
        car_image3 = in.readString();
        car_image4 = in.readString();
        car_image5 = in.readString();
        car_image6 = in.readString();
        car_image7 = in.readString();
        car_image8 = in.readString();
        car_image9 = in.readString();
        car_image10 = in.readString();
        car_owner_id = in.readString();
        drop_location1 = in.readString();
        drop_location2 = in.readString();
        owner_id = in.readString();
        owner_name = in.readString();
        owner_type = in.readString();
        owner_image = in.readString();
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
        image = in.readString();
        phone_number = in.readString();
        address = in.readString();
        member_since = in.readString();
        email = in.readString();
        pickup_location = in.readString();
        book_amount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(book_id);
        dest.writeString(booking_id);
        dest.writeString(user_id);
        dest.writeString(licence_id);
        dest.writeString(status);
        dest.writeString(start_trip);
        dest.writeString(end_trip);
        dest.writeString(car_id);
        dest.writeString(book_from);
        dest.writeString(book_to);
        dest.writeString(drop_location);
        dest.writeString(car_name);
        dest.writeString(car_model);
        dest.writeString(car_lat);
        dest.writeString(car_long);
        dest.writeString(fuel_type);
        dest.writeString(car_type);
        dest.writeString(car_seater);
        dest.writeString(hourly_price);
        dest.writeString(daily_price);
        dest.writeString(weekly_price);
        dest.writeString(vehicle_type);
        dest.writeString(vehicle_make);
        dest.writeString(vehicle_model);
        dest.writeString(consumption);
        dest.writeString(registration_year);
        dest.writeString(doors);
        dest.writeString(odometer);
        dest.writeString(drive_train);
        dest.writeString(feature);
        dest.writeString(description);
        dest.writeString(car_image1);
        dest.writeString(car_image2);
        dest.writeString(car_image3);
        dest.writeString(car_image4);
        dest.writeString(car_image5);
        dest.writeString(car_image6);
        dest.writeString(car_image7);
        dest.writeString(car_image8);
        dest.writeString(car_image9);
        dest.writeString(car_image10);
        dest.writeString(car_owner_id);
        dest.writeString(drop_location1);
        dest.writeString(drop_location2);
        dest.writeString(owner_id);
        dest.writeString(owner_name);
        dest.writeString(owner_type);
        dest.writeString(owner_image);
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
        dest.writeString(image);
        dest.writeString(phone_number);
        dest.writeString(address);
        dest.writeString(member_since);
        dest.writeString(email);
        dest.writeString(pickup_location);
        dest.writeString(book_amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RideDetailModel> CREATOR = new Creator<RideDetailModel>() {
        @Override
        public RideDetailModel createFromParcel(Parcel in) {
            return new RideDetailModel(in);
        }

        @Override
        public RideDetailModel[] newArray(int size) {
            return new RideDetailModel[size];
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

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_lat() {
        return car_lat;
    }

    public void setCar_lat(String car_lat) {
        this.car_lat = car_lat;
    }

    public String getCar_long() {
        return car_long;
    }

    public void setCar_long(String car_long) {
        this.car_long = car_long;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_seater() {
        return car_seater;
    }

    public void setCar_seater(String car_seater) {
        this.car_seater = car_seater;
    }

    public String getHourly_price() {
        return hourly_price;
    }

    public void setHourly_price(String hourly_price) {
        this.hourly_price = hourly_price;
    }

    public String getDaily_price() {
        return daily_price;
    }

    public void setDaily_price(String daily_price) {
        this.daily_price = daily_price;
    }

    public String getWeekly_price() {
        return weekly_price;
    }

    public void setWeekly_price(String weekly_price) {
        this.weekly_price = weekly_price;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(String vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getRegistration_year() {
        return registration_year;
    }

    public void setRegistration_year(String registration_year) {
        this.registration_year = registration_year;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getDrive_train() {
        return drive_train;
    }

    public void setDrive_train(String drive_train) {
        this.drive_train = drive_train;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCar_image1() {
        return car_image1;
    }

    public void setCar_image1(String car_image1) {
        this.car_image1 = car_image1;
    }

    public String getCar_image2() {
        return car_image2;
    }

    public void setCar_image2(String car_image2) {
        this.car_image2 = car_image2;
    }

    public String getCar_image3() {
        return car_image3;
    }

    public void setCar_image3(String car_image3) {
        this.car_image3 = car_image3;
    }

    public String getCar_image4() {
        return car_image4;
    }

    public void setCar_image4(String car_image4) {
        this.car_image4 = car_image4;
    }

    public String getCar_image5() {
        return car_image5;
    }

    public void setCar_image5(String car_image5) {
        this.car_image5 = car_image5;
    }

    public String getCar_image6() {
        return car_image6;
    }

    public void setCar_image6(String car_image6) {
        this.car_image6 = car_image6;
    }

    public String getCar_image7() {
        return car_image7;
    }

    public void setCar_image7(String car_image7) {
        this.car_image7 = car_image7;
    }

    public String getCar_image8() {
        return car_image8;
    }

    public void setCar_image8(String car_image8) {
        this.car_image8 = car_image8;
    }

    public String getCar_image9() {
        return car_image9;
    }

    public void setCar_image9(String car_image9) {
        this.car_image9 = car_image9;
    }

    public String getCar_image10() {
        return car_image10;
    }

    public void setCar_image10(String car_image10) {
        this.car_image10 = car_image10;
    }

    public String getCar_owner_id() {
        return car_owner_id;
    }

    public void setCar_owner_id(String car_owner_id) {
        this.car_owner_id = car_owner_id;
    }

    public String getDrop_location1() {
        return drop_location1;
    }

    public void setDrop_location1(String drop_location1) {
        this.drop_location1 = drop_location1;
    }

    public String getDrop_location2() {
        return drop_location2;
    }

    public void setDrop_location2(String drop_location2) {
        this.drop_location2 = drop_location2;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_type() {
        return owner_type;
    }

    public void setOwner_type(String owner_type) {
        this.owner_type = owner_type;
    }

    public String getOwner_image() {
        return owner_image;
    }

    public void setOwner_image(String owner_image) {
        this.owner_image = owner_image;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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


    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getMember_since() {
        return member_since;
    }

    public void setMember_since(String member_since) {
        this.member_since = member_since;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }



}












