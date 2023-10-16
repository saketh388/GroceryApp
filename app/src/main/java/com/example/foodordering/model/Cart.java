package com.example.foodordering.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {

    // String -- id, CartedProduct object
    public static Map<String, CartedProduct> cartedProductMap;

    public static Map<String, CartedProduct> getCartedProductMap() {

        if (cartedProductMap == null) {
            cartedProductMap = new ConcurrentHashMap<>();
        }
        return cartedProductMap;
    }

    public static void setCartedProductMap(Map<String, CartedProduct> cartedProductMap) {
        Cart.cartedProductMap = cartedProductMap;
    }

}
