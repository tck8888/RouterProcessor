package com.tck.router;

import android.app.Activity;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/20</p>
 *
 * @author tck
 * @version 1.0
 */
public class YRouter {

    private HashMap<String, Class<? extends Activity>> activityList;

    public static YRouter yRouter = new YRouter();

    private YRouter() {
        activityList = new HashMap<>();
    }

    public static YRouter getInstance() {
        return yRouter;
    }

    public void putActivity(String path, Class<? extends Activity> clazz) {
        if (!TextUtils.isEmpty(path)) {
            return;
        }

        activityList.put(path, clazz);
    }

}
