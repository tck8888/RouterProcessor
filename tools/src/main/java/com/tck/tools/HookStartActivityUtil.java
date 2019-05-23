package com.tck.tools;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * <p>description:</p>
 * <p>created on: 2019/5/23 8:47</p>
 *
 * @author tck
 * @version 3.5
 */
public class HookStartActivityUtil {

    private static final String TAG = "==============";

    public static void hookStartActivity() throws ClassNotFoundException {

        Object gDefault = RefInvoke.getStaticFieldObject("android.app.ActivityManager", "IActivityManagerSingleton");
        Object mInstance = RefInvoke.getFieldObject("android.util.Singleton", gDefault, "mInstance");
        Class<?> aClass = Class.forName("android.app.IActivityManager");

        Object proxyInstance = Proxy.newProxyInstance(
                HookStartActivityUtil.class.getClassLoader(),
                new Class[]{aClass},
                new MyInvocationHandler(mInstance)
        );

        RefInvoke.setFieldObject("android.util.Singleton", gDefault, "mInstance", proxyInstance);
    }

    public static void hookActivityThread() throws Exception{
        Object sCurrentActivityThread = RefInvoke.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");
        Handler mH = (Handler) RefInvoke.getFieldObject(sCurrentActivityThread, "mH");

        RefInvoke.setFieldObject(Handler.class,mH,"mCallback",new MockClass(mH));
    }

    static class MyInvocationHandler implements InvocationHandler {

        private Object object;

        public MyInvocationHandler(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(TAG, "invoke: " + method.getName());
            if ("startActivity".equals(method.getName())) {
                Intent raw;
                int index =0;
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent){
                        index = i;
                        break;
                    }
                }
                raw =   (Intent)args[index];
                Intent newIntent = new Intent();

                String subPackage = "com.tck.tools";
                ComponentName componentName = new ComponentName(subPackage, SubActivity.class.getName());
                newIntent.setComponent(componentName);
                newIntent.putExtra("===",raw);
                args[index] = newIntent;
                Log.d(TAG, "invoke: hook success" );

            }
            return method.invoke(object, args);
        }
    }

  static   class MockClass implements Handler.Callback{

        Handler handler;

        public MockClass(Handler handler) {
            this.handler = handler;
        }

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    handleLaunchActivity(msg);
                    break;
            }
            handler.handleMessage(msg);
            return true;
        }

        private void handleLaunchActivity(Message msg) {
            Object obj = msg.obj;
            Intent raw = (Intent) RefInvoke.getFieldObject(obj, "intent");
            Intent target = raw.getParcelableExtra("===");
            raw.setComponent(target.getComponent());
        }
    }
}
