package com.example.appbantraicay.model;

public class Users {
    private String name, password, phone, hinhanh;

    public Users(){

    }

    public Users(String name, String password, String phone, String hinhanh) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.hinhanh = hinhanh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String gethinhanh() {
        return hinhanh;
    }

    public void sethinhanh(String phone) {
        this.hinhanh = hinhanh;
    }
}
