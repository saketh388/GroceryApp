package com.example.foodordering.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodordering.R;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText usernameET, mobileET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.userNameET);
        mobileET = findViewById(R.id.mobileNUmberET);

        loginBtn = findViewById(R.id.log_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameET.getText().toString();
                String mobile = mobileET.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    usernameET.setError("Provide Username");
                } else if (TextUtils.isEmpty(mobile)) {
                    mobileET.setError("Provide Phone Number");
                } else {
                    if (mobileValidCheck()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("username", username);
                        bundle.putString("mobile", mobile);

                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
    }

    private boolean mobileValidCheck() {
        String phone = mobileET.getText().toString();
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() > 9 && phone.length() <= 13) {
                return true;
            }
        }
        Toast.makeText(LoginActivity.this, " Provide Valid Mobile Number", Toast.LENGTH_SHORT).show();
        return false;
    }
}