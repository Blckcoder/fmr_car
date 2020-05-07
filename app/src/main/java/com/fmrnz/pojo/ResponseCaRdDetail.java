package com.fmrnz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCaRdDetail {

@SerializedName("txnid")
@Expose
private String txnid;

public String getTxnid() {
return txnid;
}

public void setTxnid(String txnid) {
this.txnid = txnid;
}

}