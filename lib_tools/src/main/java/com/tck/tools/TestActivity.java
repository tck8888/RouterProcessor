package com.tck.tools;

import android.os.Bundle;

import com.healthmudi.dia.routerannotations.YRouterPath;

import androidx.appcompat.app.AppCompatActivity;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/23 8:21</p>
 *
 * @author tck
 * @version 3.5
 */
@YRouterPath("test")
public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
