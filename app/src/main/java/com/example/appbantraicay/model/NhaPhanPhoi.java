package com.example.appbantraicay.model;

public class NhaPhanPhoi {
    private String ID, DiaChi, Email, Ngay, SDT, SoLuong, TenSP, TenNPP;

    public NhaPhanPhoi(String ID, String DiaChi, String Email, String Ngay, String SDT, String SoLuong, String TenSP, String TenNPP) {
        this.ID = ID;
        this.DiaChi = DiaChi;
        this.Email = Email;
        this.Ngay = Ngay;
        this.SDT = SDT;
        this.SoLuong = SoLuong;
        this.TenSP = TenSP;
        this.TenNPP = TenNPP;
    }
    public String getId() {
        return ID;
    }

    public void setId(String id) {
        ID = ID;
    }
    public String getDiaChi() {
        return DiaChi;
    }
    public void getDiaChi(String diachi) {
        DiaChi = DiaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = Email;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = Ngay;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sdt) {
        SDT = SDT;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soluong) {
        SoLuong = SoLuong;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tensp) {
        TenSP = TenSP; }

    public String getTenNPP() {
        return TenNPP;
    }

    public void setTenNPP(String tennpp) {
        TenNPP = TenNPP; }


    public NhaPhanPhoi(){

    }
}