package com.tck.routerdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tck.trouter.annotations.TRouter;

/**
 * description:
 *
 * @author tck88
 * @version v1.0.0
 * @date 2020/12/13 20:45
 */
@TRouter(path = "MainActivity")
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
