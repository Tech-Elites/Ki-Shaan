package com.example.ki_shaan;

import java.util.HashMap;

public class Products {
    int price, quantityavailable;

    public Products(int price, int quantityavailable) {
        this.price = price;
        this.quantityavailable = quantityavailable;
    }

    public HashMap<String, Object> getHashMap() {
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put("price", price);
        newUser.put("quantityavailable", quantityavailable);
        return newUser;
    }
}
