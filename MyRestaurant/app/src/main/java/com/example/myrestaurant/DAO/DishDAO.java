package com.example.myrestaurant.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myrestaurant.DB.CreateDB;
import com.example.myrestaurant.Model.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishDAO {
    SQLiteDatabase db;
    public DishDAO(Context context){
        CreateDB createDB = new CreateDB(context);
        db = createDB.open();
    }

    public boolean AddDish(Dish dish){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_DISH_CATEGORYID, dish.getCategoryId());
        contentValues.put(CreateDB.TBL_DISH_NAME, dish.getDishName());
        contentValues.put(CreateDB.TBL_DISH_PRICE, dish.getPrice());
        contentValues.put(CreateDB.TBL_DISH_IMAGE, dish.getImage());
        contentValues.put(CreateDB.TBL_DISH_STATE, "true");

        long ktra = db.insert(CreateDB.TBL_DISH, null, contentValues);

        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DeleteDish(int dishId){
        long ktra = db.delete(CreateDB.TBL_DISH, CreateDB.TBL_DISH_ID + " = " + dishId
                , null);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EditDish(Dish dish, int dishId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_DISH_CATEGORYID, dish.getCategoryId());
        contentValues.put(CreateDB.TBL_DISH_NAME, dish.getDishName());
        contentValues.put(CreateDB.TBL_DISH_PRICE, dish.getPrice());
        contentValues.put(CreateDB.TBL_DISH_IMAGE, dish.getImage());
        contentValues.put(CreateDB.TBL_DISH_STATE, dish.getState());

        long ktra = db.update(CreateDB.TBL_DISH, contentValues,
                CreateDB.TBL_DISH_ID + " = " + dishId,null);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("Range")
    public List<Dish> SelectDishListByCategory(int cateId){
        List<Dish> dishList = new ArrayList<Dish>();
        String query = "SELECT * FROM " + CreateDB.TBL_DISH + " WHERE " + CreateDB.TBL_DISH_CATEGORYID + " = '" + cateId + "' ";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Dish dish = new Dish();
            dish.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDB.TBL_DISH_IMAGE)));
            dish.setDishName(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_DISH_NAME)));
            dish.setCategoryId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_DISH_CATEGORYID)));
            dish.setDishId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_DISH_ID)));
            dish.setPrice(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_DISH_PRICE)));
            dish.setState(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_DISH_STATE)));
            dishList.add(dish);

            cursor.moveToNext();
        }
        return dishList;
    }

    @SuppressLint("Range")
    public Dish SelectDishById(int dishId){
        Dish dish = new Dish();
        String query = "SELECT * FROM " + CreateDB.TBL_DISH + " WHERE " + CreateDB.TBL_DISH_ID + " = " + dishId;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            dish.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDB.TBL_DISH_IMAGE)));
            dish.setDishName(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_DISH_NAME)));
            dish.setCategoryId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_DISH_CATEGORYID)));
            dish.setDishId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_DISH_ID)));
            dish.setPrice(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_DISH_PRICE)));
            dish.setState(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_DISH_STATE)));

            cursor.moveToNext();
        }
        return dish;
    }
}
