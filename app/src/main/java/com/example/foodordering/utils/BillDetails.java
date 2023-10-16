package com.example.foodordering.utils;

import com.example.foodordering.model.Cart;
import com.example.foodordering.model.CartedProduct;
import com.example.foodordering.model.Product;

import java.util.Map;

public class BillDetails {

    // This will update the total bill amount in the bottom when ever this is called
    public static String[] updateBill() {

        int bill_price = 0;
        int carted_single_product_count = 0;
        float carted_single_product_cost = 0;
        int total_no_of_products = 0;
        Product product;

        // get the map from cart
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();

        if (!cartedProductMap.isEmpty()) {
            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<String, CartedProduct> entry : cartedProductMap.entrySet()) {
                carted_single_product_count = entry.getValue().getCount();
                product = entry.getValue().getProduct();
                carted_single_product_cost = product.getPrice();

                bill_price += carted_single_product_count * carted_single_product_cost;
            }

            // Total no of Products in cart
            total_no_of_products = cartedProductMap.size();

        }

        // Set back to the TextViews
        // These two TextViews are Static in the CartActivity, because when a product is updated in the cart these also have to be updated in the bottom total price
//        CartActivity.total_bill_text_view.setText(String.valueOf(bill_price));
//        CartActivity.total_noOf_products_text_view.setText(String.valueOf(total_no_of_products));

        String[] bill_det = new String[2];
        bill_det[0] = String.valueOf(bill_price);
        bill_det[1] = String.valueOf(total_no_of_products);

//        CartFragment.total_bill_text_view.setText(String.valueOf(bill_price));
//        CartFragment.total_noOf_products_text_view.setText(String.valueOf(total_no_of_products));

        return bill_det;
    }
}

