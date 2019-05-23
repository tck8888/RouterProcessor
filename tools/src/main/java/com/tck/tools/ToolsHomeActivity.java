package com.tck.tools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ToolsHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_home);

        Man man = new Man();
        Salesman salesman = new Salesman(man);
        salesman.applyBank();

        findViewById(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToolsHomeActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            HookStartActivityUtil.hookStartActivity();
            HookStartActivityUtil.hookActivityThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
