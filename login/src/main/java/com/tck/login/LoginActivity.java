package com.tck.login;

import android.os.Bundle;
import android.view.View;

import com.healthmudi.dia.routerannotations.YRouterPath;
import com.tck.router.YRouter;

import androidx.appcompat.app.AppCompatActivity;

@YRouterPath("login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YRouter.getInstance()
                        .action("tools")
                        .with(LoginActivity.this)
                        .navigation();
            }
        });
    }
}
