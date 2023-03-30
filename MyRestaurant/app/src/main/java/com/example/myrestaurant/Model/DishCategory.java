package com.example.myrestaurant.Model;

public class DishCategory {
    int CategoryId;
    String CategoryName;
    byte[] Image;


    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int CategoryId) {
        CategoryId = CategoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        CategoryName = CategoryName;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] Image) {
        Image = Image;
    }
}
