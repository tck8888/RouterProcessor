package com.tck.resumemodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tck.yysannotation.YYSRouterPage;

/**
 * author: tck
 * created on: 2018/12/2 13:25
 * description:
 */
@YYSRouterPage("md_resume")
public class ResumeHomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_home_activity);
    }
}
