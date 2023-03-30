package com.example.myrestaurant.Model;

public class Dish {
    int DishId, CategoryId;
    String DishName, Price, State;
    byte[] Image;

    public int getDishId() {
        return DishId;
    }

    public void setDishId(int DishId) {
        DishId = DishId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int CategoryId) {
        CategoryId = CategoryId;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String DishName) {
        DishName = DishName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        Price = Price;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        State = State;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] Image) {
        Image = Image;
    }
}
