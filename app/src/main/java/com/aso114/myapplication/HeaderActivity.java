package com.aso114.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HeaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new HeaderFragement()).commitAllowingStateLoss();
    }
}
