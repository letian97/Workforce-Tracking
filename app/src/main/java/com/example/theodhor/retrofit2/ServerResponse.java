package com.example.theodhor.retrofit2;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dori on 12/28/2016.
 */

public class ServerResponse implements Serializable{
    @SerializedName("returned_name")
    private String name;
    @SerializedName("returned_site")
    private String site;
    @SerializedName("message")
    private String message;
    @SerializedName("error_code")
    private int errorCode;
    private int status = 1;
    private String error;

    public ServerResponse(String name, String site, String message, int errorCode, int status, String error){
        this.name = name;
        this.site = site;
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }





}