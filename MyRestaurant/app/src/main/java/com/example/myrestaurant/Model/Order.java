package com.example.myrestaurant.Model;

public class Order {
    int OrderId, TableId , StaffId;
    String OrderState, OrderDate , Total;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int OrderId) {
        OrderId = OrderId;
    }

    public int getTableId() {
        return TableId;
    }

    public void setTableId(int TableId) {
        TableId = TableId;
    }

    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(int StaffId) {
        StaffId = StaffId;
    }

    public String getOrderState() {
        return OrderState;
    }

    public void setOrderState(String OrderState) {
        OrderState = OrderState;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        OrderDate = OrderDate;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        Total = Total;
    }
}
