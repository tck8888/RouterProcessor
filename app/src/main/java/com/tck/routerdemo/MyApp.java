package com.tck.routerdemo;

import android.app.Application;

import com.tck.router.YRouter;


/**
 * <p>description:</p>
 * <p>created on: 2019/5/25</p>
 *
 * @author tck
 * @version 1.0
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        YRouter.getInstance().init(this, "com.tck.tools.routesrs");
    }
}
