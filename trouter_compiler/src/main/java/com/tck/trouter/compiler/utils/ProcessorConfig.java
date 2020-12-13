package com.tck.trouter.compiler.utils;

/**
 * description:
 *
 * @author tck88
 * @version v1.0.0
 * @date 2020/12/13 19:27
 */
public interface ProcessorConfig {

    // @ARouter注解 的 包名 + 类名
    String AROUTER_PACKAGE =  "com.tck.trouter.annotations.TRouter";

    // 接收参数的TAG标记
    String OPTIONS = "moduleName"; // 同学们：目的是接收 每个module名称
    String APT_PACKAGE = "packageNameForAPT"; // 同学们：目的是接收 包名（APT 存放的包名）
}
