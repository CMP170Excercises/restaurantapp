package com.example.myrestaurant.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDB extends SQLiteOpenHelper {
    public static String TBL_STAFF = "STAFF";
    public static String TBL_DISH = "DISH";
    public static String TBL_DISHCATEGORY = "DISHCATEGORY";
    public static String TBL_TABLE = "TABLES";
    public static String TBL_ORDER = "ORDERS";
    public static String TBL_ORDERDETAIL = "ORDERDETAIL";
    public static String TBL_PERMISSION = "PERMISSION";

    // Table: Staff
    public static String TBL_STAFF_ID = "STAFFID";
    public static String TBL_STAFF_FULLNAME = "STAFFFULLNAME";
    public static String TBL_STAFF_USERNAME = "USERNAME";
    public static String TBL_STAFF_PASSWORD = "PASSWORD";
    public static String TBL_STAFF_EMAIL = "EMAIL";
    public static String TBL_STAFF_PHONE = "PHONE";
    public static String TBL_STAFF_GENDER = "GENDER";
    public static String TBL_STAFF_DOB = "NGAYSINH";
    public static String TBL_STAFF_PERMISSIONID = "PERMISSIONID";

    // Table: Permission
    public static String TBL_PERMISSION_ID = "PERMISSIONID";
    public static String TBL_PERMISSION_NAME = "PERMISSIONNAME";

    //  Table: Dish
    public static String TBL_DISH_ID = "DISHID";
    public static String TBL_DISH_NAME = "DISHNAME";
    public static String TBL_DISH_PRICE = "PRICE";
    public static String TBL_DISH_STATE = "STATE";
    public static String TBL_DISH_IMAGE = "IMAGE";
    public static String TBL_DISH_CATEGORYID = "CATEGORYID";

    // Table: Dish category
    public static String TBL_DISHCATEGORY_ID = "CATEGORYID";
    public static String TBL_DISHCATEGORY_NAME = "CATEGORYNAME";
    public static String TBL_DISHCATEGORY_IMAGE = "IMAGE";

    // Table: Table
    public static String TBL_TABLE_ID = "TABLEID";
    public static String TBL_TABLE_NAME = "TABLENAME";
    public static String TBL_TABLE_STATE = "STATE";

    // Table: Order
    public static String TBL_ORDER_ID = "ORDERID";
    public static String TBL_ORDER_STAFFID = "STAFFID";
    public static String TBL_ORDER_DATE = "ORDERDATE";
    public static String TBL_ORDER_STATE = "STATE";
    public static String TBL_ORDER_TOTAL = "TOTAL";
    public static String TBL_ORDER_TABLEID = "TABLEID";

    // Table: Order detail
    public static String TBL_ORDERDETAIL_ORDERID = "ORDERID";
    public static String TBL_ORDERDETAIL_DISHID = "DISHID";
    public static String TBL_ORDERDETAIL_QUANTITY = "QUANTITY";

    public CreateDB(Context context) {
        super(context, "MyRestaurant", null, 1);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tblSTAFF = "CREATE TABLE " + TBL_STAFF + " ( " + TBL_STAFF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_STAFF_FULLNAME + " TEXT, " + TBL_STAFF_USERNAME + " TEXT, " + TBL_STAFF_PASSWORD + " TEXT, " + TBL_STAFF_EMAIL + " TEXT, "
                + TBL_STAFF_PHONE + " TEXT, " + TBL_STAFF_GENDER + " TEXT, " + TBL_STAFF_DOB + " TEXT , " + TBL_STAFF_PERMISSIONID + " INTEGER)";

        String tblPERMISSION = "CREATE TABLE " + TBL_PERMISSION + " ( " + TBL_PERMISSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_PERMISSION_NAME + " TEXT)" ;

        String tblTABLE = "CREATE TABLE " + TBL_TABLE + " ( " + TBL_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_TABLE_NAME + " TEXT, " + TBL_TABLE_STATE + " TEXT )";

        String tblDISH = "CREATE TABLE " + TBL_DISH + " ( " + TBL_DISH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_DISH_NAME + " TEXT, " + TBL_DISH_PRICE + " TEXT, " + TBL_DISH_STATE + " TEXT, "
                + TBL_DISH_IMAGE + " BLOB, " + TBL_DISH_CATEGORYID + " INTEGER )";

        String tblDISHCATEGORY = "CREATE TABLE " + TBL_DISHCATEGORY + " ( " + TBL_DISHCATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_DISHCATEGORY_IMAGE + " BLOB, " + TBL_DISHCATEGORY_NAME + " TEXT)" ;

        String tblORDER = "CREATE TABLE " + TBL_ORDER + " ( " + TBL_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_ORDER_TABLEID + " INTEGER, " + TBL_ORDER_STAFFID + " INTEGER, " + TBL_ORDER_DATE + " TEXT, " + TBL_ORDER_TOTAL +" TEXT,"
                + TBL_ORDER_STATE + " TEXT )" ;

        String tblORDERDETAIL = "CREATE TABLE " + TBL_ORDERDETAIL + " ( " + TBL_ORDERDETAIL_ORDERID + " INTEGER, "
                + TBL_ORDERDETAIL_DISHID + " INTEGER, " + TBL_ORDERDETAIL_QUANTITY + " INTEGER, "
                + " PRIMARY KEY ( " + TBL_ORDERDETAIL_ORDERID + "," + TBL_ORDERDETAIL_DISHID + "))";

        db.execSQL(tblSTAFF);
        db.execSQL(tblPERMISSION);
        db.execSQL(tblTABLE);
        db.execSQL(tblDISH);
        db.execSQL(tblDISHCATEGORY);
        db.execSQL(tblORDER);
        db.execSQL(tblORDERDETAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    // Open database connect
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
