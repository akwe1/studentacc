
package com.example.personalaccount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tutor {

    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("phone_number")
    @Expose
    private Long phoneNumber;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
