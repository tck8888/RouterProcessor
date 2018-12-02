package com.tck.routerprocessor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.tck.yysannotation.YYSRouterPage;

/**
 * https://blog.csdn.net/mingjiezuo/article/details/79667769
 * <p>
 * YYSAnnotationProcessor
 */
@YYSRouterPage("md_home")
public class MainActivity extends AppCompatActivity {

    private Button btnGotoNext;
    private Button btnGotoResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnGotoNext = (Button) findViewById(R.id.btn_goto_next);
        btnGotoResume = (Button) findViewById(R.id.btn_goto_resume);

        btnGotoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnGotoResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
