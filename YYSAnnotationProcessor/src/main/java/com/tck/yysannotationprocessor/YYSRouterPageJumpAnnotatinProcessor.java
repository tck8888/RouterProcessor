package com.tck.yysannotationprocessor;

import com.google.auto.service.AutoService;
import com.tck.yysannotation.YYSRouterPage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;


/**
 * author: tck
 * created on: 2018/11/30 12:57
 * description:
 */
@AutoService(javax.annotation.processing.Processor.class)
public class YYSRouterPageJumpAnnotatinProcessor extends AbstractProcessor {

    private Map<Element, List<VariableElement>> items = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(YYSRouterPage.class);
        for (Element element : elementsAnnotatedWith) {
            //主要获取ElementType 是不是null，即class，interface，enum或者注解类型
            if (element.getEnclosingElement() == null) {
                //直接结束处理器
                return true;
            }

            //如果items的key不存在，则添加一个key
            if (!items.containsKey(element.getEnclosingElement())){
                items.put(element.getEnclosingElement(), new LinkedList<VariableElement>());
            }
        }
        return false;
    }
}
