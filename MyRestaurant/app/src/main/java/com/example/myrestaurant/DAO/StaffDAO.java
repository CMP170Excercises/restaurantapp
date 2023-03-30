package com.example.myrestaurant.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myrestaurant.DB.CreateDB;
import com.example.myrestaurant.Model.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    SQLiteDatabase db;
    public StaffDAO(Context context) {
        CreateDB createDB = new CreateDB(context);
        db = createDB.open();
    }

    public long AddStaff(Staff staff){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_STAFF_FULLNAME, staff.getStaffFullname());
        contentValues.put(CreateDB.TBL_STAFF_USERNAME, staff.getUsername());
        contentValues.put(CreateDB.TBL_STAFF_PASSWORD, staff.getPassword());
        contentValues.put(CreateDB.TBL_STAFF_EMAIL, staff.getEmail());
        contentValues.put(CreateDB.TBL_STAFF_PHONE, staff.getPhone());
        contentValues.put(CreateDB.TBL_STAFF_GENDER, staff.getGender());
        contentValues.put(CreateDB.TBL_STAFF_DOB, staff.getDob());
        contentValues.put(CreateDB.TBL_STAFF_PERMISSIONID, staff.getPermissionId());

        long ktra = db.insert(CreateDB.TBL_STAFF,null, contentValues);
        return ktra;
    }

    public long EditStaff(Staff staff, int staffid){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_STAFF_FULLNAME, staff.getStaffFullname());
        contentValues.put(CreateDB.TBL_STAFF_USERNAME, staff.getUsername());
        contentValues.put(CreateDB.TBL_STAFF_PASSWORD, staff.getPassword());
        contentValues.put(CreateDB.TBL_STAFF_EMAIL, staff.getEmail());
        contentValues.put(CreateDB.TBL_STAFF_PHONE, staff.getPhone());
        contentValues.put(CreateDB.TBL_STAFF_GENDER, staff.getGender());
        contentValues.put(CreateDB.TBL_STAFF_DOB, staff.getDob());
        contentValues.put(CreateDB.TBL_STAFF_PERMISSIONID, staff.getPermissionId());

        long ktra = db.update(CreateDB.TBL_STAFF,contentValues,
                CreateDB.TBL_STAFF_ID + " = " + staffid,null);
        return ktra;
    }

    @SuppressLint("Range")
    public int CheckLogin(String username, String password){
        String query = "SELECT * FROM " + CreateDB.TBL_STAFF + " WHERE "
                + CreateDB.TBL_STAFF_USERNAME + " = '" + username + "' AND " + CreateDB.TBL_STAFF_PASSWORD + " = '" + password + "'";
        int staffid = 0;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            staffid = cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_STAFF_ID)) ;
            cursor.moveToNext();
        }
        return staffid;
    }

    public boolean CheckExist(){
        String query = "SELECT * FROM " + CreateDB.TBL_STAFF;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("Range")
    public List<Staff> LayDSNV(){
        List<Staff> nhanVienDTOS = new ArrayList<Staff>();
        String query = "SELECT * FROM "+ CreateDB.TBL_STAFF;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Staff staff = new Staff();
            staff.setStaffFullname(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_FULLNAME)));
            staff.setEmail(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_EMAIL)));
            staff.setGender(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_GENDER)));
            staff.setDob(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_DOB)));
            staff.setPhone(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_PHONE)));
            staff.setUsername(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_USERNAME)));
            staff.setPassword(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_PASSWORD)));
            staff.setStaffId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_STAFF_ID)));
            staff.setPermissionId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_STAFF_PERMISSIONID)));

            nhanVienDTOS.add(staff);
            cursor.moveToNext();
        }
        return nhanVienDTOS;
    }

    public boolean DeleteStaff(int staffid){
        long ktra = db.delete(CreateDB.TBL_STAFF, CreateDB.TBL_STAFF_ID + " = " + staffid
                , null);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("Range")
    public Staff SelectStaffById(int staffid){
        Staff staff = new Staff();
        String query = "SELECT * FROM " + CreateDB.TBL_STAFF + " WHERE " + CreateDB.TBL_STAFF_ID + " = " + staffid;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            staff.setStaffFullname(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_FULLNAME)));
            staff.setEmail(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_EMAIL)));
            staff.setGender(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_GENDER)));
            staff.setDob(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_DOB)));
            staff.setPhone(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_PHONE)));
            staff.setUsername(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_USERNAME)));
            staff.setPassword(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_STAFF_PASSWORD)));
            staff.setStaffId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_STAFF_ID)));
            staff.setPermissionId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_STAFF_PERMISSIONID)));

            cursor.moveToNext();
        }
        return staff;
    }

    @SuppressLint("Range")
    public int SelectStaffPermission(int staffid){
        int perid = 0;
        String query = "SELECT * FROM "+ CreateDB.TBL_STAFF + " WHERE " + CreateDB.TBL_STAFF_ID + " = " + staffid;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            perid = cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_STAFF_PERMISSIONID));

            cursor.moveToNext();
        }
        return perid;
    }
}
