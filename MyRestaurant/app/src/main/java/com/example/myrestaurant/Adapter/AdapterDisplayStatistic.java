package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myrestaurant.DAO.StaffDAO;
import com.example.myrestaurant.DAO.TableDAO;
import com.example.myrestaurant.Model.Order;
import com.example.myrestaurant.Model.Staff;
import com.example.myrestaurant.R;

import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter {
    Context context;
    int layout;
    List<Order> orderList;
    ViewHolder viewHolder;
    StaffDAO staffDAO;
    TableDAO tableDAO;

    public AdapterDisplayStatistic(Context context, int layout, List<Order> orderList){
        this.context = context;
        this.layout = layout;
        this.orderList = orderList;
        staffDAO = new StaffDAO(context);
        tableDAO = new TableDAO(context);
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderList.get(position).getOrderId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent, false);

            viewHolder.txtCustomStatisticOrderId = (TextView)view.findViewById(R.id.txtCustomStatisticOrderID);
            viewHolder.txtCustomStatisticOrderDate = (TextView)view.findViewById(R.id.txtCustomStatisticOrderDate);
            viewHolder.txtCustomStatisticStaffName = (TextView)view.findViewById(R.id.txtCustomStatisticStaffName);
            viewHolder.txtCustomStatisticTotal = (TextView)view.findViewById(R.id.txtCustomStatisticTotal);
            viewHolder.txtCustomStatisticState = (TextView)view.findViewById(R.id.txtCustomStatisticState);
            viewHolder.txtCustomStatisticOrderTable = (TextView)view.findViewById(R.id.txtCustomStatisticOrderTable);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Order order = orderList.get(position);

        viewHolder.txtCustomStatisticOrderId.setText("Order ID: " + order.getOrderId());
        viewHolder.txtCustomStatisticOrderDate.setText(order.getOrderDate());
        viewHolder.txtCustomStatisticTotal.setText(order.getTotal() + " VNĐ");
        if (order.getOrderState().equals("true")) {
            viewHolder.txtCustomStatisticState.setText("Paid");
        } else {
            viewHolder.txtCustomStatisticState.setText("Unpaid");
        }
        Staff staff = staffDAO.SelectStaffById(order.getStaffId());
        viewHolder.txtCustomStatisticStaffName.setText(staff.getStaffFullname());
        viewHolder.txtCustomStatisticOrderTable.setText(tableDAO.SelectTableNameById(order.getTableId()));

        return view;
    }
    public class ViewHolder{
        TextView txtCustomStatisticOrderId, txtCustomStatisticOrderDate, txtCustomStatisticStaffName
                , txtCustomStatisticTotal, txtCustomStatisticState, txtCustomStatisticOrderTable;

    }
}
