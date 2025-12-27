package com.example.ddcn;

public class Avatar {
    private String tenHienThi;
    private String tenFileAnh;
    private int resId;

    public Avatar(String tenHienThi, String tenFileAnh, int resId) {
        this.tenHienThi = tenHienThi;
        this.tenFileAnh = tenFileAnh;
        this.resId = resId;
    }

    public String getTenHienThi() { return tenHienThi; }
    public String getTenFileAnh() { return tenFileAnh; }
    public int getResId() { return resId; }
    
    @Override
    public String toString() {
        return tenHienThi;
    }
}