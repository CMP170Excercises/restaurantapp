package com.example.myrestaurant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myrestaurant.Adapter.AdapterDisplayPayment;
import com.example.myrestaurant.DAO.OrderDAO;
import com.example.myrestaurant.DAO.PaymentDAO;
import com.example.myrestaurant.DAO.TableDAO;
import com.example.myrestaurant.Model.Payment;
import com.example.myrestaurant.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgPaymentBackBtn;
    TextView txtPaymentTableName, txtPaymentOrderDate, txtPaymentTotal;
    Button btnPaymentPay;
    GridView gvDisplayPayment;
    OrderDAO orderDAO;
    TableDAO tableDAO;
    PaymentDAO paymentDAO;
    List<Payment> paymentList;
    AdapterDisplayPayment adapterDisplayPayment;
    long total = 0;
    int tableId, orderId;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payment);

        //region thuộc tính view
        gvDisplayPayment= (GridView)findViewById(R.id.gvDisplayPayment);
        imgPaymentBackBtn = (ImageView)findViewById(R.id.imgPaymentBackBtn);
        txtPaymentTableName = (TextView)findViewById(R.id.txtPaymentTableName);
        txtPaymentOrderDate = (TextView)findViewById(R.id.txtPaymentOrderDate);
        txtPaymentTotal = (TextView)findViewById(R.id.txtPaymentTotal);
        btnPaymentPay = (Button)findViewById(R.id.btnPaymentPay);

        //khởi tạo kết nối csdl
        orderDAO = new OrderDAO(this);
        paymentDAO = new PaymentDAO(this);
        tableDAO = new TableDAO(this);

        fragmentManager = getSupportFragmentManager();

        //lấy data từ mã bàn đc chọn
        Intent intent = getIntent();
        tableId = intent.getIntExtra("tableId",0);
        String tableName = intent.getStringExtra("tableName");
        String orderDate = intent.getStringExtra("orderDate");

        txtPaymentTableName.setText(tableName);
        txtPaymentOrderDate.setText(orderDate);

        //ktra mã bàn tồn tại thì hiển thị
        if(tableId != 0){
            ShowPayment();

            for (int i = 0; i < paymentList.size(); i++) {
                int qty = paymentList.get(i).getQty();
                int price = paymentList.get(i).getPrice();

                total += (qty * price);
            }
            txtPaymentTotal.setText(String.valueOf(total) +" VND");
        }

        btnPaymentPay.setOnClickListener(this);
        imgPaymentBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnPaymentPay:
                boolean checkTable = tableDAO.UpdateState(tableId, "false");
                boolean checkOrder = orderDAO.UpdateOrderStateByTable(tableId, "true");
                boolean checkTotal = orderDAO.UpdateTotal(orderId, String.valueOf(total));
                if(checkTable && checkOrder && checkTotal) {
                    ShowPayment();
                    txtPaymentTotal.setText("0 VND");
                    Toast.makeText(getApplicationContext(),"Pay Successfully!",Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(getApplicationContext(),"Pay Failed!",Toast.LENGTH_SHORT);
                }
                break;

            case R.id.imgPaymentBackBtn:
                finish();
                break;
        }
    }

    //hiển thị data lên gridview
    private void ShowPayment(){
        orderId = (int) orderDAO.SelectIdByTable(tableId, "false");
        paymentList = paymentDAO.SelectDishListByOrderId(orderId);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_payment_menu, paymentList);
        gvDisplayPayment.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}
