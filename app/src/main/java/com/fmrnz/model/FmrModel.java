package com.fmrnz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eurysinfosystems on 20/04/17.
 */

public class FmrModel extends BaseModel implements Parcelable {

    private String point_id;
    private String user_id;
    private String invite_id;
    private String points;

    public FmrModel() {
    }

    public String getPoint_id() {
        return point_id;
    }

    public void setPoint_id(String point_id) {
        this.point_id = point_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getInvite_id() {
        return invite_id;
    }

    public void setInvite_id(String invite_id) {
        this.invite_id = invite_id;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }




    private FmrModel(Parcel in) {
        // This order must match the order in writeToParcel()
        point_id = in.readString();
        user_id = in.readString();
        invite_id = in.readString();
        points = in.readString();








    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(point_id);
        parcel.writeString(user_id);
        parcel.writeString(invite_id);
        parcel.writeString(points);







    }

    // Just cut and paste this for now
    public static final Creator<FmrModel> CREATOR = new Creator<FmrModel>() {
        public FmrModel createFromParcel(Parcel in) {
            return new FmrModel(in);
        }

        public FmrModel[] newArray(int size) {
            return new FmrModel[size];
        }
    };
}









