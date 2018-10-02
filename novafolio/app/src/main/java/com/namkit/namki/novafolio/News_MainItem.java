package com.namkit.namki.novafolio;

/**
 * Created by namki on 2018-03-10.
 */

public class News_MainItem {

    int avatar;
    String tenSinhVien;
    String sdtSinhVien;


    public News_MainItem() {

    }

    public News_MainItem(int avatar, String tenSinhVien, String sdtSinhVien) {
        this.avatar = avatar;
        this.tenSinhVien = tenSinhVien;
        this.sdtSinhVien = sdtSinhVien;

    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getTenSinhVien() {
        return tenSinhVien;
    }

    public void setTenSinhVien(String tenSinhVien) {
        this.tenSinhVien = tenSinhVien;
    }

    public String getSdtSinhVien() {
        return sdtSinhVien;
    }

    public void setSdtSinhVien(String sdtSinhVien) {
        this.sdtSinhVien = sdtSinhVien;
    }
}

