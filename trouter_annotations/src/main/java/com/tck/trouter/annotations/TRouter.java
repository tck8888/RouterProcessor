package com.tck.trouter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description:
 *
 * @author tck88
 * @version v1.0.0
 * @date 2020/12/13 16:11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface TRouter {

    String path();

    String group() default "";
}
