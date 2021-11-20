package com.example.appbantraicay;

public class nhaphanphoi {
    private String NPP;
    private String DiaChi;
    private String Email;
    private String SDT;

    private String Ngay;
    private String SoLuong;
    private String TenSP;
    private String TenNPP;

    public nhaphanphoi(String diaChi, String email, String SDT, String ngay, String soLuong, String tenSP, String tenNPP) {

        DiaChi = diaChi;
        Email = email;
        this.SDT = SDT;
        Ngay = ngay;
        SoLuong = soLuong;
        TenSP = tenSP;
        TenNPP = tenNPP;
    }



    public String getNPP() {
        return NPP;
    }

    public void setNPP(String NPP) {
        this.NPP = NPP;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getTenNPP() {
        return TenNPP;
    }

    public void setTenNPP(String tenNPP) {
        TenNPP = tenNPP;
    }

    public nhaphanphoi() {
    }

    @Override
    public String toString() {
        return "nhaphanphoi{" +
                "NPP='" + NPP + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", Email='" + Email + '\'' +
                ", SDT='" + SDT + '\'' +
                ", Ngay='" + Ngay + '\'' +
                ", SoLuong='" + SoLuong + '\'' +
                ", TenSP='" + TenSP + '\'' +
                ", TenNPP='" + TenNPP + '\'' +
                '}';
    }
}
