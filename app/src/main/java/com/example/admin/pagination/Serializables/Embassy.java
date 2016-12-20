package com.example.admin.pagination.Serializables;

import java.io.Serializable;

/**
 * Created by Admin on 21.07.2016.
 */
public class Embassy implements Serializable {
    String country, region,email,fax,phoneNumber,site,id,image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getFax() {
        return fax;
    }

    public String getId() {
        return id;
    }

    public String getRegion() {
        return region;
    }

    public String getSite() {
        return site;
    }

}
