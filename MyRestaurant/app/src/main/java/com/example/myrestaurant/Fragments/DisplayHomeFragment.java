package com.example.myrestaurant.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.Activity.AddCategoryActivity;
import com.example.myrestaurant.Activity.HomeActivity;
import com.example.myrestaurant.Adapter.AdapterRecyclerViewCategory;
import com.example.myrestaurant.Adapter.AdapterRecyclerViewStatistic;
import com.example.myrestaurant.DAO.DishCategoryDAO;
import com.example.myrestaurant.DAO.OrderDAO;
import com.example.myrestaurant.Model.DishCategory;
import com.example.myrestaurant.Model.Order;
import com.example.myrestaurant.R;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DisplayHomeFragment extends Fragment implements View.OnClickListener {
    RecyclerView rcvDisplayHomeDishCategory, rcvDisplayHomeOrderOfDay;
    RelativeLayout layoutDisplayHomeStatistic, layoutDisplayHomeViewTable, layoutDisplayHomeViewMenu, layoutDisplayHomeViewStaff;
    TextView txtDisplayHomeViewAllCategory, txtDisplayHomeViewAllStatistic;
    DishCategoryDAO dishCategoryDAO;
    OrderDAO orderDAO;
    List<DishCategory> dishCategoryList;
    List<Order> orderList;
    AdapterRecyclerViewCategory adapterRecyclerViewCategory;
    AdapterRecyclerViewStatistic adapterRecyclerViewStatistic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_display_home, container, false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Home Page");
        setHasOptionsMenu(true);

        rcvDisplayHomeDishCategory = (RecyclerView)view.findViewById(R.id.rcvDisplayHomeDishCategory);
        rcvDisplayHomeOrderOfDay = (RecyclerView)view.findViewById(R.id.rcvDisplayHomeOrderOfDay);
        layoutDisplayHomeStatistic = (RelativeLayout)view.findViewById(R.id.layoutDisplayHomeStatistic);
        layoutDisplayHomeViewTable = (RelativeLayout)view.findViewById(R.id.layoutDisplayHomeViewTable);
        layoutDisplayHomeViewMenu = (RelativeLayout)view.findViewById(R.id.layoutDisplayHomeViewMenu);
        layoutDisplayHomeViewStaff = (RelativeLayout)view.findViewById(R.id.layoutDisplayHomeViewStaff);
        txtDisplayHomeViewAllCategory = (TextView) view.findViewById(R.id.txtDisplayHomeViewAllCategory);
        txtDisplayHomeViewAllStatistic = (TextView) view.findViewById(R.id.txtDisplayHomeViewAllStatistic);

        // khởi tạo kết nối
        dishCategoryDAO = new DishCategoryDAO(getActivity());
        orderDAO = new OrderDAO(getActivity());

        ShowListCategory();
        ShowOrderOfDay();

        layoutDisplayHomeStatistic.setOnClickListener(this);
        layoutDisplayHomeViewTable.setOnClickListener(this);
        layoutDisplayHomeViewMenu.setOnClickListener(this);
        layoutDisplayHomeViewStaff.setOnClickListener(this);
        txtDisplayHomeViewAllCategory.setOnClickListener(this);
        txtDisplayHomeViewAllStatistic.setOnClickListener(this);

        return view;
    }

    private void ShowListCategory(){
        rcvDisplayHomeDishCategory.setHasFixedSize(true);
        rcvDisplayHomeDishCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        dishCategoryList = dishCategoryDAO.SelectCategoryList();
        adapterRecyclerViewCategory = new AdapterRecyclerViewCategory(getActivity(),R.layout.custom_layout_display_category, dishCategoryList);
        rcvDisplayHomeDishCategory.setAdapter(adapterRecyclerViewCategory);
        adapterRecyclerViewCategory.notifyDataSetChanged();
    }

    private void ShowOrderOfDay(){
        rcvDisplayHomeOrderOfDay.setHasFixedSize(true);
        rcvDisplayHomeOrderOfDay.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String orderDate= dateFormat.format(calendar.getTime());

        orderList = orderDAO.SelectOrderListByDate(orderDate);
        adapterRecyclerViewStatistic = new AdapterRecyclerViewStatistic(getActivity(), R.layout.custom_layout_display_statistic, orderList);
        rcvDisplayHomeOrderOfDay.setAdapter(adapterRecyclerViewStatistic);
        adapterRecyclerViewCategory.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        NavigationView navigationView = (NavigationView)getActivity().findViewById(R.id.navViewHome);
        switch (id){
            case R.id.layoutDisplayHomeStatistic:

            case R.id.txtDisplayHomeViewAllStatistic:
                FragmentTransaction tranDisplayStatistic = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayStatistic.replace(R.id.contentView, new DisplayStatisticFragment());
                tranDisplayStatistic.addToBackStack(null);
                tranDisplayStatistic.commit();
                navigationView.setCheckedItem(R.id.navStatistic);
                break;

            case R.id.layoutDisplayHomeViewTable:
                FragmentTransaction tranDisplayTable = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayTable.replace(R.id.contentView,new DisplayTableFragment());
                tranDisplayTable.addToBackStack(null);
                tranDisplayTable.commit();
                navigationView.setCheckedItem(R.id.navTable);
                break;

            case R.id.layoutDisplayHomeViewMenu:
                Intent iAddCategory = new Intent(getActivity(), AddCategoryActivity.class);
                startActivity(iAddCategory);
                navigationView.setCheckedItem(R.id.navCategory);
                break;

            case R.id.layoutDisplayHomeViewStaff:
                FragmentTransaction tranDisplayStaff= getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayStaff.replace(R.id.contentView,new DisplayStaffFragment());
                tranDisplayStaff.addToBackStack(null);
                tranDisplayStaff.commit();
                navigationView.setCheckedItem(R.id.navStaff);
                break;

            case R.id.txtDisplayHomeViewAllCategory:
                FragmentTransaction tranDisplayCategory = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayCategory.replace(R.id.contentView,new DisplayCategoryFragment());
                tranDisplayCategory.addToBackStack(null);
                tranDisplayCategory.commit();
                navigationView.setCheckedItem(R.id.navCategory);
                break;
        }
    }
}
