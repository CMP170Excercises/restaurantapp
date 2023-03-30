package com.example.myrestaurant.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myrestaurant.Activity.DetailStatisticActivity;
import com.example.myrestaurant.Activity.HomeActivity;
import com.example.myrestaurant.Adapter.AdapterDisplayStatistic;
import com.example.myrestaurant.DAO.OrderDAO;
import com.example.myrestaurant.Model.Order;
import com.example.myrestaurant.R;

import java.util.List;

public class DisplayStatisticFragment extends Fragment {
    ListView lvStatistic;
    List<Order> orderList;
    OrderDAO orderDAO;
    AdapterDisplayStatistic adapterDisplayStatistic;
    FragmentManager fragmentManager;
    int orderId, staffId, tableId;
    String orderDate, total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_display_statistic, container, false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Statistical Management");
        setHasOptionsMenu(true);

        lvStatistic = (ListView)view.findViewById(R.id.lvStatistic);
        orderDAO = new OrderDAO(getActivity());

        orderList = orderDAO.SelectOrderList();
        adapterDisplayStatistic = new AdapterDisplayStatistic(getActivity(),R.layout.custom_layout_display_statistic, orderList);
        lvStatistic.setAdapter(adapterDisplayStatistic);
        adapterDisplayStatistic.notifyDataSetChanged();

        lvStatistic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderId = orderList.get(position).getOrderId();
                staffId = orderList.get(position).getStaffId();
                tableId = orderList.get(position).getTableId();
                orderDate = orderList.get(position).getOrderDate();
                total = orderList.get(position).getTotal();

                Intent intent = new Intent(getActivity(), DetailStatisticActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("staffId", staffId);
                intent.putExtra("tableId", tableId);
                intent.putExtra("orderDate", orderDate);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        return view;
    }
}
