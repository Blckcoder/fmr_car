
package com.fmrnz.pojo;

import com.google.gson.annotations.SerializedName;


public class ResponseForShareAndEarn {

    @SerializedName("response_code")
    private String mResponseCode;
    @SerializedName("response_message")
    private String mResponseMessage;
    @SerializedName("result")
    private String mResult;

    public String getResponseCode() {
        return mResponseCode;
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

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

}
