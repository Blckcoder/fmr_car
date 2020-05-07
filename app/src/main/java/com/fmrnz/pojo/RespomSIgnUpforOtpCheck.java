
package com.fmrnz.pojo;


import com.google.gson.annotations.SerializedName;


public class RespomSIgnUpforOtpCheck {

    @SerializedName("response_code")
    private String mResponseCode;
    @SerializedName("response_message")
    private String mResponseMessage;
    @SerializedName("result")
    private String mResponsResult;

    public String getResponseCode() {
        return mResponseCode;
    }
    public String getResponseResult() {
        return mResponsResult;
    }

    public void setResponseCode(String responseCode) {
        mResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        mResponseMessage = responseMessage;
    }

}
