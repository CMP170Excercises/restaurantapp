package com.example.myrestaurant.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.myrestaurant.Activity.AddStaffActivity;
import com.example.myrestaurant.Activity.HomeActivity;
import com.example.myrestaurant.Adapter.AdapterDisplayStaff;
import com.example.myrestaurant.DAO.StaffDAO;
import com.example.myrestaurant.Model.Staff;
import com.example.myrestaurant.R;

import java.util.List;

public class DisplayStaffFragment extends Fragment {
    GridView gvStaff;
    StaffDAO staffDAO;
    List<Staff> staffList;
    AdapterDisplayStaff adapterDisplayStaff;

    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                Intent intent = result.getData();
                long ktra = intent.getLongExtra("ketquaktra",0);
                String function = intent.getStringExtra("function");
                if(function.equals("addstaff")) {
                    if(ktra != 0) {
                        ShowListStaff();
                        Toast.makeText(getActivity(),"Add successfully!",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),"Add Failed!",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(ktra != 0) {
                        ShowListStaff();
                        Toast.makeText(getActivity(),"Edit successfully!",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),"Edit Failed!",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_display_staff,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Staff Management");
        setHasOptionsMenu(true);

        gvStaff = (GridView)view.findViewById(R.id.gvStaff) ;

        staffDAO = new StaffDAO(getActivity());
        ShowListStaff();

        registerForContextMenu(gvStaff);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = menuInfo.position;
        int staffId = staffList.get(pos).getStaffId();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddStaffActivity.class);
                iEdit.putExtra("staffId", staffId);
                resultLauncherAdd.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = staffDAO.DeleteStaff(staffId);
                if(ktra){
                    ShowListStaff();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddStaff = menu.add(1, R.id.itAddStaff, 1, "Add Staff");
        itAddStaff.setIcon(R.drawable.ic_baseline_add_24);
        itAddStaff.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddStaff:
                Intent regis = new Intent(getActivity(), AddStaffActivity.class);
                resultLauncherAdd.launch(regis);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShowListStaff(){
        staffList = staffDAO.LayDSNV();
        adapterDisplayStaff = new AdapterDisplayStaff(getActivity(),R.layout.custom_layout_display_staff, staffList);
        gvStaff.setAdapter(adapterDisplayStaff);
        adapterDisplayStaff.notifyDataSetChanged();
    }
}
