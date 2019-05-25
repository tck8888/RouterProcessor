package com.tck.router;

import android.app.Activity;

import java.util.Map;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/20</p>
 *
 * @author tck
 * @version 1.0
 */
public interface IYRouter {

    void onLoad(Map<String, Class<? extends Activity>> routers);
}
