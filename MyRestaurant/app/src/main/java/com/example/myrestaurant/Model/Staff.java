package com.example.myrestaurant.Model;

import static android.provider.Telephony.Carriers.PASSWORD;

public class Staff {
    String StaffFullname, Username, Password, Email, Phone, Gender, Dob;
    int StaffId, PermissionId;

    public int getPermissionId() {
        return PermissionId;
    }

    public void setPermissionId(int PermissionId) {
        this.PermissionId = PermissionId;
    }

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int StaffId) {
        this.StaffId = StaffId;
    }

    public String getStaffFullname() {
        return StaffFullname;
    }

    public void setStaffFullname(String StaffFullname) {
        this.StaffFullname = StaffFullname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String Dob) {
        this.Dob = Dob;
    }
}
