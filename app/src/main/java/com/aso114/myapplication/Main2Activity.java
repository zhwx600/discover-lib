package com.aso114.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void click1(View view) {
        EventBus.getDefault().post(123, "123");
    }
}
