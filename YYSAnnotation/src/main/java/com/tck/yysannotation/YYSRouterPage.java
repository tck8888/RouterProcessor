package com.tck.yysannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: tck
 * created on: 2018/11/30 12:52
 * description:
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface YYSRouterPage {

    String value() default " ";
}
