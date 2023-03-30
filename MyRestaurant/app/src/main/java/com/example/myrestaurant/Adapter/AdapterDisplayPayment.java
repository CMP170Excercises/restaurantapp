package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myrestaurant.Model.Payment;
import com.example.myrestaurant.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDisplayPayment extends BaseAdapter {
    Context context;
    int layout;
    List<Payment> paymentList;
    ViewHolder viewHolder;

    public AdapterDisplayPayment(Context context, int layout, List<Payment> paymentList){
        this.context = context;
        this.layout = layout;
        this.paymentList = paymentList;
    }

    @Override
    public int getCount() {
        return paymentList.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.imgCustomPaymentDishImage = view.findViewById(R.id.imgCustomPaymentDishImage);
            viewHolder.txtCustomPaymentDishName = (TextView)view.findViewById(R.id.txtCustomPaymentDishName);
            viewHolder.txtCustomPaymentQuantity = (TextView)view.findViewById(R.id.txtCustomPaymentQuantity);
            viewHolder.txtCustomPaymentPrice = (TextView)view.findViewById(R.id.txtCustomPaymentPrice);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Payment payment = paymentList.get(position);

        viewHolder.txtCustomPaymentDishName.setText(payment.getDishName());
        viewHolder.txtCustomPaymentQuantity.setText(String.valueOf(payment.getQty()));
        viewHolder.txtCustomPaymentPrice.setText(String.valueOf(payment.getPrice()) + " VND");

        byte[] paymentimg = payment.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(paymentimg,0,paymentimg.length);
        viewHolder.imgCustomPaymentDishImage.setImageBitmap(bitmap);

        return view;
    }

    public class ViewHolder{
        CircleImageView imgCustomPaymentDishImage;
        TextView txtCustomPaymentDishName, txtCustomPaymentQuantity, txtCustomPaymentPrice;
    }
}
