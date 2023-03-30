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

import com.example.myrestaurant.Model.Dish;
import com.example.myrestaurant.R;

import java.util.List;

public class AdapterDisplayMenu extends BaseAdapter {
    Context context;
    int layout;
    List<Dish> dishList;
    Viewholder viewholder;

    //constructor
    public AdapterDisplayMenu(Context context, int layout, List<Dish> dishList){
        this.context = context;
        this.layout = layout;
        this.dishList = dishList;
    }

    @Override
    public int getCount() {
        return dishList.size();
    }

    @Override
    public Object getItem(int position) {
        return dishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dishList.get(position).getDishId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewholder.imgCustomMenuDishImage = (ImageView)view.findViewById(R.id.imgCustomMenuDishImage);
            viewholder.txtCustomMenuDishName = (TextView) view.findViewById(R.id.txtCustomMenuDishName);
            viewholder.txtCustomMenuState = (TextView)view.findViewById(R.id.txtCustomMenuState);
            viewholder.txtCustomMenuPrice = (TextView)view.findViewById(R.id.txtCustomMenuPrice);
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }
        Dish dish = dishList.get(position);
        viewholder.txtCustomMenuDishName.setText(dish.getDishName());
        viewholder.txtCustomMenuPrice.setText(dish.getPrice() + " VND");

        //hiển thị tình trạng của món
        if(dish.getState().equals("true")) {
            viewholder.txtCustomMenuState.setText("Dish Still");
        } else {
            viewholder.txtCustomMenuState.setText("Out of dishes");
        }

        //lấy hình ảnh
        if(dish.getImage() != null) {
            byte[] menuimage = dish.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            viewholder.imgCustomMenuDishImage.setImageBitmap(bitmap);
        } else {
            viewholder.imgCustomMenuDishImage.setImageResource(R.drawable.cafe_americano);
        }
        return view;
    }

    //tạo viewholer lưu trữ component
    public class Viewholder {
        ImageView imgCustomMenuDishImage;
        TextView txtCustomMenuDishName, txtCustomMenuPrice, txtCustomMenuState;

    }
}
