package com.sodino.retrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sodino.retrofit.R;
import com.sodino.retrofit.protocol.AccountProtocol;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccountProtocol accountProtocol = new AccountProtocol();
        accountProtocol.reqGetAccountInfo();
        accountProtocol.reqGetDepartmentList(0, 2);
        accountProtocol.reqDelMessage("5718");
    }
}
