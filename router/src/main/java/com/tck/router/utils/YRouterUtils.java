package com.tck.router.utils;

import java.util.List;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/25</p>
 *
 * @author tck
 * @version 1.0
 */
public class YRouterUtils {

    public static <T> boolean isNotEmpty(List<T> list) {
        return list != null && !list.isEmpty();
    }
}
