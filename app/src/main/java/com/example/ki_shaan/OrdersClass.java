package com.example.ki_shaan;

import java.util.HashMap;

public class OrdersClass {
    String email, name;
    int price, quantity;

    public OrdersClass(String email, String name, int price, int quantity) {
        this.email = email;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    OrdersClass()
    {}

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public HashMap<String, Object> getHashMap() {
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put("email", email);
        newUser.put("name", name);
        newUser.put("quantity", quantity);
        newUser.put("price", price);
        return newUser;
    }
}
