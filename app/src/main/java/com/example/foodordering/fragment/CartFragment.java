package com.example.foodordering.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodordering.R;
import com.example.foodordering.adapter.CartedProductAdapter;
import com.example.foodordering.model.Cart;
import com.example.foodordering.model.CartedProduct;
import com.example.foodordering.utils.BillDetails;

import java.util.ArrayList;
import java.util.Map;


public class CartFragment extends Fragment {

    // get the return values from the bill details function
    public static String[] bill_det = new String[2];
    public static TextView total_bill_text_view;
    public static TextView total_noOf_products_text_view;
    View mView;
    ArrayList<CartedProduct> cartedProductsList = null;

    public static void updateBillPriceTextView(String bill_price) {
        // Set back to the TextViews
        // This will be called in Bll Details to update the bill price if any changes
        // total bill price

        // update bill price in the cart fragement also, if this function is calling from anywhere
        bill_det[0] = bill_price;
        total_bill_text_view.setText(bill_price);
    }

    public static void updateBillTotaLNoOfProdeTextView(String total_no_of_products) {
        // Set back to the TextViews
        // This will be called in Bll Details to update the bill total n of products if any changes
        // total no of products

        // update bill total_no_of_products in the cart fragement also, if this function is calling from anywhere
        bill_det[1] = total_no_of_products;
        total_noOf_products_text_view.setText(total_no_of_products);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_cart, container, false);

        ListView cartListView = mView.findViewById(R.id.cart_list_view);

        // These two TextViews are Static, because when a product is updated in teh cart these also have to be updated in teh bottom total price
        total_bill_text_view = mView.findViewById(R.id.Total_bill_price);
        total_noOf_products_text_view = mView.findViewById(R.id.Total_no_of_products);

        Button checkOutBtn = mView.findViewById(R.id.checkOutBtn);
        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFrag(new CheckoutFragment());
            }
        });


        // get the cartedProductMap from cart
        // cartedProductMap is created as static, so no object is required
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();


        // getting all Carted products from the CartedProductMap values
        // Because in CartedProductMap we have productId as key and Carted products as values
        // So we are taking all values and put it into an arrayList
        if (cartedProductMap != null) {
            cartedProductsList = new ArrayList<>(cartedProductMap.values());
        }

        // Checking Whether we are getting carted items
        if (!cartedProductsList.isEmpty()) {
            for (CartedProduct cp : cartedProductsList) {
                Log.i("bha oh1 Name ", String.valueOf(cp.getProduct().getName()));
                Log.i("bha oh2 Price ", String.valueOf(cp.getProduct().getPrice()));
                Log.i("bha oh3 count ", String.valueOf(cp.getCount()));
            }
        }

        // Adapter
        CartedProductAdapter cartedProductAdapter = new CartedProductAdapter(getActivity().getBaseContext(), cartedProductsList);

        cartListView.setAdapter(cartedProductAdapter);

        // Call static updateBill function in BillDetails class to update the total bill amount, total number of items in cart
        // so that we can see live bill calculating


        bill_det = BillDetails.updateBill();

        // total bill price
        updateBillPriceTextView(bill_det[0]);

        // total no of products
        updateBillTotaLNoOfProdeTextView(bill_det[1]);


        return mView;
    }

    private void changeFrag(Fragment fragment) {
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("totalBill", bill_det[0]);
        bundle.putString("totalCartedProducts", bill_det[1]);

        fm.setFragmentResult("BillDetailsBundle", bundle);
        ft.replace(R.id.frameLay1, fragment);
        ft.addToBackStack("bhaStack");
        ft.commit();

    }

}