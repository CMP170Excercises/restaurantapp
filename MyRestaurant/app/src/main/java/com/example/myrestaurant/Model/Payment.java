package com.example.myrestaurant.Model;

public class Payment {
    String DishName;
    int Quantity, Price;
    byte[] Image;

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String DishName) {
        DishName = DishName;
    }

    public int getQty() {
        return Quantity;
    }

    public void setQty(int Quantity) {
        Quantity = Quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        Price = Price;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] Image) {
        Image = Image;
    }
}
