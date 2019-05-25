package com.tck.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.tck.router.utils.YRouterUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/20</p>
 *
 * @author tck
 * @version 1.0
 */
public class YRouter {

    private static volatile YRouter instance;
    private Context mApplicationContext;
    private String routerModulePackageName = "com.tck.tools.routesrs";

    private static Map<String, Class<? extends Activity>> routers = new HashMap<>();

    private YRouter() {

    }

    public static YRouter getInstance() {
        if (instance == null) {
            synchronized (YRouter.class) {
                if (instance == null) {
                    instance = new YRouter();
                }
            }
        }
        return instance;
    }

    public void init(Application context, String routerModulePackageName) {
        this.mApplicationContext = context;
        this.routerModulePackageName = routerModulePackageName;
        try {
            List<String> fileNameByPackageName = ClassUtils.getFileNameByPackageName(context, routerModulePackageName);
            if (YRouterUtils.isNotEmpty(fileNameByPackageName)) {
                for (int i = 0; i < fileNameByPackageName.size(); i++) {
                    String routersClassName = fileNameByPackageName.get(i);
                    Class<?> aClass = Class.forName(routersClassName);
                    if (IYRouter.class.isAssignableFrom(aClass)) {
                        IYRouter router = (IYRouter) aClass.newInstance();
                        router.onLoad(routers);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    public RouterForward action(String path) {
        Class<? extends Activity> aClass = routers.get(path);
        return new RouterForward(aClass);
    }
}
