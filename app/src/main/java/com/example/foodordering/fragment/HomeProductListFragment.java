package com.example.foodordering.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodordering.R;
import com.example.foodordering.adapter.ProductAdapter;
import com.example.foodordering.model.Product;

import java.util.ArrayList;
import java.util.Map;


public class HomeProductListFragment extends Fragment {

    View mView;
    FragmentManager fm;

    // this can be get from map that is send by main activity
    // used to show the list in the list view
    ArrayList<Product> arrayProductList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("here bha11", "here here ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home_product_list, container, false);

        // get the productMap that is send by the main activity
        fm = getParentFragmentManager();
        fm.setFragmentResultListener("dataproductMap", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                // got the bundle from Home Item Fragment
                // in that we have product object
                Map<String, Product> productMap = (Map<String, Product>) result.getSerializable("productMap");

                Log.i("here bha", "data ");

                // add the products in to the arrayProductList which ic required for showing in listview
                for (Map.Entry<String, Product> entry : productMap.entrySet()) {
                    arrayProductList.add(entry.getValue());
                }

            }
        });

        ListView all_items_list_view = mView.findViewById(R.id.all_items_list_view);

        ProductAdapter productAdapter = new ProductAdapter(getActivity().getBaseContext(), arrayProductList);

        all_items_list_view.setAdapter(productAdapter);


        all_items_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // get the product from that array Product List
                Product product = arrayProductList.get(i);

                // send data to single item fragment by using fragment manager with bundles
                Bundle bundle = new Bundle();
                bundle.putParcelable("myProductObj", product);

                fm = getParentFragmentManager();
                fm.setFragmentResult("dataFromHomeItemFrag", bundle);
                changeFragment(new SingleItemFragment());

            }
        });

        return mView;
    }


    public void changeFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frameLay1, fragment);
        ft.addToBackStack("bhaStack");
        ft.commit();
    }

}