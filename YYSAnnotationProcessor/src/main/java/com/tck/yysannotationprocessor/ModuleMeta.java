package com.tck.yysannotationprocessor;

import com.tck.yysannotation.YYSRouterPage;

/**
 * author: tck
 * created on: 2018/12/2 11:11
 * description:
 */
public class ModuleMeta {

    public String templet;
    public String moduleName;
    public String title;

    public ModuleMeta(YYSRouterPage annotation, String moduleName) {
        this.moduleName=moduleName;

    }
}
