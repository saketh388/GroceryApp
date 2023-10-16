package com.example.foodordering.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodordering.R;
import com.example.foodordering.fragment.CartFragment;
import com.example.foodordering.fragment.HomeProductListFragment;
import com.example.foodordering.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    public static String username;
    public static String mobile;
    // this is used to map product and its id
    public static Map<String, Product> productMap = new ConcurrentHashMap<>();
    FrameLayout frameLayout1;
    // product list
    ArrayList<Product> arrayProductList = new ArrayList<>();
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout1 = findViewById(R.id.frameLay1);


        fm = getSupportFragmentManager();

        // get user name and mobile number from login activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        username = bundle.getString("username");
        mobile = bundle.getString("mobile");
//
//        //send this details the checkout fragment
//        // we just post the bundle when ever that fragment launch, it will take this
//        sendToChechoutFrag(username,mobile, new CheckoutFragment());

        // add new products to list
        addProducts();

        // insert product id with product in the productMap
        //Converting arrayProductList List into product Map. This is used for to get product by product Id
        // we are mapping product id : product object
        // Product::getProductId -- is to get the product id
        //  Function.identity()-- telling the same object as value
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            productMap = arrayProductList.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));
        }

        changeFragment(new HomeProductListFragment());

    }

    public void changeFragment(Fragment fragment) {

        ft = fm.beginTransaction();

        ft.replace(R.id.frameLay1, fragment, "bharathfrag");


        // if fragment is HomeProductListFragment then don't add into backstack
        // if fragment is not HomeProductListFragment then  add into backstack
        if (!(fragment instanceof HomeProductListFragment)) {
            ft.addToBackStack("bhaStack");
        }

        if (fragment instanceof HomeProductListFragment) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("productMap", (Serializable) productMap);
            fm.setFragmentResult("dataproductMap", bundle);
        }
        ft.commit();
    }

    // Assign Top menu cart feature
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    //we have to override onOptionsItemSelected method in our Activity, which is called when user clicks on the item in Options menu.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_cart_item:
                changeFragment(new CartFragment());
//                Intent intent = new Intent(this, CartActivity.class);
//                startActivity(intent);
                break;
        }

        return true;
    }

    public void addProducts() {
        arrayProductList.add(new Product("p1", R.drawable.food1, "bread", 60));
        arrayProductList.add(new Product("p2", R.drawable.food2, "cola", 20));
        arrayProductList.add(new Product("p3", R.drawable.food3, "burger", 50));

        arrayProductList.add(new Product("p4", R.drawable.food4, "donut", 90));
        arrayProductList.add(new Product("p5", R.drawable.food5, "pizza", 320));
        arrayProductList.add(new Product("p6", R.drawable.food6, "french frizz", 50));

        arrayProductList.add(new Product("p7", R.drawable.food7, "ice cream", 10));
        arrayProductList.add(new Product("p8", R.drawable.food8, "cup bone", 85));
        arrayProductList.add(new Product("p9", R.drawable.food9, "shake", 120));

        arrayProductList.add(new Product("p10", R.drawable.food10, "sand which", 40));
        arrayProductList.add(new Product("p11", R.drawable.food11, "cup cake", 135));
        arrayProductList.add(new Product("p12", R.drawable.food12, "lollipops", 150));
    }

/*    public void sendToChechoutFrag(String username, String mobile, Fragment fragment){
        ft = fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("mobile", mobile);

        fm.setFragmentResult("UserDetailsBundle", bundle);
        ft.commit();
    }*/

}