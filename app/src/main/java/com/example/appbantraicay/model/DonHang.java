package com.example.appbantraicay.model;

public class DonHang {
    private String Ten, SDT, Diachi, TenSP, SoLuong, DonGia, ID;

    public DonHang(){

    }

    public DonHang(String Ten, String SDT, String Diachi, String tenSP, String SoLuong, String DonGia, String ID) {
        this.Ten = Ten;
        this.SDT = SDT;
        this.Diachi = Diachi;
        this.TenSP = tenSP;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
        this.ID = ID;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        this.Ten = ten;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String Diachi) {
        this.Diachi = Diachi;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        this.SoLuong = soLuong;
    }

    public String getDonGia() {
        return DonGia;
    }

    public void setDonGia(String DonGia) { this.DonGia = DonGia; }

    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

}
