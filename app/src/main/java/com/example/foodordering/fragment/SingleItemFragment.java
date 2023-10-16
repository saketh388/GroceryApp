package com.example.foodordering.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.foodordering.R;
import com.example.foodordering.model.Cart;
import com.example.foodordering.model.CartedProduct;
import com.example.foodordering.model.Product;
import com.example.foodordering.utils.Utility;

import java.util.Map;


public class SingleItemFragment extends Fragment {

    View mView;
    Product product;

    // this text view is used in other function, so declare in global
    TextView sin_product_count_textView;

    ImageView sin_product_image;
    TextView sin_product_name;
    TextView sin_product_price;


    // we want the product id
    // in  Parcelable we have product that is sending , in product there is product id
    String pro_productId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("here bha", "here here ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_single_item, container, false);

        // get all attributes
        sin_product_image = mView.findViewById(R.id.dia_product_image);
        sin_product_name = mView.findViewById(R.id.dia_product_name);
        sin_product_price = mView.findViewById(R.id.dia_product_price);

        Button bt_decrease = mView.findViewById(R.id.bt_decrease);
        sin_product_count_textView = mView.findViewById(R.id.dia_product_count_textView);
        Button bt_increase = mView.findViewById(R.id.bt_increase);

        // get the product details that is selected in all product list
        FragmentManager fm = getParentFragmentManager();
        fm.setFragmentResultListener("dataFromHomeItemFrag", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                // got the bundle from Home Item Fragment
                // in that we have product object
                product = result.getParcelable("myProductObj");

                // set product into view
                setProductIntoView();
                Log.i("here bha", "data " + product.getName() + " .. " + product.getProductId());


            }
        });

        // set all the values in the layout
        // set product into view
        // this is used again because if we clicked cup cake, we get into cupcake single product view
        // here if we click on cart button, we will redirect to cart view
        // in cart if we click back, we have to show the details of cup cake as it is
        // that's why we have this here
        // for the first time to set this view is set from above setFragmentResultListener
        setProductIntoView();


        bt_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.decreaseCartedProductCount(pro_productId, sin_product_count_textView);

                // give product count according to the updated in text view
                setCountTextView();
            }
        });

        bt_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.increaseCartedProductCount(pro_productId, sin_product_count_textView);

                // give product count according to the updated in text view
                setCountTextView();
            }
        });

        return mView;
    }

    // use to set product into the view
    private void setProductIntoView() {
        if (product != null) {
            Log.i("here bha", "data " + product.getName() + " .. " + product.getProductId());
            pro_productId = product.getProductId();
            sin_product_image.setImageResource(product.getImageUrlId());
            sin_product_name.setText(product.getName());
            sin_product_price.setText("$ " + product.getPrice());

            // if product present in cart then give count according to that
            // give product count according to the present in cart in text view
            setCountTextView();
        }
    }

    // use to set text count into view
    private void setCountTextView() {
        // get the cart map and check whether the product is present in the cart
        // if it is present then give product count according to that

        // we always fectch the count fom the cartedProductMap, this will be advantage in many situations like
        // this is used again because if we clicked cup cake, we get into cupcake single product view
        // here if we click on cart button, we will redirect to cart view
        // in cart if we click back, we have to show the details of cup cake as it is, even if updated in the cart product count
        // that's why we have this here
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();

        if (!cartedProductMap.isEmpty() && cartedProductMap.containsKey(pro_productId)) {
            sin_product_count_textView.setText(cartedProductMap.get(pro_productId).getCount() + "");
        } else {
            sin_product_count_textView.setText("0");
        }
    }


}