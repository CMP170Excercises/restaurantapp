package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.Model.DishCategory;
import com.example.myrestaurant.R;

import java.util.List;

public class AdapterRecyclerViewCategory extends RecyclerView.Adapter<AdapterRecyclerViewCategory.ViewHolder> {
    Context context;
    int layout;
    List<DishCategory> dishCategoryList;

    public AdapterRecyclerViewCategory(Context context, int layout, List<DishCategory> dishCategoryList){
        this.context = context;
        this.layout = layout;
        this.dishCategoryList = dishCategoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerViewCategory.ViewHolder holder, int position) {
        DishCategory dishCategory = dishCategoryList.get(position);
        holder.txtCustomCategoryName.setText(dishCategory.getCategoryName());
        byte[] categoryimage = dishCategory.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        holder.imgCustomCategoryImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return dishCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtCustomCategoryName;
        ImageView imgCustomCategoryImage;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txtCustomCategoryName = itemView.findViewById(R.id.txtCustomCategoryName);
            imgCustomCategoryImage = itemView.findViewById(R.id.imgCustomCategoryImage);
        }
    }
}
