package com.example.myrestaurant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.Adapter.AdapterDisplayPayment;
import com.example.myrestaurant.DAO.PaymentDAO;
import com.example.myrestaurant.DAO.StaffDAO;
import com.example.myrestaurant.DAO.TableDAO;
import com.example.myrestaurant.Model.Payment;
import com.example.myrestaurant.Model.Staff;
import com.example.myrestaurant.R;

import java.util.List;

public class DetailStatisticActivity extends AppCompatActivity {
    ImageView imgDetailStatisticBackBtn;
    TextView txtDetailStatisticOrderId, txtDetailStatisticOrderDate, txtDetailStatisticTableName
            , txtDetailStatisticStaffName, txtDetailStatisticTotal;
    GridView gvDetailStatistic;
    int orderId, staffId, tableId;
    String orderDate, total;
    StaffDAO staffDAO;
    TableDAO tableDAO;
    List<Payment> paymentList;
    PaymentDAO paymentDAO;
    AdapterDisplayPayment adapterDisplayPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_statistic);

        // Lấy thông tin từ display statistic
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", 0);
        staffId = intent.getIntExtra("staffId", 0);
        tableId = intent.getIntExtra("tableId",0);
        orderDate = intent.getStringExtra("orderDate");
        total = intent.getStringExtra("total");

        // region Thuộc tính bên view
        imgDetailStatisticBackBtn = (ImageView)findViewById(R.id.imgDetailStatisticBack);
        txtDetailStatisticOrderId = (TextView)findViewById(R.id.txtDetailStatisticId);
        txtDetailStatisticOrderDate = (TextView)findViewById(R.id.txtDetailStatisticDate);
        txtDetailStatisticTableName = (TextView)findViewById(R.id.txtDetailStatisticTableName);
        txtDetailStatisticStaffName = (TextView)findViewById(R.id.txtDetailStatisticStaff);
        txtDetailStatisticTotal = (TextView)findViewById(R.id.txtDetailStatisticTotal);
        gvDetailStatistic = (GridView)findViewById(R.id.gvDetailStatistic);

        // khởi tạo lớp dao mở kết nối csdl
        staffDAO = new StaffDAO(this);
        tableDAO = new TableDAO(this);
        paymentDAO = new PaymentDAO(this);

        // chỉ hiển thị nếu lấy đc mã đơn đc chọn
        if (orderId != 0) {
            txtDetailStatisticOrderId.setText("Order ID: " + orderId);
            txtDetailStatisticOrderDate.setText(orderDate);
            txtDetailStatisticTotal.setText(total + " VNĐ");

            Staff staff = staffDAO.SelectStaffById(staffId);
            txtDetailStatisticStaffName.setText(staff.getStaffFullname());
            txtDetailStatisticTableName.setText(tableDAO.SelectTableNameById(tableId));

            ShowListOrderDetail();
        }

        imgDetailStatisticBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void ShowListOrderDetail(){
        paymentList = paymentDAO.SelectDishListByOrderId(orderId);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_payment_menu, paymentList);
        gvDetailStatistic.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}
