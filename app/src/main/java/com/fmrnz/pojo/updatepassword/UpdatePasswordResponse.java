
package com.fmrnz.pojo.updatepassword;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class UpdatePasswordResponse {

    @SerializedName("email")
    private List<Email> mEmail;
    @SerializedName("response_code")
    private String mResponseCode;
    @SerializedName("response_message")
    private String mResponseMessage;
    @SerializedName("result")
    private String mResult;

    public List<Email> getEmail() {
        return mEmail;
    }

    public void setEmail(List<Email> email) {
        mEmail = email;
    }

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
