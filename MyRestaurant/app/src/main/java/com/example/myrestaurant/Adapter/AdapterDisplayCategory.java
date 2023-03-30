package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myrestaurant.Model.DishCategory;
import com.example.myrestaurant.R;

import java.util.List;

public class AdapterDisplayCategory extends BaseAdapter {
    Context context;
    int layout;
    List<DishCategory> dishCategoryList ;
    ViewHolder viewHolder;

    //constructor
    public AdapterDisplayCategory(Context context, int layout, List<DishCategory> dishCategoryList) {
        this.context = context;
        this.layout = layout;
        this.dishCategoryList = dishCategoryList;
    }

    @Override
    public int getCount() {
        return dishCategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return dishCategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dishCategoryList.get(position).getCategoryId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //nếu lần đầu gọi view
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            //truyền component vào viewholder để ko gọi findview ở những lần hiển thị khác
            viewHolder.imgCustomCategoryImage = (ImageView)view.findViewById(R.id.imgCustomCategoryImage);
            viewHolder.txtCustomCategoryName = (TextView)view.findViewById(R.id.txtCustomCategoryName);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DishCategory dishCategory = dishCategoryList.get(position);

        viewHolder.txtCustomCategoryName.setText(dishCategory.getCategoryName());

        byte[] categoryimage = dishCategory.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        viewHolder.imgCustomCategoryImage.setImageBitmap(bitmap);

        return view;
    }

    //tạo viewholer lưu trữ component
    public class ViewHolder{
        TextView txtCustomCategoryName;
        ImageView imgCustomCategoryImage;
    }
}
