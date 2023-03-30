package com.example.myrestaurant.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myrestaurant.DB.CreateDB;
import com.example.myrestaurant.Model.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    SQLiteDatabase db;
    public PaymentDAO(Context context){
        CreateDB createDB = new CreateDB(context);
        db = createDB.open();
    }

    @SuppressLint("Range")
    public List<Payment> SelectDishListByOrderId(int orderId){
        List<Payment> paymentList = new ArrayList<Payment>();
        String query = "SELECT * FROM " + CreateDB.TBL_ORDERDETAIL + " ctdd," + CreateDB.TBL_DISH + " mon WHERE "
                + "ctdd." + CreateDB.TBL_ORDERDETAIL_DISHID + " = mon." + CreateDB.TBL_DISH_ID + " AND "
                + CreateDB.TBL_ORDERDETAIL_ORDERID + " = '"+ orderId +"'";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Payment payment = new Payment();
            payment.setQty(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_ORDERDETAIL_QUANTITY)));
            payment.setPrice(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_DISH_PRICE)));
            payment.setDishName(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_DISH_NAME)));
            payment.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDB.TBL_DISH_IMAGE)));
            paymentList.add(payment);

            cursor.moveToNext();
        }
        return paymentList;
    }
}
