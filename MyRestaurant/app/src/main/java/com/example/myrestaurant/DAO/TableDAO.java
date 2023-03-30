package com.example.myrestaurant.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myrestaurant.DB.CreateDB;
import com.example.myrestaurant.Model.Table;

import java.util.ArrayList;
import java.util.List;

public class TableDAO {
    SQLiteDatabase db;
    public TableDAO(Context context){
        CreateDB createDatabase = new CreateDB(context);
        db = createDatabase.open();
    }

    public boolean AddTable(String tableName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_TABLE_NAME, tableName);
        contentValues.put(CreateDB.TBL_TABLE_STATE, "false");

        long ktra = db.insert(CreateDB.TBL_TABLE, null, contentValues);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DeleteTableById(int tableId){
        long ktra = db.delete(CreateDB.TBL_TABLE, CreateDB.TBL_TABLE_ID + " = " + tableId, null);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EditTableName(int tableId, String tableName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_TABLE_NAME, tableName);

        long ktra = db.update(CreateDB.TBL_TABLE,contentValues, CreateDB.TBL_TABLE_ID + " = '" + tableId +"' ", null);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("Range")
    public List<Table> SelectAllTable(){
        List<Table> tableList = new ArrayList<Table>();
        String query = "SELECT * FROM " + CreateDB.TBL_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Table table = new Table();
            table.setTableId(cursor.getInt(cursor.getColumnIndex(CreateDB.TBL_TABLE_ID)));
            table.setTableName(cursor.getString(cursor.getColumnIndex(CreateDB.TBL_TABLE_NAME)));

            tableList.add(table);
            cursor.moveToNext();
        }
        return tableList;
    }

    @SuppressLint("Range")
    public String SelectStateById(int tableId){
        String state = "";
        String query = "SELECT * FROM "+ CreateDB.TBL_TABLE + " WHERE " + CreateDB.TBL_TABLE_ID + " = '" + tableId + "' ";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            state = cursor.getString(cursor.getColumnIndex(CreateDB.TBL_TABLE_STATE));
            cursor.moveToNext();
        }

        return state;
    }

    public boolean UpdateState(int tableId, String state){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.TBL_TABLE_STATE, state);

        long ktra = db.update(CreateDB.TBL_TABLE, contentValues, CreateDB.TBL_TABLE_ID + " = '" + tableId + "' ", null);
        if(ktra != 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("Range")
    public String SelectTableNameById(int tableId){
        String name = "";
        String query = "SELECT * FROM " + CreateDB.TBL_TABLE + " WHERE " + CreateDB.TBL_TABLE_ID + " = '" + tableId + "' ";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            name = cursor.getString(cursor.getColumnIndex(CreateDB.TBL_TABLE_NAME));
            cursor.moveToNext();
        }

        return name;
    }
}
