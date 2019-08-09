package com.aso114.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().registerSticky(this);
//        getSupportFragmentManager().beginTransaction().add(R.id.container,new MyFragment()).commitAllowingStateLoss();
    }
    @Subscriber(tag = "123")
    public void asd(int a){
        Toast.makeText(this,""+a,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public void click1(View view) {

        startActivity(new Intent(this,Main2Activity.class));
    }
}
