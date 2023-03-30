package com.example.myrestaurant.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myrestaurant.DB.CreateDB;
import com.example.myrestaurant.Model.DishCategory;

import java.util.ArrayList;
import java.util.List;

public class DishCategoryDAO {
    SQLiteDatabase db;
    public DishCategoryDAO(Context context){
        CreateDB createDB = new CreateDB(context);
        db = createDB.open();
    }

    public boolean AddCategory(DishCategory dishCategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_DISHCATEGORY_NAME, dishCategory.getCategoryName());
        contentValues.put(CreateDB.TBL_DISHCATEGORY_IMAGE, dishCategory.getImage());
        long ktra = db.insert(CreateDB.TBL_DISHCATEGORY, null, contentValues);

        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DeleteCategory(int cateId){
        long ktra = db.delete(CreateDB.TBL_DISHCATEGORY, CreateDB.TBL_DISHCATEGORY_ID + " = " + cateId, null);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EditCategory(DishCategory dishCategory, int cateId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_DISHCATEGORY_NAME, dishCategory.getCategoryName());
        contentValues.put(CreateDB.TBL_DISHCATEGORY_IMAGE, dishCategory.getImage());
        long ktra = db.update(CreateDB.TBL_DISHCATEGORY, contentValues, CreateDB.TBL_DISHCATEGORY_ID + " = " + cateId,null);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("Range")
    public List<DishCategory> SelectCategoryList() {
        List<DishCategory> dishCategoryList = new ArrayList<DishCategory>();
        String query = "SELECT * FROM " + CreateDB.TBL_DISHCATEGORY;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DishCategory dishCategory = new DishCategory();
            dishCategory.setCategoryId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_DISHCATEGORY_ID)));
            dishCategory.setCategoryName(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_DISHCATEGORY_NAME)));
            dishCategory.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDB.TBL_DISHCATEGORY_IMAGE)));
            dishCategoryList.add(dishCategory);

            cursor.moveToNext();
        }
        return dishCategoryList;
    }

    @SuppressLint("Range")
    public DishCategory SelectCategoryById(int cateId) {
        DishCategory dishCategory = new DishCategory();
        String query = "SELECT * FROM " + CreateDB.TBL_DISHCATEGORY + " WHERE " + CreateDB.TBL_DISHCATEGORY_ID + " = " + cateId;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            dishCategory.setCategoryId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_DISHCATEGORY_ID)));
            dishCategory.setCategoryName(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_DISHCATEGORY_NAME)));
            dishCategory.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDB.TBL_DISHCATEGORY_IMAGE)));

            cursor.moveToNext();
        }
        return dishCategory;
    }
}
