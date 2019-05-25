package com.healthmudi.dia.annotationscompiler;

import java.util.Map;

public class YUtils {

    public static boolean isNotEmpty(Map<String, String> options) {
        return options != null && !options.isEmpty();
    }
}
