package com.example.foodordering.utils;

import android.util.Log;
import android.widget.TextView;

import com.example.foodordering.activity.MainActivity;
import com.example.foodordering.model.Cart;
import com.example.foodordering.model.CartedProduct;
import com.example.foodordering.model.Product;

import java.util.Map;

public class Utility {

    public static int increaseCartedProductCount(String productId, TextView textView) {
        // get map from cart
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();

        CartedProduct cartedProduct = null;

        if (!cartedProductMap.isEmpty() && cartedProductMap.containsKey(productId)) {
            cartedProduct = cartedProductMap.get(productId);
        } else {
            // create new item into the map

            // check if product is present in the product id map in main activity
            if (MainActivity.productMap.containsKey(productId)) {
                // get the product object
                Product product = MainActivity.productMap.get(productId);

                // product is not present in the cart, so create a new productItem into the cart
                cartedProduct = new CartedProduct(product, 0);
            }
        }

        if (cartedProduct == null) {
            return 0;
        }

        Integer count = cartedProduct.getCount();

        cartedProduct.setCount(++count);

        // Map won't update automatically, so we have to update
        // Map can't take duplicates, if any duplicate is present in the map, first present will be override by new item
        // so our map has keys with product id, after modifying the object , insert newly into the map
        // since map won't allow duplicates new product will override existing one
        cartedProductMap.put(productId, cartedProduct);

        textView.setText(String.valueOf(count));
        Log.i("Hero", (String) textView.getText());

        return count;
    }


    // For decreasing the count
    // we have to pass productId and the view we have to update
    public static void decreaseCartedProductCount(String productId, TextView dia_product_count_textView) {
        // get the cartedProductMap from cart
        // cartedProductMap is created as static, so no object is required
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();

        int count;
        CartedProduct cartedProduct;

        // If cartedProductMap is  empty or product is not present in the cart,  so product is not clicked add button at least once
        if (cartedProductMap.isEmpty() || !cartedProductMap.containsKey(productId)) {
            return;
        }

        // get relative CartedProduct object from Map
        cartedProduct = cartedProductMap.get(productId);
        count = cartedProduct.getCount();

        // checking if count is > 0
        if (count > 0) {
            // decrease count by 1
            cartedProduct.setCount(--count);
        }

        // Map won't update automatically, so we have to update
        // Map can't take duplicates, if any duplicate is present in the map, first present will be override by new item
        // so our map has keys with product id, after modifying the object , insert newly into the map
        // since map won't allow duplicates new product will override existing one
        cartedProductMap.put(productId, cartedProduct);

        // update in the ui Dialog box TextView Count
        dia_product_count_textView.setText(String.valueOf(cartedProduct.getCount()));

        // If Count goes to zero, then remove the item from cart
        if (cartedProduct.getCount() == 0) {
            Log.d("bha bef rem siz", "" + cartedProductMap.size());
            cartedProductMap.remove(productId);
            Log.d("bha aft rem size ", "" + cartedProductMap.size());
        }


    }


}
