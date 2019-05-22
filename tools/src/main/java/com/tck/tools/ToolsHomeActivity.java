package com.tck.tools;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ToolsHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_home);

        Man man = new Man();
        Salesman salesman = new Salesman(man);
        salesman.applyBank();

    }
}
