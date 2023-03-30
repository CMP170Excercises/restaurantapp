package com.example.myrestaurant.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myrestaurant.DB.CreateDB;

public class PermissionDAO {
    SQLiteDatabase db;
    public PermissionDAO(Context context){
        CreateDB createDB = new CreateDB(context);
        db = createDB.open();
    }

    public void AddPermission(String perName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_PERMISSION_NAME, perName);
        db.insert(CreateDB.TBL_PERMISSION,null,contentValues);
    }


    @SuppressLint("Range")
    public String SelectPerNameById(int perId){
        String perName = "";
        String query = "SELECT * FROM " + CreateDB.TBL_PERMISSION + " WHERE " + CreateDB.TBL_PERMISSION_ID + " = " + perId;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            perName = cursor.getString(cursor.getColumnIndex(CreateDB.TBL_PERMISSION_NAME));
            cursor.moveToNext();
        }
        return perName;
    }
}
