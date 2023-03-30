package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myrestaurant.DAO.PermissionDAO;
import com.example.myrestaurant.Model.Staff;
import com.example.myrestaurant.R;

import java.util.List;

public class AdapterDisplayStaff extends BaseAdapter {
    Context context;
    int layout;
    List<Staff> staffList;
    ViewHolder viewHolder;
    PermissionDAO permissionDAO;

    public AdapterDisplayStaff(Context context, int layout, List<Staff> staffList){
        this.context = context;
        this.layout = layout;
        this.staffList = staffList;
        permissionDAO = new PermissionDAO(context);
    }

    @Override
    public int getCount() {
        return staffList.size();
    }

    @Override
    public Object getItem(int position) {
        return staffList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return staffList.get(position).getStaffId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolder.imgCustomStaffImage = (ImageView)view.findViewById(R.id.imgCustomStaffImage);
            viewHolder.txtCustomStaffName = (TextView)view.findViewById(R.id.txtCustomStaffName);
            viewHolder.txtCustomStaffPermission = (TextView)view.findViewById(R.id.txtCustomStaffPermission);
            viewHolder.txtCustomStaffPhone = (TextView)view.findViewById(R.id.txtCustomStaffPhone);
            viewHolder.txtCustomStaffEmail = (TextView)view.findViewById(R.id.txtCustomStaffEmail);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Staff staff = staffList.get(position);

        viewHolder.txtCustomStaffName.setText(staff.getStaffFullname());
        viewHolder.txtCustomStaffPermission.setText(permissionDAO.SelectPerNameById(staff.getPermissionId()));
        viewHolder.txtCustomStaffPhone.setText(staff.getPhone());
        viewHolder.txtCustomStaffEmail.setText(staff.getEmail());

        return view;
    }

    public class ViewHolder{
        ImageView imgCustomStaffImage;
        TextView txtCustomStaffName, txtCustomStaffPermission, txtCustomStaffPhone, txtCustomStaffEmail;
    }
}
