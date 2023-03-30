package com.example.myrestaurant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.DAO.OrderDAO;
import com.example.myrestaurant.DAO.OrderDetailDAO;
import com.example.myrestaurant.Model.OrderDetail;
import com.example.myrestaurant.R;
import com.google.android.material.textfield.TextInputLayout;

public class AmountMenuActivity extends AppCompatActivity {
    TextInputLayout txtAmountMenuQuantity;
    Button btnAmountMenuAccept;
    int tableId, dishId;
    OrderDAO orderDAO;
    OrderDetailDAO orderDetailDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_amount_menu);

        //Lấy đối tượng view
        txtAmountMenuQuantity = (TextInputLayout)findViewById(R.id.txtAmountMenuQuantity);
        btnAmountMenuAccept = (Button)findViewById(R.id.btnAmountMenuAccept);

        //khởi tạo kết nối csdl
        orderDAO = new OrderDAO(this);
        orderDetailDAO = new OrderDetailDAO(this);

        //Lấy thông tin từ bàn được chọn
        Intent intent = getIntent();
        tableId = intent.getIntExtra("tableId",0);
        dishId = intent.getIntExtra("dishId",0);

        btnAmountMenuAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAmount()) {
                    return;
                }

                int orderId = (int) orderDAO.SelectIdByTable(tableId, "false");
                boolean ktra = orderDetailDAO.CheckDishExist(orderId, dishId);
                if(ktra) {
                    //update số lượng món đã chọn
                    int oldQty = orderDetailDAO.SelectQtyDishByOrderId(orderId, dishId);
                    int newQty = Integer.parseInt(txtAmountMenuQuantity.getEditText().getText().toString());
                    int sumQty = oldQty + newQty;

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setDishId(dishId);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setQty(sumQty);

                    boolean checkUpdate = orderDetailDAO.UpdateQuantity(orderDetail);
                    if(checkUpdate) {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //thêm số lượng món nếu chưa chọn món này
                    int qty = Integer.parseInt(txtAmountMenuQuantity.getEditText().getText().toString());
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setDishId(dishId);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setQty(qty);

                    boolean checkUpdate = orderDetailDAO.AddOrderDetail(orderDetail);
                    if(checkUpdate) {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Validate số lượng
    private boolean validateAmount(){
        String val = txtAmountMenuQuantity.getEditText().getText().toString().trim();
        if(val.isEmpty()) {
            txtAmountMenuQuantity.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(!val.matches(("\\d+(?:\\.\\d+)?"))) {
            txtAmountMenuQuantity.setError("Quantity is invalid");
            return false;
        } else {
            txtAmountMenuQuantity.setError(null);
            txtAmountMenuQuantity.setErrorEnabled(false);
            return true;
        }
    }
}
