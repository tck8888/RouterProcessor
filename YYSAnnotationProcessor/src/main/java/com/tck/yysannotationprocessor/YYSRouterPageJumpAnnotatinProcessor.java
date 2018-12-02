package com.tck.yysannotationprocessor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.tck.yysannotation.YYSRouterPage;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;


/**
 * author: tck
 * created on: 2018/11/30 12:57
 * description:
 */
@AutoService(Processor.class)
public class YYSRouterPageJumpAnnotatinProcessor extends AbstractProcessor {


    private Map<String, ModuleMeta> groupMap = new HashMap<>();
    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {


        Set<? extends Element> routeElements = roundEnvironment.getElementsAnnotatedWith(YYSRouterPage.class);

        if (routeElements == null || routeElements.size() == 0) {
            return true;
        }
        ParameterizedTypeName mapTypeName = ParameterizedTypeName
                .get(ClassName.get(HashMap.class), ClassName.get(String.class),
                        ClassName.get(Class.class));
        ParameterSpec mapParameterSpec = ParameterSpec.builder(mapTypeName, "activityMap")
                .build();
        MethodSpec.Builder routerInitBuilder = MethodSpec.methodBuilder("initActivityMap")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(mapParameterSpec);

        for (Element element : routeElements) {
            YYSRouterPage route = element.getAnnotation(YYSRouterPage.class);
            String[] routerUrls = route.value();
            ClassName name = ClassName.get((TypeElement) element);

            for (String url : routerUrls) {
                //核心逻辑  将字符与类做映射关联
                routerInitBuilder.addStatement("activityMap.put($S, $T.class)", url, name);
            }

            TypeSpec autoCreateModuleActivityMap_app = TypeSpec.classBuilder("AutoCreateModuleActivityMap_app")
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(routerInitBuilder.build())
                    .build();
            try {
                JavaFile.builder(name.packageName(),autoCreateModuleActivityMap_app).build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private TypeSpec processRouterTable(Set<? extends Element> elements) {
        //注意这里一定要做判空处理,因为apt会多次扫描项目所有文件包括javaPoet生成的文件.
        //不做这步处理的话,第一次生成你需要的文件,第二次再进行扫描的时候,又会去生成文件,那时系统就会报一个异常,提示不能生成相同的文件
        if (elements == null || elements.size() == 0) {
            return null;
        }
        ParameterizedTypeName mapTypeName = ParameterizedTypeName
                .get(ClassName.get(HashMap.class), ClassName.get(String.class),
                        ClassName.get(Class.class));
        ParameterSpec mapParameterSpec = ParameterSpec.builder(mapTypeName, "activityMap")
                .build();
        MethodSpec.Builder routerInitBuilder = MethodSpec.methodBuilder("initActivityMap")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(mapParameterSpec);

        for (Element element : elements) {
            YYSRouterPage route = element.getAnnotation(YYSRouterPage.class);
            String[] routerUrls = route.value();
            for (String url : routerUrls) {
                //核心逻辑  将字符与类做映射关联
                routerInitBuilder.addStatement("activityMap.put($S, $T.class)", url, ClassName.get((TypeElement) element));
            }
        }

        return TypeSpec.classBuilder("AutoCreateModuleActivityMap_app")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(routerInitBuilder.build())
                .build();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> ret = new HashSet<>();
        ret.add(YYSRouterPage.class.getCanonicalName());
        return ret;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void error(String error) {
        messager.printMessage(Diagnostic.Kind.ERROR, error);
    }
}
