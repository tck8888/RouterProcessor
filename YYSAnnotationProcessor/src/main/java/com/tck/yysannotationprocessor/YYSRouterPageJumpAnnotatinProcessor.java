package com.tck.yysannotationprocessor;

import com.google.auto.service.AutoService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;


/**
 * author: tck
 * created on: 2018/11/30 12:57
 * description:
 */
@AutoService(javax.annotation.processing.Processor.class)
public class YYSRouterPageJumpAnnotatinProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
