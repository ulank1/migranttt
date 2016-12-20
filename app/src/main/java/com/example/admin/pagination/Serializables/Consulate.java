package com.example.admin.pagination.Serializables;

import java.io.Serializable;

/**
 * Created by Admin on 22.07.2016.
 */
public class Consulate implements Serializable {
    String region,address,phoneNumber;



    public void setRegion(String region) {
        this.region = region;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRegion() {
        return region;
    }

    public String getAddress() {
        return address;
    }
}
