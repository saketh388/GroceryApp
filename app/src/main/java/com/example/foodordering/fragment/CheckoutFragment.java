package com.example.foodordering.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.foodordering.R;
import com.example.foodordering.activity.MainActivity;

public class CheckoutFragment extends Fragment {

    View mView;

    String total_bill;
    String total_carted_products;

    String username;
    String mobile;

    TextView total_amt;
    TextView total_items;
    TextView mobile_tv;
    TextView username_tv;

    Button send_sms_btn, pay_btn, contact_us_btn, locate_btn;

/*
    // only one time u can get the details of the user
    // because we used fragment manager to transfer data from main activity to this fragment
    private boolean isUsed = false;*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_checkout, container, false);

        total_amt = mView.findViewById(R.id.total_amt);
        total_items = mView.findViewById(R.id.total_items);
        mobile_tv = mView.findViewById(R.id.mobile_tv);
        username_tv = mView.findViewById(R.id.username_tv);

        send_sms_btn = mView.findViewById(R.id.send_sms_btn);
        pay_btn = mView.findViewById(R.id.pay_btn);
        contact_us_btn = mView.findViewById(R.id.contact_us_btn);
        locate_btn = mView.findViewById(R.id.locate_btn);


        FragmentManager fm = getParentFragmentManager();

        // get details from cart fragment
        fm.setFragmentResultListener("BillDetailsBundle", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                // get the bill details from the cart fragment
                total_bill = result.getString("totalBill");
                total_carted_products = result.getString("totalCartedProducts");

                setBillDetails(total_bill, total_carted_products);

                Log.i("jack", total_bill + "  " + total_carted_products);
            }
        });

/*        if(!isUsed) {
            // get details of user from main activity
            fm.setFragmentResultListener("UserDetailsBundle", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                    // get the bill details from the cart fragment
                    username = result.getString("username");
                    mobile = result.getString("mobile");

                    setUserDetails(username, mobile);

                    Log.i("jack", username + "  " + mobile);

                }
            });

            isUsed = true;
        }*/

        // get the bill details from the cart fragment
        username = MainActivity.username;
        mobile = MainActivity.mobile;

        setBillDetails(total_bill, total_carted_products);
        setUserDetails(username, mobile);

        // send sms
        send_sms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
                Log.i("jacuuuk", username + "  " + mobile);
            }
        });

        // make payment
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment();
            }
        });

        // contact us via call
        contact_us_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactUs();
            }
        });

        // company location
        locate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyLocation();
            }
        });


        return mView;
    }

    public void setBillDetails(String total_bill, String total_carted_products) {
        if (total_bill != null && total_carted_products != null) {
            total_amt.setText(total_bill);
            total_items.setText(total_carted_products);
        }
    }

    public void setUserDetails(String username, String mobile) {
        if (username != null && mobile != null) {
            username_tv.setText(username);
            mobile_tv.setText(mobile);
        }
    }

    private void sendSMS() {
        if (mobile != null) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mobile, null, "Total amout : " + total_bill + "  " + "Total Items : " + total_carted_products, null, null);
            Toast.makeText(getActivity(), "SMS Send Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void payment() {
        Toast.makeText(getActivity(), "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    private void contactUs() {
        // This will directly call to company
        Intent intent1 = new Intent(Intent.ACTION_CALL);
        intent1.setData(Uri.parse("tel:" + "9191919191"));
        startActivity(intent1);
        Log.i("jack hiiii ", username + "  " + mobile);

    }

    private void companyLocation() {
        Intent mMapIntent;
        Uri mLocation;

        mLocation = Uri.parse("geo:0,0?q=Amrita Vishwa Vidyapeetham,+Ettimadai,+Coimbatore");

        mMapIntent = new Intent();
        mMapIntent.setAction(Intent.ACTION_VIEW);
        mMapIntent.setData(mLocation);
        startActivity(mMapIntent);
    }


}