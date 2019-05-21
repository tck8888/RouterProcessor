package com.tck.routerdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.healthmudi.dia.routerannotations.YRouter;

@YRouter("main/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
