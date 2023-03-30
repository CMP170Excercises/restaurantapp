package com.example.myrestaurant.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myrestaurant.DB.CreateDB;
import com.example.myrestaurant.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    SQLiteDatabase db;
    public OrderDAO(Context context){
        CreateDB createDB = new CreateDB(context);
        db = createDB.open();
    }

    public long ThemDonDat(Order order){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_ORDER_TABLEID, order.getTableId());
        contentValues.put(CreateDB.TBL_ORDER_STAFFID, order.getStaffId());
        contentValues.put(CreateDB.TBL_ORDER_DATE, order.getOrderDate());
        contentValues.put(CreateDB.TBL_ORDER_STATE, order.getOrderState());
        contentValues.put(CreateDB.TBL_ORDER_TOTAL, order.getTotal());

        long orderId = db.insert(CreateDB.TBL_ORDER, null, contentValues);

        return orderId;
    }

    @SuppressLint("Range")
    public List<Order> SelectOrderList(){
        List<Order> orderList = new ArrayList<Order>();
        String query = "SELECT * FROM " + CreateDB.TBL_ORDER;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Order donDatDTO = new Order();
            donDatDTO.setOrderId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_ORDER_ID)));
            donDatDTO.setTableId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_ORDER_TABLEID)));
            donDatDTO.setTotal(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_ORDER_TOTAL)));
            donDatDTO.setOrderState(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_ORDER_STATE)));
            donDatDTO.setOrderDate(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_ORDER_DATE)));
            donDatDTO.setStaffId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_ORDER_STAFFID)));
            orderList.add(donDatDTO);

            cursor.moveToNext();
        }
        return orderList;
    }

    @SuppressLint("Range")
    public List<Order> SelectOrderListByDate(String oDate){
        List<Order> orderList = new ArrayList<Order>();
        String query = "SELECT * FROM " + CreateDB.TBL_ORDER + " WHERE " + CreateDB.TBL_ORDER_DATE + " like '" + oDate + "'";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Order order = new Order();
            order.setOrderId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_ORDER_ID)));
            order.setTableId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_ORDER_TABLEID)));
            order.setTotal(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_ORDER_TOTAL)));
            order.setOrderState(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_ORDER_STATE)));
            order.setOrderDate(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_ORDER_DATE)));
            order.setStaffId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_ORDER_STAFFID)));
            orderList.add(order);

            cursor.moveToNext();
        }
        return orderList;
    }

    @SuppressLint("Range")
    public long SelectIdByTable(int tableId, String state){
        String query = "SELECT * FROM " + CreateDB.TBL_ORDER + " WHERE " + CreateDB.TBL_ORDER_TABLEID + " = '" + tableId + "' AND "
                + CreateDB.TBL_ORDER_STATE + " = '" + state + "' ";
        long oderId = 0;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            oderId = cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_ORDER_ID));

            cursor.moveToNext();
        }
        return oderId;
    }

    public boolean UpdateTotal(int orderId, String total){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_ORDER_TOTAL, total);
        long ktra  = db.update(CreateDB.TBL_ORDER, contentValues,
                CreateDB.TBL_ORDER_ID + " = " + orderId, null);
        if(ktra != 0) {
            return true;
        } else{
            return false;
        }
    }

    public boolean UpdateOrderStateByTable(int tableId, String state){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_ORDER_STATE, state);
        long ktra = db.update(CreateDB.TBL_ORDER, contentValues, CreateDB.TBL_ORDER_TABLEID +
                " = '" + tableId + "'", null);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }
}
