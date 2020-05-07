package com.fmrnz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRawDataForCard {

@SerializedName("NONCE")
@Expose
private String nONCE;
@SerializedName("amnt")
@Expose
private String amnt;

public String getNONCE() {
return nONCE;
}

public void setNONCE(String nONCE) {
this.nONCE = nONCE;
}

public String getAmnt() {
return amnt;
}

public void setAmnt(String amnt) {
this.amnt = amnt;
}}