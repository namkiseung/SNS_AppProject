package com.namkit.namki.novafolio.Wakeup;

import android.widget.ImageView;

/**
 * Created by namki on 2018-03-07.
 */

public class Wakeup_Item {

    String name;
    String mobile;
    int age;
    int resId;

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    ImageView img;

    public Wakeup_Item(int resId) {
        this.resId = resId;
    }
    public Wakeup_Item(ImageView img) {
        this.img = img;
    }


    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
   /* public Wakeup_Item(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public Wakeup_Item(String name, String mobile, int age, int resId) {
        this.name = name;
        this.mobile = mobile;
        this.age = age;
        this.resId = resId;
    }*/


   /* public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/
}
