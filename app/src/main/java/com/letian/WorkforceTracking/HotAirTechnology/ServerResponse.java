package com.letian.WorkforceTracking.HotAirTechnology;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ServerResponse implements Serializable{
//    @SerializedName("returned_name")
//    private String name;
//    @SerializedName("returned_site")
//    private String site;
    @SerializedName("message")
    private String message;
    @SerializedName("response_code")
    private int errorCode;
//    private int status = 1;
//    private String error;

    public ServerResponse(String message, int errorCode){
//        this.name = name;
//        this.site = site;
        this.message = message;
        this.errorCode = errorCode;
//        this.status = status;
//        this.error = error;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSite() {
//        return site;
//    }
//
//    public void setSite(String site) {
//        this.site = site;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

//    public String getError() {
//        return error;
//    }
//
//    public void setError(String error) {
//        this.error = error;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }





}