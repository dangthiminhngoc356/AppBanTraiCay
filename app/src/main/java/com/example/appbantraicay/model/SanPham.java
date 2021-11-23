package com.example.appbantraicay.model;

public class SanPham {
    private String id, Ten, MoTa, DonGia, HinhAnh;

    public SanPham(){

    }

    public SanPham(String Ten, String MoTa, String DonGia, String HinhAnh, String id) {
        this.id = id;
        this.Ten = Ten;
        this.MoTa = MoTa;
        this.DonGia = DonGia;
        this.HinhAnh = HinhAnh;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String Ten) {
        this.Ten = Ten;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public String getDonGia() {
        return DonGia;
    }

    public void setDonGia(String DonGia) {
        this.HinhAnh = DonGia;
    }
}
