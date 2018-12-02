package com.tck.routerprocessor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tck.yysannotation.YYSRouterPage;

/**
 * https://blog.csdn.net/mingjiezuo/article/details/79667769
 * <p>
 * YYSAnnotationProcessor
 */
@YYSRouterPage("md_setting")
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
