package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.DAO.StaffDAO;
import com.example.myrestaurant.DAO.TableDAO;
import com.example.myrestaurant.Model.Order;
import com.example.myrestaurant.Model.Staff;
import com.example.myrestaurant.R;

import java.util.List;

public class AdapterRecyclerViewStatistic extends RecyclerView.Adapter<AdapterRecyclerViewStatistic.ViewHolder> {
    Context context;
    int layout;
    List<Order> orderList;
    StaffDAO staffDAO;
    TableDAO tableDAO;

    public AdapterRecyclerViewStatistic(Context context, int layout, List<Order> orderList){

        this.context =context;
        this.layout = layout;
        this.orderList = orderList;
        staffDAO = new StaffDAO(context);
        tableDAO = new TableDAO(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerViewStatistic.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtCustomStatisticOrderId.setText("Order ID: " + order.getOrderId());
        holder.txtCustomStatisticOrderDate.setText(order.getOrderDate());
        if(order.getTotal().equals("0")) {
            holder.txtCustomStatisticTotal.setVisibility(View.INVISIBLE);
        } else {
            holder.txtCustomStatisticTotal.setVisibility(View.VISIBLE);
        }

        if (order.getOrderState().equals("true")) {
            holder.txtCustomStatisticState.setText("Paid");
        } else {
            holder.txtCustomStatisticState.setText("Unpaid");
        }
        Staff staff = staffDAO.SelectStaffById(order.getStaffId());
        holder.txtCustomStatisticStaffName.setText(staff.getStaffFullname());
        holder.txtCustomStatisticOrderTable.setText(tableDAO.SelectTableNameById(order.getTableId()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCustomStatisticOrderId, txtCustomStatisticOrderDate, txtCustomStatisticStaffName,
                txtCustomStatisticOrderTable, txtCustomStatisticTotal,txtCustomStatisticState;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txtCustomStatisticOrderId = itemView.findViewById(R.id.txtCustomStatisticOrderID);
            txtCustomStatisticOrderDate = itemView.findViewById(R.id.txtCustomStatisticOrderDate);
            txtCustomStatisticStaffName = itemView.findViewById(R.id.txtCustomStatisticStaffName);
            txtCustomStatisticOrderTable = itemView.findViewById(R.id.txtCustomStatisticOrderTable);
            txtCustomStatisticTotal = itemView.findViewById(R.id.txtCustomStatisticTotal);
            txtCustomStatisticState = itemView.findViewById(R.id.txtCustomStatisticState);
        }
    }
}
