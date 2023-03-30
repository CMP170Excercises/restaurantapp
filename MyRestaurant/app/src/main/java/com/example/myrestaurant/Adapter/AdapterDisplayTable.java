package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myrestaurant.Activity.HomeActivity;
import com.example.myrestaurant.Activity.PaymentActivity;
import com.example.myrestaurant.DAO.OrderDAO;
import com.example.myrestaurant.DAO.TableDAO;
import com.example.myrestaurant.Fragments.DisplayCategoryFragment;
import com.example.myrestaurant.Model.Order;
import com.example.myrestaurant.Model.Table;
import com.example.myrestaurant.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterDisplayTable extends BaseAdapter implements View.OnClickListener{
    Context context;
    int layout;
    List<Table> tableList;
    ViewHolder viewHolder;
    TableDAO tableDAO;
    OrderDAO orderDAO;
    FragmentManager fragmentManager;

    public AdapterDisplayTable(Context context, int layout, List<Table> tableList){
        this.context = context;
        this.layout = layout;
        this.tableList = tableList;
        tableDAO = new TableDAO(context);
        orderDAO = new OrderDAO(context);
        fragmentManager = ((HomeActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return tableList.size();
    }

    @Override
    public Object getItem(int position) {
        return tableList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tableList.get(position).getTableId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout, parent, false);

            viewHolder.imgTable = (ImageView) view.findViewById(R.id.imgCustomTableTable);
            viewHolder.imgOrder = (ImageView) view.findViewById(R.id.imgCustomTableOrder);
            viewHolder.imgPay = (ImageView) view.findViewById(R.id.imgCustomTablePay);
            viewHolder.imgClick = (ImageView) view.findViewById(R.id.imgCustomTableClick);
            viewHolder.txtTableName = (TextView)view.findViewById(R.id.txtCustomTableName);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if(tableList.get(position).isChosen()) {
            ShowButton();
        } else {
            AnButton();
        }

        Table table = tableList.get(position);

        String checkState = tableDAO.SelectStateById(table.getTableId());
        //đổi hình theo tình trạng
        if(checkState.equals("true")){
            viewHolder.imgTable.setImageResource(R.drawable.ic_baseline_airline_seat_legroom_normal_40);
        } else {
            viewHolder.imgTable.setImageResource(R.drawable.ic_baseline_event_seat_40);
        }

        viewHolder.txtTableName.setText(table.getTableName());
        viewHolder.imgTable.setTag(position);

        // click event
        viewHolder.imgTable.setOnClickListener(this);
        viewHolder.imgOrder.setOnClickListener(this);
        viewHolder.imgPay.setOnClickListener(this);
        viewHolder.imgClick.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolder = (ViewHolder) ((View) v.getParent()).getTag();

        int pos1 = (int) viewHolder.imgTable.getTag();

        int tableId = tableList.get(pos1).getTableId();
        String tableName = tableList.get(pos1).getTableName();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String orderDate = dateFormat.format(calendar.getTime());

        switch (id){
            case R.id.imgCustomTableTable:
                int pos = (int)v.getTag();
                tableList.get(pos).setChosen(true);
                ShowButton();
                break;

            case R.id.imgCustomTableClick:
                AnButton();
                break;

            case R.id.imgCustomTableOrder:
                Intent getIHome = ((HomeActivity)context).getIntent();
                int staffId = getIHome.getIntExtra("staffId",0);
                String state = tableDAO.SelectStateById(tableId);

                if(state.equals("false")){
                    //Thêm bảng gọi món và update tình trạng bàn
                    Order order = new Order();
                    order.setTableId(tableId);
                    order.setStaffId(staffId);
                    order.setOrderDate(orderDate);
                    order.setOrderState("false");
                    order.setTotal("0");

                    long ktra = orderDAO.ThemDonDat(order);
                    tableDAO.UpdateState(tableId, "true");
                    if(ktra == 0) { Toast.makeText(context, context.getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show(); }
                }
                //chuyển qua trang category
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                DisplayCategoryFragment displayCategoryFragment = new DisplayCategoryFragment();

                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("tableId", tableId);
                displayCategoryFragment.setArguments(bDataCategory);

                transaction.replace(R.id.contentView, displayCategoryFragment).addToBackStack("showtable");
                transaction.commit();
                break;

            case R.id.imgCustomTablePay:
                //chuyển dữ liệu qua trang thanh toán
                Intent iThanhToan = new Intent(context, PaymentActivity.class);
                iThanhToan.putExtra("tableId", tableId);
                iThanhToan.putExtra("tableName",tableName);
                iThanhToan.putExtra("orderDate",orderDate);
                context.startActivity(iThanhToan);
                break;
        }
    }

    private void ShowButton() {
        viewHolder.imgOrder.setVisibility(View.VISIBLE);
        viewHolder.imgPay.setVisibility(View.VISIBLE);
        viewHolder.imgClick.setVisibility(View.VISIBLE);
    }
    private void AnButton() {
        viewHolder.imgOrder.setVisibility(View.INVISIBLE);
        viewHolder.imgPay.setVisibility(View.INVISIBLE);
        viewHolder.imgClick.setVisibility(View.INVISIBLE);
    }

    public class ViewHolder {
        ImageView imgTable, imgOrder, imgPay, imgClick;
        TextView txtTableName;
    }
}
