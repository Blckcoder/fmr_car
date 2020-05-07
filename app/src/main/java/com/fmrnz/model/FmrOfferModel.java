package com.fmrnz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eurysinfosystems on 20/04/17.
 */

public class FmrOfferModel extends BaseModel implements Parcelable {

    private String offer_id;
    private String offer_image;


    public FmrOfferModel() {
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getOffer_image() {
        return offer_image;
    }

    public void setOffer_image(String offer_image) {
        this.offer_image = offer_image;
    }






    private FmrOfferModel(Parcel in) {
        // This order must match the order in writeToParcel()
        offer_id = in.readString();
        offer_image = in.readString();









    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(offer_id);
        parcel.writeString(offer_image);








    }

    // Just cut and paste this for now
    public static final Creator<FmrOfferModel> CREATOR = new Creator<FmrOfferModel>() {
        public FmrOfferModel createFromParcel(Parcel in) {
            return new FmrOfferModel(in);
        }

        public FmrOfferModel[] newArray(int size) {
            return new FmrOfferModel[size];
        }
    };
}









