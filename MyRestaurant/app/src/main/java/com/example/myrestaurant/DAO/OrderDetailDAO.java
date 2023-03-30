package com.example.myrestaurant.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myrestaurant.DB.CreateDB;
import com.example.myrestaurant.Model.OrderDetail;

public class OrderDetailDAO {
    SQLiteDatabase db;
    public OrderDetailDAO(Context context){
        CreateDB createDB = new CreateDB(context);
        db = createDB.open();
    }

    public boolean CheckDishExist(int orderId, int dishId){
        String query = "SELECT * FROM " + CreateDB.TBL_ORDERDETAIL + " WHERE " + CreateDB.TBL_ORDERDETAIL_DISHID +
                " = " + dishId + " AND " + CreateDB.TBL_ORDERDETAIL_ORDERID + " = " + orderId;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("Range")
    public int SelectQtyDishByOrderId(int orderId, int dishId){
        int qty = 0;
        String query = "SELECT * FROM " + CreateDB.TBL_ORDERDETAIL + " WHERE " + CreateDB.TBL_ORDERDETAIL_DISHID +
                " = " + dishId + " AND " + CreateDB.TBL_ORDERDETAIL_ORDERID + " = "+ orderId;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            qty = cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_ORDERDETAIL_QUANTITY));
            cursor.moveToNext();
        }
        return qty;
    }

    public boolean UpdateQuantity(OrderDetail orderDetail){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_ORDERDETAIL_QUANTITY, orderDetail.getQty());

        long ktra = db.update(CreateDB.TBL_ORDERDETAIL, contentValues, CreateDB.TBL_ORDERDETAIL_ORDERID + " = "
                + orderDetail.getOrderId()+ " AND " + CreateDB.TBL_ORDERDETAIL_DISHID + " = "
                + orderDetail.getDishId(), null);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean AddOrderDetail(OrderDetail orderDetail){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_ORDERDETAIL_QUANTITY, orderDetail.getQty());
        contentValues.put(CreateDB.TBL_ORDERDETAIL_ORDERID, orderDetail.getOrderId());
        contentValues.put(CreateDB.TBL_ORDERDETAIL_DISHID, orderDetail.getDishId());

        long ktra = db.insert(CreateDB.TBL_ORDERDETAIL,null,contentValues);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }
}
