package com.example.ki_shaan;

public class ShopItemsClass {
    String name;
    int price;

    public ShopItemsClass(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public ShopItemsClass() {
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
