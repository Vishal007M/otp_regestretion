package com.vsl.otpregestretion.model;

public class OtpModel {

    private String Status;
    private String Details;


    public OtpModel(String status, String details) {
        Status = status;
        Details = details;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }
}
