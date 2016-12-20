package com.example.admin.pagination.Serializables;

/**
 * Created by ulan on 11/9/16.
 */
public class Diaspora {
    String address,city,email,manager,number,place;

    public String getManager() {
        return manager;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getPlace() {
        return place;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
