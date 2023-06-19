package com.example.mcardiology;

import android.graphics.Bitmap;

import java.io.Serializable;

public class UserData  implements Serializable {
  public   String name,email,phone,gender,dob;
public  String weight,height,spo2,bg,bp,sugar;
    public Bitmap ecgimg;

    public UserData(Bitmap ecgimg) {
        this.ecgimg = ecgimg;
    }


    public UserData(String name, String email, String phone, String gender, String dob) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }
}
