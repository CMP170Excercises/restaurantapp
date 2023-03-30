package com.example.myrestaurant.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrestaurant.Fragments.DisplayCategoryFragment;
import com.example.myrestaurant.Fragments.DisplayHomeFragment;
import com.example.myrestaurant.Fragments.DisplayStaffFragment;
import com.example.myrestaurant.Fragments.DisplayStatisticFragment;
import com.example.myrestaurant.Fragments.DisplayTableFragment;
import com.example.myrestaurant.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MenuItem selectedFeature, selectedManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    TextView txtMenuStaffName;
    int permissionId = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        // thuộc tính bên view
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        navigationView = (NavigationView)findViewById(R.id.navViewHome);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolBar);
        View view = navigationView.getHeaderView(0);
        txtMenuStaffName = (TextView) view.findViewById(R.id.txtMenuStaffName);

        // xử lý toolbar và navigation
        setSupportActionBar(toolbar); //tạo toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tạo nút mở navigation
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opentoggle, R.string.closetoggle){
            @Override
            public void onDrawerOpened(View drawerView) { super.onDrawerOpened(drawerView); }

            @Override
            public void onDrawerClosed(View drawerView) { super.onDrawerClosed(drawerView); }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Tụ động gán tên nv đăng nhập qua Extras
        Intent intent = getIntent();
        String user = intent.getStringExtra("username");
        txtMenuStaffName.setText( "Hello " + user + " !!");

        //lấy file share prefer
        sharedPreferences = getSharedPreferences("savepermission", Context.MODE_PRIVATE);
        permissionId = sharedPreferences.getInt("permissionId", 0);

        // hiện thị fragment home mặc định
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
        DisplayHomeFragment displayHomeFragment = new DisplayHomeFragment();
        tranDisplayHome.replace(R.id.contentView, displayHomeFragment);
        tranDisplayHome.commit();
        navigationView.setCheckedItem(R.id.navHome);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.navHome:
                //hiển thị tương ứng trên navigation
                FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
                DisplayHomeFragment displayHomeFragment = new DisplayHomeFragment();
                tranDisplayHome.replace(R.id.contentView,displayHomeFragment);
                tranDisplayHome.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                break;

            case R.id.navStatistic:
                //hiển thị tương ứng trên navigation
                FragmentTransaction tranDisplayStatistic = fragmentManager.beginTransaction();
                DisplayStatisticFragment displayStatisticFragment = new DisplayStatisticFragment();
                tranDisplayStatistic.replace(R.id.contentView,displayStatisticFragment);
                tranDisplayStatistic.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                break;

            case R.id.navTable:
                //hiển thị tương ứng trên navigation
                FragmentTransaction tranDisplayTable = fragmentManager.beginTransaction();
                DisplayTableFragment displayTableFragment = new DisplayTableFragment();
                tranDisplayTable.replace(R.id.contentView,displayTableFragment);
                tranDisplayTable.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                break;

            case R.id.navCategory:
                //hiển thị tương ứng trên navigation
                FragmentTransaction tranDisplayMenu = fragmentManager.beginTransaction();
                DisplayCategoryFragment displayCategoryFragment = new DisplayCategoryFragment();
                tranDisplayMenu.replace(R.id.contentView, displayCategoryFragment);
                tranDisplayMenu.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                break;

            case R.id.navStaff:
                if(permissionId == 1) {
                    //hiển thị tương ứng trên navigation
                    FragmentTransaction tranDisplayStaff = fragmentManager.beginTransaction();
                    DisplayStaffFragment displayStaffFragment = new DisplayStaffFragment();
                    tranDisplayStaff.replace(R.id.contentView,displayStaffFragment);
                    tranDisplayStaff.commit();
                    navigationView.setCheckedItem(item.getItemId());
                    drawerLayout.closeDrawers();
                } else {
                    Toast.makeText(getApplicationContext(), "You do not have access!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.navLogout:
                //gọi activity ra trang welcome
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}