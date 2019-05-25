package com.tck.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/25</p>
 *
 * @author tck
 * @version 1.0
 */
public class RouterForward {


    private Bundle bundle;
    private Context context;
    private Class<? extends Activity> aClass;

    public RouterForward(Class<? extends Activity> aClass) {

        bundle = new Bundle();
        this.aClass = aClass;
    }

    public RouterForward with(Context context) {
        this.context = context;
        return this;
    }

    public RouterForward param(String key, String value) {
        bundle.putString(key, value);
        return this;
    }

    public RouterForward param(String key, Serializable value) {
        bundle.putSerializable(key, value);
        return this;
    }

    public RouterForward param(String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public void navigation() {
        if (context == null) {
            throw new RuntimeException("context，不能为空");
        }
        if (aClass == null) {
            Toast.makeText(context, "未找到对应路由", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, aClass);
        intent.putExtra("key", bundle);
        context.startActivity(intent);

    }

}
