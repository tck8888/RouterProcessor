package com.healthmudi.dia.routerannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/21 8:24</p>
 *
 * @author tck
 * @version 3.5
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface YRouter {

    String value();
}
